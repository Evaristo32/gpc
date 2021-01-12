package br.com.gpc.repository;

import br.com.gpc.domain.ResultaVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface ResultadoVotacaoRepository extends JpaRepository<ResultaVotacao, Long> {

}
