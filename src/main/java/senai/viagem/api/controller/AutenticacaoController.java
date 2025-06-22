package senai.viagem.api.controller;

import org.springframework.web.bind.annotation.*;
import senai.viagem.api.dto.UsuarioDTO;
import senai.viagem.api.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    private final UsuarioService usuarioService;

    public AutenticacaoController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public String register(@RequestBody UsuarioDTO dto) {
        return usuarioService.Registrar(dto);
    }
}
