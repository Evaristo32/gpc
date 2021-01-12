package br.com.gpc.service.impl;

import br.com.gpc.enums.StatusUsuarioEnum;
import br.com.gpc.domain.Usuario;
import br.com.gpc.dto.ResponseUsuarioDTO;
import br.com.gpc.dto.UsuarioDTO;
import br.com.gpc.client.UsuarioHerokuClient;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.mapper.UsuarioMapper;
import br.com.gpc.repository.UsuarioRepository;
import br.com.gpc.service.UsuarioService;
import br.com.gpc.util.MensagensUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    private final UsuarioHerokuClient usuarioHerokuClient;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) throws NegocioException {
        this.logger.info("Method cadastrarUsuario.");
        String cpf = usuarioDTO.getCpf().replaceAll("[^0-9]+", "");
        usuarioDTO.setCpf(cpf);
        Optional<Usuario> opUsuario = this.usuarioRepository.buscarUsuarioPorCpf(cpf);
        if(opUsuario.isPresent()) {
            throw new NegocioException(MensagensUtil.USUARIO_JA_CADASTRADO);
        }
        ResponseUsuarioDTO responseUsuarioDTO = this.usuarioHerokuClient.validarStatusUsuario(cpf);
        if(responseUsuarioDTO.getStatus().equals(StatusUsuarioEnum.UNABLE_TO_VOTE.getDescricao())) {
            throw new NegocioException(MensagensUtil.CPF_INVALIDO);
        }
        return this.usuarioMapper.toDto(this.usuarioRepository.save(this.usuarioMapper.toEntity(usuarioDTO)));
    }

    @Override
    public List<UsuarioDTO> buscarTodosUsuarios() {
        this.logger.info("Method buscarTodosUsuarios.");
        return this.usuarioMapper.toDto(this.usuarioRepository.findAll());
    }
}
