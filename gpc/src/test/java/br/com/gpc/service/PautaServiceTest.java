package br.com.gpc.service;

import br.com.gpc.domain.Pauta;
import br.com.gpc.domain.Usuario;
import br.com.gpc.dto.PautaDTO;
import br.com.gpc.dto.UsuarioDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.mapper.PautaMapper;
import br.com.gpc.repository.PautaRepository;
import br.com.gpc.service.impl.PautaServiceImpl;
import br.com.gpc.util.MensagensUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest extends Assertions {

    @InjectMocks
    private PautaServiceImpl pautaServiceImpl;
    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private PautaMapper pautaMapper;

    @Test
    public void cadastrarPautaTest() {
        PautaDTO pautaDTO = PautaDTO.builder().tema("teste").descricao("teste").build();
        when(this.pautaRepository.buscarPautaPorTema(any())).thenReturn(Optional.empty());
        when(this.pautaMapper.toDto((Pauta) any())).thenReturn(pautaDTO);
        Pauta pauta = Pauta.builder().id(1l).build();
        when(this.pautaMapper.toEntity((PautaDTO) any())).thenReturn(pauta);
        when(this.pautaRepository.save(any())).thenReturn(pauta);
        assertNotNull(this.pautaServiceImpl.cadastrarPauta(pautaDTO));
    }

    @Test
    public void whenConfigNonVoidRetunMethodToThrowEx_thenExIsThrown() {
        when(this.pautaRepository.buscarPautaPorTema(any())).thenReturn(Optional.of(Pauta.builder().id(1l).build()));
        NegocioException negocioException = assertThrows(NegocioException.class, () -> {
            this.pautaServiceImpl.cadastrarPauta(PautaDTO.builder().build());
        });
        assertEquals(negocioException.getMessage(), MensagensUtil.TEMA_JA_EXISTE);
    }

    @Test
    public void associarUsuariosPautaTest() {
        Set<UsuarioDTO> usuarios = new HashSet<>();
        usuarios.add(UsuarioDTO.builder().id(1l).build());
        PautaDTO build = PautaDTO.builder().id(1l).usuarios(usuarios).build();
        when(this.pautaMapper.toEntity((PautaDTO) any())).thenReturn(Pauta.builder().id(1l).build());
        when(this.pautaMapper.toDto((Pauta) any())).thenReturn(build);
        assertNotNull(this.pautaServiceImpl.associarUsuariosPauta(build));
    }

    @Test
    public void associarUsuariosPautaExceptionTest() {
        Set<UsuarioDTO> usuarios = new HashSet<>();
        usuarios.add(UsuarioDTO.builder().id(1l).build());
        PautaDTO build = PautaDTO.builder().id(1l).usuarios(null).build();
        NegocioException negocioException = assertThrows(NegocioException.class, () -> {
            this.pautaServiceImpl.associarUsuariosPauta(build);
        });
        assertEquals(negocioException.getMessage(), MensagensUtil.NENHUM_USUARIO_INFORMADO);
    }

    @Test
    public void abrirSessoaParaVotacaoTest() throws NegocioException {
        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(Usuario.builder().id(1l).build());
        Pauta build = Pauta.builder().id(1l).usuarios(usuarios).build();
        when(this.pautaRepository.buscarPautaComVotacaoParaAbrir(any())).thenReturn(Optional.of(build));
        this.pautaServiceImpl.abrirSessoaParaVotacao(1l);
    }

    @Test
    public void abrirSessoaParaVotacaoExceptionTest() {

        when(this.pautaRepository.buscarPautaComVotacaoParaAbrir(any())).thenReturn(Optional.empty());
        NegocioException negocioExceptionPauta = assertThrows(NegocioException.class, () -> {
            this.pautaServiceImpl.abrirSessoaParaVotacao(1l);
        });
        assertEquals(negocioExceptionPauta.getMessage(), MensagensUtil.PAUTA_NAO_ENCOTRADA_OU_VOTACAO_INICIADA);

        when(this.pautaRepository.buscarPautaComVotacaoParaAbrir(any())).thenReturn(Optional.of(Pauta.builder().id(1l).usuarios(null).build()));
        NegocioException negocioExceptionUsuario = assertThrows(NegocioException.class, () -> {
            this.pautaServiceImpl.abrirSessoaParaVotacao(1l);
        });

        assertEquals(negocioExceptionUsuario.getMessage(), MensagensUtil.NENHUM_USUARIO_ASSOCIADO);
    }

    @Test
    public void validarPautaComVotacaoAbertaTest() {
        when(this.pautaRepository.buscarPautaComVotacaoAberta(any())).thenReturn(Optional.of(Pauta.builder().dataHoraVotacao(LocalDateTime.now().plusHours(1)).build()));
        this.pautaServiceImpl.validarPautaComVotacaoAberta(1l);
    }

    @Test
    public void validarPautaComVotacaoAbertaExceptionTest() {
        when(this.pautaRepository.buscarPautaComVotacaoAberta(any())).thenReturn(Optional.empty());
        NegocioException negocioExceptionPauta = assertThrows(NegocioException.class, () -> {
            this.pautaServiceImpl.validarPautaComVotacaoAberta(1l);
        });
        assertEquals(negocioExceptionPauta.getMessage(), MensagensUtil.VOTACAO_FECHADA_OU_INEXISTENTE);

        when(this.pautaRepository.buscarPautaComVotacaoAberta(any())).thenReturn(Optional.of(Pauta.builder().dataHoraVotacao(LocalDateTime.now().minusHours(1)).build()));
        NegocioException negocioExceptionVotacao = assertThrows(NegocioException.class, () -> {
            this.pautaServiceImpl.validarPautaComVotacaoAberta(1l);
        });
        assertEquals(negocioExceptionVotacao.getMessage(), MensagensUtil.VOTACAO_ENCERRADA);
    }

    @Test
    public void buscarPautasTest() {
        when(this.pautaRepository.buscarTodasPautas()).thenReturn(Arrays.asList(Pauta.builder().id(1l).build()));
        when(this.pautaMapper.toDto((List<Pauta>) any())).thenReturn(Arrays.asList(PautaDTO.builder().id(1l).build()));
        assertNotNull(this.pautaServiceImpl.buscarPautas());
    }

    @Test
    public void buscarPautaPorIdTest() {
        when(this.pautaRepository.buscarPorId(any())).thenReturn(Optional.of(Pauta.builder().id(1l).build()));
        when(this.pautaMapper.toDto((Pauta) any())).thenReturn(PautaDTO.builder().id(1l).build());
        assertNotNull(this.pautaServiceImpl.buscarPautaPorId(1l));
    }

    @Test
    public void buscarPautaPorIdExceptionTest() {
        when(this.pautaRepository.buscarPorId(any())).thenReturn(Optional.empty());
        NegocioException negocioException = assertThrows(NegocioException.class, () -> {
            this.pautaServiceImpl.buscarPautaPorId(1l);
        });
        assertEquals(negocioException.getMessage(), MensagensUtil.PAUTA_NAO_ENCONTRADA);
    }

    @Test
    public void buscarPautasParaEmitirResultadoTest() {
        when(this.pautaRepository.buscarTodasPautasComVotacaoEncerrada(any())).thenReturn(Arrays.asList(Pauta.builder().dataHoraVotacao(LocalDateTime.now().minusHours(1)).build()));
        assertNotNull(this.pautaServiceImpl.buscarPautasParaEmitirResultado(LocalDateTime.now().plusHours(1)));
    }

    @Test
    public void tornaPautasEmitidasTest() {
        this.pautaServiceImpl.tornaPautasEmitidas(Pauta.builder().build());
    }
}
