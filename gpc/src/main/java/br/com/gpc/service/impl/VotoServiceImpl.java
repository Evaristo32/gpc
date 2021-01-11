package br.com.gpc.service.impl;

import br.com.gpc.domain.Voto;
import br.com.gpc.dto.PautaDTO;
import br.com.gpc.dto.ResultaVotacaoDTO;
import br.com.gpc.dto.VotoDTO;
import br.com.gpc.enums.StatusVotoEnum;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.mapper.VotoMapper;
import br.com.gpc.repository.VotoRepository;
import br.com.gpc.service.PautaService;
import br.com.gpc.service.VotoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VotoServiceImpl implements VotoService {

    private Logger logger = LoggerFactory.getLogger(VotoServiceImpl.class);
    private final VotoMapper votoMapper;
    private final VotoRepository votoRepository;
    private final PautaService pautaService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public VotoDTO cadastrarVoto(VotoDTO votoDTO) throws NegocioException {
        this.logger.info("Method cadastrarVoto.");
        this.pautaService.validarPautaComVotacaoAberta(votoDTO.getPauta().getId());
        validarVotoDoUsuario(votoDTO);
        return this.votoMapper.toDto(this.votoRepository.save(this.votoMapper.toEntity(votoDTO)));
    }

    @Override
    public ResultaVotacaoDTO contabilizarVotacaoDaPauta(Long idPauta) throws NegocioException {
        this.logger.info("Method contabilizarVotacaoDaPauta.");
        validarTempoDeSesaoFinalizado(idPauta);
        BigDecimal quantidadeVotosTotal = this.votoRepository.totalDeVotosRealizadosNaPauta(idPauta);
        if (quantidadeVotosTotal.intValue() == 0) {
            throw new NegocioException("A pauta informada não possui votos para serem apurados!");
        }
        BigDecimal quantidadeVotosFavoravel = this.votoRepository.totalDeVotosRealizadosNaPautaPorTipoDoVoto(idPauta, StatusVotoEnum.SIM);
        BigDecimal quantidadeVotosOpostos = this.votoRepository.totalDeVotosRealizadosNaPautaPorTipoDoVoto(idPauta, StatusVotoEnum.NAO);
        return ResultaVotacaoDTO.builder()
                .pauta(PautaDTO.builder().id(idPauta).build())
                .votosFavoraveis(quantidadeVotosFavoravel.intValue())
                .votosOpostos(quantidadeVotosOpostos.intValue())
                .totalDeVotos(quantidadeVotosTotal.intValue())
                .mensagem(calcularResultadoFinal(quantidadeVotosFavoravel, quantidadeVotosOpostos))
                .build();
    }

    private String calcularResultadoFinal(BigDecimal quantidadeVotosFavoravel, BigDecimal quantidadeVotosOpostos) {
        this.logger.info("Method calcularResultadoFinal.");
        String resultadoFinal = "";
        int resultadoComparado = quantidadeVotosFavoravel.compareTo(quantidadeVotosOpostos);
        switch (resultadoComparado) {
            case -1:
                resultadoFinal = "A pauta não foi aprovada pois a quantidade de votos negativos foi superior aos positivos.";
                break;
            case 0:
                resultadoFinal = "Votação da pauta empatada.";
                break;
            case 1:
                resultadoFinal = "A pauta foi aprovada pois a quantidade de votos positivos foi superior aos negativos.";
                break;
            default:
                break;

        }
        return resultadoFinal;
    }

    private void validarVotoDoUsuario(VotoDTO votoDTO) {
        this.logger.info("Method validarVotoDoUsuario.");
        Optional<Voto> opVoto = this.votoRepository.buscarVotoPelaPautaeVoto(votoDTO.getPauta().getId(), votoDTO.getUsuario().getId());
        if (opVoto.isPresent()) {
            throw new NegocioException("O usuário não pode realizar mais de um voto em uma única pauta!");
        }
    }

    private void validarTempoDeSesaoFinalizado(Long idPauta) {
        this.logger.info("Method validarTempoDeSesaoFinalizado.");
        PautaDTO pautaDTO = this.pautaService.buscarPautaPorId(idPauta);
        if (pautaDTO.getDataHoraVotacao().isAfter(LocalDateTime.now())) {
            throw new NegocioException("A pauta informada ainda não terminou a sessão de votação!");
        }
    }

}
