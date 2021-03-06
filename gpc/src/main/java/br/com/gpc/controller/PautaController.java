package br.com.gpc.controller;

import br.com.gpc.dto.PautaDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.service.PautaService;
import br.com.gpc.util.constant.MensagensSwaggerUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "api/v1/pauta")
@RequiredArgsConstructor
@RestController
public class PautaController {

    private Logger logger = LoggerFactory.getLogger(PautaController.class);
    private final PautaService pautaService;

    @ApiOperation(value = MensagensSwaggerUtil.CRIAR_PAUTA)
    @RequestMapping( method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PautaDTO> cadastrarPauta(@Valid @RequestBody PautaDTO pautaDTO) throws NegocioException {
        this.logger.info("Method cadastrarPauta.");
        return ResponseEntity.ok(this.pautaService.cadastrarPauta(pautaDTO));
    }

    @ApiOperation(value = MensagensSwaggerUtil.ASSOCIAR_USUARIO_PAUTA)
    @RequestMapping(value = "/associar", method = RequestMethod.PUT, produces= MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PautaDTO> associarUsuariosPauta(@Valid @RequestBody PautaDTO pautaDTO) {
        this.logger.info("Method associarUsuariosPauta.");
        return ResponseEntity.ok(this.pautaService.associarUsuariosPauta(pautaDTO));
    }

    @ApiOperation(value = MensagensSwaggerUtil.ABRIR_VOTACAO)
    @RequestMapping(value = "/abrir-votacao/{idPauta}", method = RequestMethod.PUT, produces= MediaType.APPLICATION_JSON_VALUE)
    public void abrirVotacao(@PathVariable("idPauta") Long idPauta) {
        this.logger.info("Method abrirVotacao.");
        this.pautaService.abrirSessoaParaVotacao(idPauta);
    }

    @ApiOperation(value = MensagensSwaggerUtil.BUSCAR_PAUTAS)
    @RequestMapping( method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PautaDTO>> buscarPautas() {
        this.logger.info("Method buscarPautas.");
        return ResponseEntity.ok(this.pautaService.buscarPautas());
    }

}
