package br.com.gpc.controller;

import br.com.gpc.dto.VotoDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.service.VotoService;
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

@RequestMapping(value = "api/v1/voto")
@RequiredArgsConstructor
@RestController
public class VotoController {

    private Logger logger = LoggerFactory.getLogger(VotoController.class);
    private final VotoService votoService;

    @ApiOperation(value = "Computa voto.")
    @RequestMapping( method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VotoDTO> computarVoto(@Valid @RequestBody VotoDTO votoDTO) throws NegocioException {
        this.logger.info("Method computarVoto.");
        return ResponseEntity.ok(this.votoService.cadastrarVoto(votoDTO));
    }

}
