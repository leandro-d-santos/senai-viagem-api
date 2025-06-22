package senai.viagem.api.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import senai.viagem.api.entities.Usuario;
import senai.viagem.api.repositories.UsuarioRepository;

import java.util.stream.Collectors;

@Service
public class DetalhesUsuarioService implements UserDetailsService {
    private final UsuarioRepository repo;

    public DetalhesUsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        var user = repo.findByNomeUsuario(userName).orElseThrow();
        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getNomeUsuario(), user.getSenha(), authorities);
    }
}