package br.com.gpc.service;

import br.com.gpc.service.impl.RabbitMqServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations="classpath:application.yml")
public class RabbitMqTest extends Assertions {

    @InjectMocks
    private RabbitMqServiceImpl rabbitMqService;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private RabbitAdmin rabbitAdmin;

    @Test
    public void enviarMensagemFilaMqTest() {
       this.rabbitMqService.enviarMensagemFilaMq("Teste");
    }

    @Test
    public void LimparFilaMqTest() {
        this.rabbitMqService.LimparFilaMq();
    }
}
