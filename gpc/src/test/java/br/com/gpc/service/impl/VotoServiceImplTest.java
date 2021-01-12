package br.com.gpc.service.impl;

import br.com.gpc.domain.Voto;
import br.com.gpc.dto.PautaDTO;
import br.com.gpc.dto.UsuarioDTO;
import br.com.gpc.dto.VotoDTO;
import br.com.gpc.enums.StatusVotoEnum;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.mapper.VotoMapper;
import br.com.gpc.repository.VotoRepository;
import br.com.gpc.service.PautaService;
import br.com.gpc.util.MensagensUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VotoServiceImplTest extends Assertions {

    @InjectMocks
    private VotoServiceImpl votoServiceImpl;
    @Mock
    private VotoMapper votoMapper;
    @Mock
    private VotoRepository votoRepository;
    @Mock
    private PautaService pautaService;


    @Test
    public void cadastrarVotoTest() {

        when(this.votoMapper.toDto((Voto) any())).thenReturn(VotoDTO.builder().id(1l).build());
        when(this.votoMapper.toEntity((VotoDTO) any())).thenReturn(Voto.builder().id(1l).build());
       assertNotNull(this.votoServiceImpl.cadastrarVoto(VotoDTO.builder()
               .pauta(PautaDTO.builder().id(1l).build())
               .usuario(UsuarioDTO.builder().id(1l).build()).build()));
    }

    @Test
    public void cadastrarVotoExceptionTest() {
        when(this.votoRepository.buscarVotoPelaPautaeVoto(any(), any())).thenReturn(Optional.of(Voto.builder().id(1l).build()));
        NegocioException negocioException = assertThrows(NegocioException.class, () -> {
            this.votoServiceImpl.cadastrarVoto(VotoDTO.builder()
                    .pauta(PautaDTO.builder().id(1l).build())
                    .usuario(UsuarioDTO.builder().id(1l).build()).build());
        });
        assertEquals(negocioException.getMessage(), MensagensUtil.USUARIO_COM_VOTO_REALIZADO);
    }

    @Test
    public void contabilizarVotacaoDaPautaAprovadaTest() {
        when(this.pautaService.buscarPautaPorId(any())).thenReturn(PautaDTO.builder().dataHoraVotacao(LocalDateTime.now().minusHours(1)).build());
        when(this.votoRepository.totalDeVotosRealizadosNaPauta(any())).thenReturn(BigDecimal.TEN);
        when(this.votoRepository.totalDeVotosRealizadosNaPautaPorTipoDoVoto(any(), eq(StatusVotoEnum.SIM))).thenReturn(BigDecimal.TEN);
        when(this.votoRepository.totalDeVotosRealizadosNaPautaPorTipoDoVoto(any(), eq(StatusVotoEnum.NAO))).thenReturn(BigDecimal.ONE);
        String mensagem = this.votoServiceImpl.contabilizarVotacaoDaPauta(1l).getMensagem();
        assertEquals(mensagem, MensagensUtil.PAUTA_APROVADA);
    }

    @Test
    public void contabilizarVotacaoDaPautaReprovadaTest() {
        when(this.pautaService.buscarPautaPorId(any())).thenReturn(PautaDTO.builder().dataHoraVotacao(LocalDateTime.now().minusHours(1)).build());
        when(this.votoRepository.totalDeVotosRealizadosNaPauta(any())).thenReturn(BigDecimal.TEN);
        when(this.votoRepository.totalDeVotosRealizadosNaPautaPorTipoDoVoto(any(), eq(StatusVotoEnum.SIM))).thenReturn(BigDecimal.ONE);
        when(this.votoRepository.totalDeVotosRealizadosNaPautaPorTipoDoVoto(any(), eq(StatusVotoEnum.NAO))).thenReturn(BigDecimal.TEN);
        String mensagem = this.votoServiceImpl.contabilizarVotacaoDaPauta(1l).getMensagem();
        assertEquals(mensagem, MensagensUtil.PAUTA_REPROVADA);
    }

    @Test
    public void contabilizarVotacaoDaPautaEmpatadaTest() {
        when(this.pautaService.buscarPautaPorId(any())).thenReturn(PautaDTO.builder().dataHoraVotacao(LocalDateTime.now().minusHours(1)).build());
        when(this.votoRepository.totalDeVotosRealizadosNaPauta(any())).thenReturn(BigDecimal.TEN);
        when(this.votoRepository.totalDeVotosRealizadosNaPautaPorTipoDoVoto(any(), eq(StatusVotoEnum.SIM))).thenReturn(BigDecimal.ONE);
        when(this.votoRepository.totalDeVotosRealizadosNaPautaPorTipoDoVoto(any(), eq(StatusVotoEnum.NAO))).thenReturn(BigDecimal.ONE);
        String mensagem = this.votoServiceImpl.contabilizarVotacaoDaPauta(1l).getMensagem();
        assertEquals(mensagem, MensagensUtil.PAUTA_EMPATADA);
    }

    @Test
    public void contabilizarVotacaoDaPautaExceptionTest() {
        when(this.pautaService.buscarPautaPorId(any())).thenReturn(PautaDTO.builder().dataHoraVotacao(LocalDateTime.now().minusHours(1)).build());
        when(this.votoRepository.totalDeVotosRealizadosNaPauta(any())).thenReturn(BigDecimal.ZERO);
        NegocioException negocioExceptionPautaSemVoto = assertThrows(NegocioException.class, () -> {
            this.votoServiceImpl.contabilizarVotacaoDaPauta(1l);
        });
        assertEquals(negocioExceptionPautaSemVoto.getMessage(), MensagensUtil.PAUTA_SEM_VOTO_PARA_APURAR);

        when(this.pautaService.buscarPautaPorId(any())).thenReturn(PautaDTO.builder().dataHoraVotacao(LocalDateTime.now().plusMinutes(1)).build());
        NegocioException negocioExceptionVotacaoAberta = assertThrows(NegocioException.class, () -> {
            this.votoServiceImpl.contabilizarVotacaoDaPauta(1l);
        });

        assertEquals(negocioExceptionVotacaoAberta.getMessage(), MensagensUtil.PAUTA_COM_VOTACAO_ABERTA);
    }

}
