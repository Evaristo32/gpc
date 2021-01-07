package br.com.gpc.repository;

import br.com.gpc.domain.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    @Query(value = "select p from  Pauta p where upper( p.tema ) = upper(:tema)")
    Optional<Pauta> buscarPautaPorTema(@Param("tema") String tema);
}
