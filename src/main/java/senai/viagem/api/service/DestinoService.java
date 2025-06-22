package senai.viagem.api.service;

import senai.viagem.api.dto.AvaliacaoDTO;
import senai.viagem.api.dto.DestinoDTO;
import senai.viagem.api.entities.Destino;
import org.springframework.stereotype.Service;
import senai.viagem.api.repositories.DestinoRepository;

import java.util.*;

@Service
public class DestinoService {

    private final DestinoRepository repository;

    public DestinoService(DestinoRepository repository) {
        this.repository = repository;
    }

    public Destino cadastrarDestino(DestinoDTO dto) {
        Destino destino = new Destino();
        destino.setNome(dto.nome);
        destino.setLocalizacao(dto.localizacao);
        destino.setDescricao(dto.descricao);
        repository.save(destino);
        return destino;
    }

    public Destino atualizarDestino(Long id, DestinoDTO dto) {
        Optional<Destino> destinoEntity =repository.findById(id);
        if (!destinoEntity.isPresent()) {
            return null;
        }
        Destino destino = destinoEntity.get();
        destino.setId(id);
        destino.setNome(dto.nome);
        destino.setLocalizacao(dto.localizacao);
        destino.setDescricao(dto.descricao);
        return repository.save(destino);
    }

    public List<Destino> listarTodos() {
        return repository.findAll().stream().toList();
    }
//
    public List<Destino> pesquisar(String termo) {
        return repository.findByNomeContainingIgnoreCaseOrLocalizacaoContainingIgnoreCase(termo, termo);
    }

    public Optional<Destino> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Destino> avaliar(Long id, AvaliacaoDTO dto) {
        return repository.findById(id).map(destino -> {
            double novaNota = ((destino.getNotaMedia() * destino.getTotalAvaliacoes()) + dto.nota) / (destino.getTotalAvaliacoes() + 1);
            destino.setNotaMedia(novaNota);
            destino.setTotalAvaliacoes(destino.getTotalAvaliacoes() + 1);
            return repository.save(destino);
        });
    }

    public boolean excluir(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
//
//    public Destino atualizarDestino(long id, DestinoDTO dto) {
//        Optional<Destino> destino = destinos.stream().filter(d -> d.getId().equals(id)).findFirst();
//        if (!destino.isPresent()) {
//            return null;
//        }
//        destino.get().setNome(dto.nome);
//        destino.get().setLocalizacao(dto.localizacao);
//        destino.get().setDescricao(dto.descricao);
//        return destino.get();
//    }
//
//    public List<Destino> pesquisar(String termo) {
//        return destinos.stream()
//            .filter(d -> d.getNome().toLowerCase().contains(termo.toLowerCase()) ||
//                         d.getLocalizacao().toLowerCase().contains(termo.toLowerCase()))
//            .collect(Collectors.toList());
//    }
//
//    public Optional<Destino> buscarPorId(Long id) {
//        return destinos.stream().filter(d -> d.getId().equals(id)).findFirst();
//    }
//
//    public boolean excluir(Long id) {
//        return destinos.removeIf(d -> d.getId().equals(id));
//    }
//
//    public Optional<Destino> avaliar(Long id, AvaliacaoDTO dto) {
//        Optional<Destino> opt = buscarPorId(id);
//        opt.ifPresent(destino -> {
//            double soma = destino.getMediaNotas() * destino.getTotalAvaliacoes();
//            destino.setTotalAvaliacoes(destino.getTotalAvaliacoes() + 1);
//            double novaMedia = (soma + dto.nota) / destino.getTotalAvaliacoes();
//            destino.setMediaNotas(novaMedia);
//        });
//        return opt;
//    }
}
