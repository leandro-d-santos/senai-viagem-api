package senai.viagem.api.controller;

import senai.viagem.api.dto.AvaliacaoDTO;
import senai.viagem.api.dto.DestinoDTO;
import senai.viagem.api.model.Destino;
import senai.viagem.api.service.DestinoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
public class DestinoController {

    private final DestinoService service;

    public DestinoController(DestinoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Destino> cadastrar(@RequestBody DestinoDTO dto) {
        return ResponseEntity.ok(service.cadastrarDestino(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destino> atualizar(@PathVariable Long id, @RequestBody DestinoDTO dto) {
        return ResponseEntity.ok(service.atualizarDestino(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<Destino>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<Destino>> pesquisar(@RequestParam String termo) {
        return ResponseEntity.ok(service.pesquisar(termo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destino> detalhar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/avaliar")
    public ResponseEntity<Destino> avaliar(@PathVariable Long id, @RequestBody AvaliacaoDTO dto) {
        if (dto.nota < 1 || dto.nota > 10)
            return ResponseEntity.badRequest().build();
        return service.avaliar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean excluido = service.excluir(id);
        return excluido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
