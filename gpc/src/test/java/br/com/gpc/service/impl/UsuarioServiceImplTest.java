package br.com.gpc.service.impl;

import br.com.gpc.client.UsuarioHerokuClient;
import br.com.gpc.domain.Usuario;
import br.com.gpc.dto.ResponseUsuarioDTO;
import br.com.gpc.dto.UsuarioDTO;
import br.com.gpc.enums.StatusUsuarioEnum;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.mapper.UsuarioMapper;
import br.com.gpc.repository.UsuarioRepository;
import br.com.gpc.util.constant.MensagensExceptionsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest extends Assertions {

    @InjectMocks
    private UsuarioServiceImpl usuarioServiceImpl;
    @Mock
    private UsuarioHerokuClient usuarioHerokuClient;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;

    @Test
    public void cadastrarUsuarioTest() {
        when(this.usuarioRepository.buscarUsuarioPorCpf(any())).thenReturn(Optional.empty());
        when(this.usuarioHerokuClient.validarStatusUsuario(any())).thenReturn(ResponseUsuarioDTO.builder().status(StatusUsuarioEnum.ABLE_TO_VOTE.getDescricao()).build());
        when(this.usuarioMapper.toDto((Usuario) any())).thenReturn(UsuarioDTO.builder().id(1l).build());
        when(this.usuarioMapper.toEntity((UsuarioDTO) any())).thenReturn(Usuario.builder().id(1l).build());
        assertNotNull(this.usuarioServiceImpl.cadastrarUsuario(UsuarioDTO.builder().id(1l).cpf("012345678909").build()));
    }


    @Test
    public void cadastrarUsuarioExceptionTest() {
        when(this.usuarioRepository.buscarUsuarioPorCpf(any())).thenReturn(Optional.of(Usuario.builder().id(1l).build()));

        NegocioException negocioExceptionUsuario = assertThrows(NegocioException.class, () -> {
            this.usuarioServiceImpl.cadastrarUsuario(UsuarioDTO.builder().id(1l).cpf("012345678909").build());
        });

        assertEquals(negocioExceptionUsuario.getMessage(), MensagensExceptionsUtil.USUARIO_JA_CADASTRADO);

        when(this.usuarioRepository.buscarUsuarioPorCpf(any())).thenReturn(Optional.empty());
        when(this.usuarioHerokuClient.validarStatusUsuario(any())).thenReturn(ResponseUsuarioDTO.builder().status(StatusUsuarioEnum.UNABLE_TO_VOTE.getDescricao()).build());
        NegocioException negocioExceptionCpf = assertThrows(NegocioException.class, () -> {
            this.usuarioServiceImpl.cadastrarUsuario(UsuarioDTO.builder().id(1l).cpf("012345678909").build());
        });

        assertEquals(negocioExceptionCpf.getMessage(), MensagensExceptionsUtil.CPF_INVALIDO);
    }

    @Test
    public void buscarTodosUsuariosTest() {
        when(this.usuarioRepository.findAll()).thenReturn(Arrays.asList(Usuario.builder().id(1l).build()));
        when(this.usuarioMapper.toDto((List<Usuario>) any())).thenReturn(Arrays.asList(UsuarioDTO.builder().id(1l).build()));
        this.usuarioServiceImpl.buscarTodosUsuarios();
    }

}
