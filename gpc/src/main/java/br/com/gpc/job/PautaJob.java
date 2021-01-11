package br.com.gpc.job;

import br.com.gpc.domain.Pauta;
import br.com.gpc.dto.ResultaVotacaoDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.mapper.PautaMapper;
import br.com.gpc.service.PautaService;
import br.com.gpc.service.RabbitMqService;
import br.com.gpc.service.ResultadoVotacaoService;
import br.com.gpc.service.VotoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class PautaJob {

    private Logger logger = LoggerFactory.getLogger(PautaJob.class);

    private final PautaService pautaService;
    private final PautaMapper j;
    private final RabbitMqService rabbitMqService;
    private final VotoService votoService;
    private final ResultadoVotacaoService resultadoVotacaoService;

    @Scheduled(cron = "${schedule.resultado-pauta}")
    public void dispararResultadoPauta() {
        this.logger.info("Method dispararResultadoPauta.");
        this.logger.info("Iniciando emissão dos resultados de pautas encerradas -" + LocalDateTime.now());
        List<Pauta> pautasParaEmissao = this.pautaService.buscarPautasParaEmitirResultado(LocalDateTime.now());
        for (Pauta pauta : pautasParaEmissao) {
            ResultaVotacaoDTO resultaVotacaoDTO = this.votoService.contabilizarVotacaoDaPauta(pauta.getId());
            this.rabbitMqService.enviarMensagemFilaMq(this.parseObjectToJson(resultaVotacaoDTO));
            this.pautaService.tornaPautasEmitidas(pauta);
        }
        this.logger.info("Finalizando emissão dos resultados de pautas encerradas -" + LocalDateTime.now());
    }

    @RabbitListener(queues = "${mq.queue.name}")
    public void recupereMensagemFilaMq(Message message) {
        this.logger.info("Method recupereMensagemFilaMq.");
        this.resultadoVotacaoService.cadastrarVotacao(this.recuperarMensagemFila(message.getBody()));
        this.rabbitMqService.LimparFilaMq();
    }

    private ResultaVotacaoDTO recuperarMensagemFila( byte[] body) {
        this.logger.info("Method recuperarMensagemFila.");
        try {
            return new ObjectMapper().readValue(body, ResultaVotacaoDTO.class);
        } catch (IOException e) {
            throw new NegocioException("Erro ao fazer parse do json para Objetto.", e);
        }
    }

    private String parseObjectToJson(ResultaVotacaoDTO resultados) {
        this.logger.info("Method parseObjectToJson.");
        String jsonString = null;
        try {
            jsonString = new ObjectMapper().writeValueAsString(resultados);
        } catch (JsonProcessingException e) {
           throw new NegocioException("Erro ao fazer parse do objeto para json.", e);
        }
       return jsonString;
    }

}
