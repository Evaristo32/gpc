package br.com.gpc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://user-info.herokuapp.com/users/", name = "UsersHerokuClient")
public interface UsersHerokuClient {

    @GetMapping(value = "{cpf}")
    String verificaPossibilidadeDeVotoDoAssociado(@PathVariable("cpf") String cpf);
}
