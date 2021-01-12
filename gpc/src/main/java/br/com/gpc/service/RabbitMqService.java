package br.com.gpc.service;

public interface RabbitMqService {

    void enviarMensagemFilaMq(String order);

    void LimparFilaMq();

}
