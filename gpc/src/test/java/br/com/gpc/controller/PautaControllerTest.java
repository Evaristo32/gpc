package br.com.gpc.controller;

import br.com.gpc.dto.PautaDTO;
import br.com.gpc.service.PautaService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PautaController.class)
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PautaService pautaService;

    @Test
    public void cadastrarPautaTest() throws Exception {

        when(this.pautaService.cadastrarPauta(any())).thenReturn(PautaDTO.builder().build());
        this.mockMvc.perform(post("/api/v1/pauta")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void associarUsuariosPautaTest() throws Exception {

        when(this.pautaService.associarUsuariosPauta(any())).thenReturn(PautaDTO.builder().build());
        this.mockMvc.perform(put("/api/v1/pauta/associar")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonPut()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void abrirVotacaoTest() throws Exception {
        this.mockMvc.perform(put("/api/v1/pauta/abrir-votacao/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void buscarPautasTest() throws Exception {
        when(this.pautaService.buscarPautas()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/api/v1/pauta"))
                .andExpect(status().isOk());
    }

    private String json() {
        return  " {\n"
                + "      \"tema\": \"12345678909\",\n"
                + "       \"descricao\": \" teste\" \n"
                + "  }";
    }

    private String jsonPut() {
        return  " {\n"
                + "      \"tema\": \"12345678909\",\n"
                + "       \"descricao\": \" teste\", \n"
                + "      \"usuarios\": \n"
                + "[{ \"id\":1,\"cpf\":\"12345678909\"}]"
                + "  }";
    }

}
