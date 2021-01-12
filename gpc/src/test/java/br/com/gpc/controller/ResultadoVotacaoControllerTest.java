package br.com.gpc.controller;

import br.com.gpc.dto.ResultaVotacaoDTO;
import br.com.gpc.service.ResultadoVotacaoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ResultadoVotacaoController.class)
public class ResultadoVotacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResultadoVotacaoService resultadoVotacaoService;

    @Test
    public void buscarResultadoDaVotacaoTest() throws Exception {

        when(this.resultadoVotacaoService.buscarVotacaoPorIdPauta(any())).thenReturn(ResultaVotacaoDTO.builder().build());
        this.mockMvc.perform(get("/api/v1/resultado-votacao/1"))
                .andExpect(status().isOk());
    }

}
