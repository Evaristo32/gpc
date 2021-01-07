package br.com.gpc.service;

import br.com.gpc.dto.PautaDTO;
import br.com.gpc.exceptions.NegocioException;

public interface PautaService {

    PautaDTO cadastrarPauta(PautaDTO pautaDTO) throws NegocioException;
}
