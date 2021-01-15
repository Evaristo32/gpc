package br.com.gpc.service.impl;

import br.com.gpc.domain.Pauta;
import br.com.gpc.dto.PautaDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.mapper.PautaMapper;
import br.com.gpc.repository.PautaRepository;
import br.com.gpc.service.PautaService;
import br.com.gpc.util.constant.MensagensExceptionsUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PautaServiceImpl implements PautaService {

    private Logger logger = LoggerFactory.getLogger(PautaServiceImpl.class);
    private final int UM_MINUTOS = 1;
    private final PautaRepository pautaRepository;
    private final PautaMapper pautaMapper;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public PautaDTO cadastrarPauta(PautaDTO pautaDTO) throws NegocioException {
        this.logger.info("Method cadastrarPauta.");
        Optional<Pauta> opPauta = this.pautaRepository.buscarPautaPorTema(pautaDTO.getTema());
        if (opPauta.isPresent()) {
            throw new NegocioException(MensagensExceptionsUtil.TEMA_JA_EXISTE);
        }
        pautaDTO.setResultadoEnviado(Boolean.FALSE);
        return this.pautaMapper.toDto(this.pautaRepository.save(this.pautaMapper.toEntity(pautaDTO)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public PautaDTO associarUsuariosPauta(PautaDTO pautaDTO) throws NegocioException {
        this.logger.info("Method associarUsuariosPauta.");
        if (CollectionUtils.isEmpty(pautaDTO.getUsuarios())) {
            throw new NegocioException(MensagensExceptionsUtil.NENHUM_USUARIO_INFORMADO);
        }
        return this.pautaMapper.toDto(this.pautaRepository.save(this.pautaMapper.toEntity(pautaDTO)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void abrirSessoaParaVotacao(Long idPauta) throws NegocioException {
        this.logger.info("Method abrirSessoaParaVotacao.");
        Pauta pauta = this.pautaRepository.buscarPautaComVotacaoParaAbrir(idPauta)
                .<NegocioException>orElseThrow(() -> {
                    throw new NegocioException(MensagensExceptionsUtil.PAUTA_NAO_ENCOTRADA_OU_VOTACAO_INICIADA);
                });
        if (CollectionUtils.isEmpty(pauta.getUsuarios())) {
            throw new NegocioException(MensagensExceptionsUtil.NENHUM_USUARIO_ASSOCIADO);
        }
        pauta.setDataHoraVotacao(LocalDateTime.now().plusMinutes(this.UM_MINUTOS));
        this.pautaRepository.save(pauta);
    }

    @Override
    public void validarPautaComVotacaoAberta(Long idPauta) throws NegocioException {
        this.logger.info("Method verificarStatusPauta.");
        Pauta pauta = this.pautaRepository.buscarPautaComVotacaoAberta(idPauta)
                .<NegocioException>orElseThrow(() -> {
                    throw new NegocioException(MensagensExceptionsUtil.VOTACAO_FECHADA_OU_INEXISTENTE);
                });
        if (pauta.getDataHoraVotacao().isBefore(LocalDateTime.now())) {
            throw new NegocioException(MensagensExceptionsUtil.VOTACAO_ENCERRADA);
        }
    }

    @Override
    public List<PautaDTO> buscarPautas() {
        this.logger.info("Method buscarPautas.");
        return this.pautaMapper.toDto(this.pautaRepository.buscarTodasPautas());
    }

    @Override
    public PautaDTO buscarPautaPorId(Long idPauta) {
        this.logger.info("Method buscarPautaPorId.");
        Pauta pauta = this.pautaRepository.buscarPorId(idPauta).<NegocioException>orElseThrow(() -> {
            throw new NegocioException(MensagensExceptionsUtil.PAUTA_NAO_ENCONTRADA);
        });
        return this.pautaMapper.toDto(pauta);
    }

    @Override
    public List<Pauta> buscarPautasParaEmitirResultado(LocalDateTime dataHoraAtual) {
        this.logger.info("Method buscarPautasParaEmitirResultado.");
        return this.pautaRepository.buscarTodasPautasComVotacaoEncerrada(Boolean.FALSE)
                .stream()
                .filter(pauta -> pauta.getDataHoraVotacao().isBefore(dataHoraAtual))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void tornaPautasEmitidas(Pauta pauta) {
        this.logger.info("Method tornaPautaEmitida.");
        pauta.setResultadoEnviado(Boolean.TRUE);
        this.pautaRepository.save(pauta);
    }
}
