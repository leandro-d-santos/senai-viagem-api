package senai.viagem.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import senai.viagem.api.entities.Destino;

import java.util.List;

public interface DestinoRepository extends JpaRepository<Destino, Long> {
    List<Destino> findByNomeContainingIgnoreCaseOrLocalizacaoContainingIgnoreCase(String nome, String localizacao);
}