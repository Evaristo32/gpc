package br.com.gpc.controller;

import br.com.gpc.dto.UsuarioDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "api/v1/usuario")
@RequiredArgsConstructor
@RestController
public class UsuarioController {

    private Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;

    @ApiOperation(value = "Cria um usuário.")
    @RequestMapping( method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws NegocioException {
        this.logger.info("Method cadastrarUsuario.");
        return ResponseEntity.ok(usuarioService.cadastrarUsuario(usuarioDTO));
    }

    @ApiOperation(value = "Buscar todos os usuários.")
    @RequestMapping( method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsuarioDTO>> buscarUsuariso() {
        this.logger.info("Method buscarUsuariso.");
        return ResponseEntity.ok(this.usuarioService.buscarTodosUsuarios());
    }


}
