package br.com.gpc.controller;

import br.com.gpc.dto.ResultaVotacaoDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.service.ResultadoVotacaoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/v1/resultado-votacao")
@RequiredArgsConstructor
@RestController
public class ResultadoVotacaoController {

    private Logger logger = LoggerFactory.getLogger(ResultadoVotacaoController.class);
    private final ResultadoVotacaoService resultadoVotacaoService;

    @ApiOperation(value = "Buscar o resultado da votação da pauta.")
    @RequestMapping( value ="{idPauta}", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultaVotacaoDTO> buscarResultadoDaVotacao(@PathVariable("idPauta") Long idPauta) throws NegocioException {
        this.logger.info("Method buscarResultadoDaVotacao.");
        return ResponseEntity.ok(this.resultadoVotacaoService.buscarVotacaoPorIdPauta(idPauta));
    }

}
