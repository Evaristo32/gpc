package br.com.gpc.service.impl;

import br.com.gpc.service.RabbitMqService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RabbitMqServiceImpl implements RabbitMqService {

    private Logger logger = LoggerFactory.getLogger(RabbitMqServiceImpl.class);
    @Value("${mq.exchange.name}")
    public String EXCHANGE_NAME;
    @Value("${mq.queue.name}")
    public String QUEUE_NAME;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;

    @Override
    public void enviarMensagemFilaMq(String order) {
        this.logger.info("Method enviarMensagemFilaMq.");
        this.rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", order);
    }

    @Override
    public void LimparFilaMq() {
        this.logger.info("Method LimparFilaMq.");
        this.rabbitAdmin.purgeQueue(this.QUEUE_NAME, false);
    }
}
