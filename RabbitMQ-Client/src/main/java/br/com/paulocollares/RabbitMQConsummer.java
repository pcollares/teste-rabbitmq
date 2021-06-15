package br.com.paulocollares;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author pcollares
 */
@Component
public class RabbitMQConsummer {

    @RabbitListener(queues = "teste.fila.direct")
    public void consumidorDirect(String payload) {
        System.out.println("teste.fila.direct: " + payload);
    }

    @RabbitListener(queues = "teste.fila.fanout.1")
    public void consumidorFanout1(String payload) {
        System.out.println("teste.fila.fanout.1: " + payload);
    }

    @RabbitListener(queues = "teste.fila.fanout.2")
    public void consumidorFanout2(String payload) {
        System.out.println("teste.fila.fanout.2: " + payload);
    }

    @RabbitListener(queues = "teste.fila.topic.1")
    public void consumidorTopic1(String payload) {
        System.out.println("teste.fila.topic.1: " + payload);
    }

    @RabbitListener(queues = "teste.fila.topic.2")
    public void consumidorTopic2(String payload) {
        System.out.println("teste.fila.topic.2: " + payload);
    }

}
