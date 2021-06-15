package br.com.paulocollares;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author pcollares
 */
@Component
public class RabbitMQConsummer {

    @RabbitListener(queues = "teste.fila.rpc")
    public String consumidorRPC(int id) {
        String json = "{'timestamp':" + System.currentTimeMillis() + ", 'id': "+id+"}";
        return json;
    }

}
