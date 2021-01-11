package br.com.gpc.repository;

import br.com.gpc.domain.Voto;
import br.com.gpc.enums.StatusVotoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    @Query(value = "select v from  Voto v where v.pauta.id = :idPauta and v.usuario.id = :idUsuario ")
    Optional<Voto> buscarVotoPelaPautaeVoto(@Param("idPauta") Long idPauta, @Param("idUsuario") Long idUsuario);

    @Query(value = "select count(v)  from  Voto v " +
            " join v.pauta p" +
            " where p.id = :idPauta and p.dataHoraVotacao is not null and v.voto = :tipoVoto ")
    BigDecimal totalDeVotosRealizadosNaPautaPorTipoDoVoto(@Param("idPauta") Long idPauta, @Param("tipoVoto") StatusVotoEnum statusVotoEnum);

    @Query(value = "select count(v) from  Voto v " +
            " join v.pauta p" +
            " where p.id = :idPauta and p.dataHoraVotacao is not null ")
    BigDecimal totalDeVotosRealizadosNaPauta(@Param("idPauta") Long idPauta);
}
