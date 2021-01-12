package br.com.gpc.service;

import br.com.gpc.dto.UsuarioDTO;
import br.com.gpc.exceptions.NegocioException;

import java.util.List;

public interface UsuarioService {

    UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) throws NegocioException;

    List<UsuarioDTO> buscarTodosUsuarios();
}
