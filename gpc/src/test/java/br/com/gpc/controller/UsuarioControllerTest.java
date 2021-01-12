package br.com.gpc.controller;

import br.com.gpc.dto.UsuarioDTO;
import br.com.gpc.service.UsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void cadastrarUsuarioTest() throws Exception {
        when(this.usuarioService.cadastrarUsuario(any())).thenReturn(UsuarioDTO.builder().build());
        this.mockMvc.perform(post("/api/v1/usuario")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void buscarUsuariosTest() throws Exception {
        when(this.usuarioService.buscarTodosUsuarios()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/api/v1/usuario"))
                .andExpect(status().isOk());
    }

    private String json() {
     return  " {\n"
      + "      \"cpf\": \"12345678909\"\n"
      + "  }";
    }

}
