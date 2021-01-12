package br.com.gpc.repository;

import br.com.gpc.domain.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    @Query(value = "select p from  Pauta p join fetch p.usuarios where p.id = :idPauta")
    Optional<Pauta> buscarPorId(@Param("idPauta") Long idPauta);

    @Query(value = "select p from  Pauta p where upper( p.tema ) = upper(:tema)")
    Optional<Pauta> buscarPautaPorTema(@Param("tema") String tema);

    @Query(value = "select p from  Pauta p where p.id = :idPauta and p.dataHoraVotacao is null ")
    Optional<Pauta> buscarPautaComVotacaoParaAbrir(@Param("idPauta") Long idPauta);

    @Query(value = "select p from  Pauta p where p.id = :idPauta and p.dataHoraVotacao is not null ")
    Optional<Pauta> buscarPautaComVotacaoAberta(@Param("idPauta") Long idPauta);

    @Query(value = "select p from  Pauta p join fetch p.usuarios u")
    List<Pauta> buscarTodasPautas();

    @Query(value = "select distinct p from  Pauta p " +
            " join fetch p.usuarios u" +
            " where p.resultadoEnviado = :resultadoEnviado"
    )
    List<Pauta> buscarTodasPautasComVotacaoEncerrada(@Param("resultadoEnviado") Boolean resultadoEnviado);
}
