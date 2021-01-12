package br.com.gpc.service;

import br.com.gpc.domain.Pauta;
import br.com.gpc.dto.PautaDTO;
import br.com.gpc.exceptions.NegocioException;

import java.time.LocalDateTime;
import java.util.List;

public interface PautaService {

    PautaDTO cadastrarPauta(PautaDTO pautaDTO) throws NegocioException;

    PautaDTO associarUsuariosPauta(PautaDTO pautaDTO) throws NegocioException;

    void abrirSessoaParaVotacao(Long idPauta) throws NegocioException;

    void validarPautaComVotacaoAberta(Long idPauta) throws NegocioException;

    List<PautaDTO> buscarPautas();

    PautaDTO buscarPautaPorId(Long idPauta);

    List<Pauta> buscarPautasParaEmitirResultado(LocalDateTime dataHoraAtual);

    void tornaPautasEmitidas(Pauta pautas);
}
