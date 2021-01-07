package br.com.gpc.controller;

import br.com.gpc.dto.PautaDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/v1/pauta")
@RequiredArgsConstructor
@RestController
public class PautaController {

    private Logger logger = LoggerFactory.getLogger(PautaController.class);
    private final PautaService pautaService;

    @RequestMapping( method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PautaDTO> cadastrarPauta(@RequestBody PautaDTO pautaDTO) throws NegocioException {
        this.logger.info("Method cadastrarPauta.");
        return ResponseEntity.ok(pautaService.cadastrarPauta(pautaDTO));
    }


}