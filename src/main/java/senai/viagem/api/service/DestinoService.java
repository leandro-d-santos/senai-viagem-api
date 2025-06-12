package senai.viagem.api.service;

import senai.viagem.api.dto.AvaliacaoDTO;
import senai.viagem.api.dto.DestinoDTO;
import senai.viagem.api.model.Destino;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DestinoService {

    private final List<Destino> destinos = new ArrayList<>();
    private long idCounter = 1;

    public Destino cadastrarDestino(DestinoDTO dto) {
        idCounter += 1;
        Destino destino = new Destino();
        destino.setId(idCounter);
        destino.setNome(dto.nome);
        destino.setLocalizacao(dto.localizacao);
        destino.setDescricao(dto.descricao);
        destinos.add(destino);
        return destino;
    }

    public Destino atualizarDestino(long id, DestinoDTO dto) {
        Optional<Destino> destino = destinos.stream().filter(d -> d.getId().equals(id)).findFirst();
        if (!destino.isPresent()) {
            return null;
        }
        destino.get().setNome(dto.nome);
        destino.get().setLocalizacao(dto.localizacao);
        destino.get().setDescricao(dto.descricao);
        return destino.get();
    }

    public List<Destino> listarTodos() {
        return destinos;
    }

    public List<Destino> pesquisar(String termo) {
        return destinos.stream()
            .filter(d -> d.getNome().toLowerCase().contains(termo.toLowerCase()) ||
                         d.getLocalizacao().toLowerCase().contains(termo.toLowerCase()))
            .collect(Collectors.toList());
    }

    public Optional<Destino> buscarPorId(Long id) {
        return destinos.stream().filter(d -> d.getId().equals(id)).findFirst();
    }

    public boolean excluir(Long id) {
        return destinos.removeIf(d -> d.getId().equals(id));
    }

    public Optional<Destino> avaliar(Long id, AvaliacaoDTO dto) {
        Optional<Destino> opt = buscarPorId(id);
        opt.ifPresent(destino -> {
            double soma = destino.getMediaNotas() * destino.getTotalAvaliacoes();
            destino.setTotalAvaliacoes(destino.getTotalAvaliacoes() + 1);
            double novaMedia = (soma + dto.nota) / destino.getTotalAvaliacoes();
            destino.setMediaNotas(novaMedia);
        });
        return opt;
    }
}
