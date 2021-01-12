package br.com.gpc.controller;

import br.com.gpc.dto.VotoDTO;
import br.com.gpc.service.VotoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VotoController.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotoService votoService;

    @Test
    public void computarVotoTest() throws Exception {
        when(this.votoService.cadastrarVoto(any())).thenReturn(VotoDTO.builder().build());
        this.mockMvc.perform(post("/api/v1/voto")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String json() {
     return  "  {"
      + "  \"pauta\":{\n"
      + "      \"id\":1\n"
      + "  },\n"
      + "      \"usuario\":{\n"
      + "      \"id\":1\n"
      + "  },\n"
      + "      \"voto\":\"SIM\"\n"
      + "  }";
    }

}
