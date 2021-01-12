package br.com.gpc.service.impl;

import br.com.gpc.dto.ResultaVotacaoDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.mapper.ResultadoVotacaoMapper;
import br.com.gpc.repository.ResultadoVotacaoRepository;
import br.com.gpc.service.ResultadoVotacaoService;
import br.com.gpc.util.MensagensUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ResultadoVotacaoServiceImpl implements ResultadoVotacaoService {

    private Logger logger = LoggerFactory.getLogger(ResultadoVotacaoServiceImpl.class);
    private final ResultadoVotacaoRepository resultadoVotacaoRepository;
    private final ResultadoVotacaoMapper resultadoVotacaoMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ResultaVotacaoDTO cadastrarVotacao(ResultaVotacaoDTO resultaVotacaoDTO) {
        this.logger.info("Method cadastrarVotacao.");
        return this.resultadoVotacaoMapper.toDto(
                this.resultadoVotacaoRepository.save(this.resultadoVotacaoMapper.toEntity(resultaVotacaoDTO))
        );
    }

    @Override
    public ResultaVotacaoDTO buscarVotacaoPorIdPauta(Long idPauta) throws NegocioException {
        this.logger.info("Method buscarVotacaoPorIdPauta.");
        return this.resultadoVotacaoMapper.toDto(
                this.resultadoVotacaoRepository.findById(idPauta).orElseThrow(() -> {
                    throw new NegocioException(MensagensUtil.PAUTA_SEM_VOTACAO);
                }));
    }
}
