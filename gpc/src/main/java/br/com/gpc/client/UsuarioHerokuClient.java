package br.com.gpc.client;

import br.com.gpc.dto.ResponseUsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://user-info.herokuapp.com/users/", name = "UsersHerokuClient")
public interface UsuarioHerokuClient {

    @GetMapping(value = "{cpf}")
    ResponseUsuarioDTO validarStatusUsuario(@PathVariable("cpf") String cpf);
}
