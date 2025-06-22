package senai.viagem.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import senai.viagem.api.dto.UsuarioDTO;
import senai.viagem.api.entities.Usuario;
import senai.viagem.api.repositories.UsuarioRepository;

import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder senhaEncoder;

    public UsuarioService(
            UsuarioRepository userRepo,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = userRepo;
        this.senhaEncoder = passwordEncoder;
    }

    public String Registrar(UsuarioDTO dto) {
        if (usuarioRepository.findByNomeUsuario(dto.nomeUsuario).isPresent()) {
            return "Usuário já existe";
        }

        var isValidRole = "ADMIN".equals(dto.role.toUpperCase()) || "USER".equals(dto.role.toUpperCase());
        if (!isValidRole) return "Perfil inválido";

        var user = new Usuario();
        user.setNomeUsuario(dto.nomeUsuario);
        user.setSenha(senhaEncoder.encode(dto.senha));
        user.setRoles(Set.of(dto.role.toUpperCase()));

        usuarioRepository.save(user);
        return "Usuário registrado com sucesso!";
    }

}
