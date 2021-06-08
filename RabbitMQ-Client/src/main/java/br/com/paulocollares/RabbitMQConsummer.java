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
    public void getQueue(String payload) {
        System.out.println(payload);
    }

}
