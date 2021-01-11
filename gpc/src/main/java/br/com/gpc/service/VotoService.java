package br.com.gpc.service;

import br.com.gpc.dto.ResultaVotacaoDTO;
import br.com.gpc.dto.VotoDTO;
import br.com.gpc.exceptions.NegocioException;

public interface VotoService {

    VotoDTO cadastrarVoto(VotoDTO votoDTO) throws NegocioException;

    ResultaVotacaoDTO contabilizarVotacaoDaPauta(Long idPauta) throws NegocioException;

}
