package br.com.gpc.service;

import br.com.gpc.dto.ResultaVotacaoDTO;
import br.com.gpc.exceptions.NegocioException;

public interface ResultadoVotacaoService {

    ResultaVotacaoDTO cadastrarVotacao(ResultaVotacaoDTO resultaVotacaoDTO);

    ResultaVotacaoDTO buscarVotacaoPorIdPauta(Long idPauta) throws NegocioException;

}
