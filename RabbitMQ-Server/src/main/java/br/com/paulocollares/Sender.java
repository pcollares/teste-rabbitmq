package br.com.paulocollares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author pcollares
 */
@Component
@EnableAsync
public class Sender {

    @Autowired
    RabbitMQ rabbitMQ;

    @Async
    @Scheduled(fixedDelay = 1_000, initialDelay = 1_000)
    public void enviar() {
        //envia para um exchange direct, para uma unica fila
        rabbitMQ.getRabbitTemplate().convertAndSend("teste.fila.direct", System.currentTimeMillis());

        //envia para um exchange fanout, para duas filas
       // rabbitMQ.getRabbitTemplate().convertAndSend("teste.exchange.fanout", "",System.currentTimeMillis());
       
       //Envia para um exchange topic
//       rabbitMQ.getRabbitTemplate().convertAndSend("teste.exchange.topic","qq.orange.qq","orange");
//       rabbitMQ.getRabbitTemplate().convertAndSend("teste.exchange.topic","lazy.qq.qq","lazy");
    }

}
