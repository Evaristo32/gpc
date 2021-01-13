package br.com.gpc.service.impl;

import br.com.gpc.domain.ResultaVotacao;
import br.com.gpc.dto.ResultaVotacaoDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.mapper.ResultadoVotacaoMapper;
import br.com.gpc.repository.ResultadoVotacaoRepository;
import br.com.gpc.util.constant.MensagensExceptionsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResultadoVotacaoServiceImplTest extends Assertions {

    @InjectMocks
    private ResultadoVotacaoServiceImpl resultadoVotacaoService;
    @Mock
    private ResultadoVotacaoRepository resultadoVotacaoRepository;
    @Mock
    private ResultadoVotacaoMapper resultadoVotacaoMapper;

    @Test
    public void cadastrarVotacaoTest() {
        when( this.resultadoVotacaoRepository.save(any())).thenReturn(ResultaVotacao.builder().id(1l).build());
        when(this.resultadoVotacaoMapper.toDto((ResultaVotacao) any())).thenReturn(ResultaVotacaoDTO.builder().votosFavoraveis(1).build());
        when(this.resultadoVotacaoMapper.toEntity((ResultaVotacaoDTO) any())).thenReturn(ResultaVotacao.builder().votosFavoraveis(1).build());
        assertNotNull(this.resultadoVotacaoService.cadastrarVotacao(ResultaVotacaoDTO.builder().mensagem("teste").build()));
    }

    @Test
    public void buscarVotacaoPorIdPautaTest() {
        when( this.resultadoVotacaoRepository.findById(any())).thenReturn(Optional.of(ResultaVotacao.builder().id(1l).build()));
        when(this.resultadoVotacaoMapper.toDto((ResultaVotacao) any())).thenReturn(ResultaVotacaoDTO.builder().votosFavoraveis(1).build());
        assertNotNull(this.resultadoVotacaoService.buscarVotacaoPorIdPauta(1l));
    }

    @Test
    public void buscarVotacaoPorIdPautaExceptionTest() {
        when( this.resultadoVotacaoRepository.findById(any())).thenReturn(Optional.empty());
        NegocioException negocioException = assertThrows(NegocioException.class, () -> {
            this.resultadoVotacaoService.buscarVotacaoPorIdPauta(1l);
        });
        assertEquals(negocioException.getMessage(), MensagensExceptionsUtil.PAUTA_SEM_VOTACAO);
    }

}
