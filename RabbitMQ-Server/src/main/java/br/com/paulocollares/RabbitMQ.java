package br.com.paulocollares;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pcollares
 */
@Service
public class RabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final AmqpAdmin amqpAdmin;

    public RabbitMQ(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    public void createQueues() {

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-length", 99);

        Queue queue = new Queue("teste.fila.direct", true, false, false, arguments);
        amqpAdmin.declareQueue(queue);
        DirectExchange directExchange = new DirectExchange("teste.exchange.direct");
        amqpAdmin.declareExchange(directExchange);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).withQueueName());

        Queue queue1 = new Queue("teste.fila.fanout.1", true, false, false, arguments);
        amqpAdmin.declareQueue(queue1);
        Queue queue2 = new Queue("teste.fila.fanout.2", true, false, false, arguments);
        amqpAdmin.declareQueue(queue2);
        FanoutExchange fanoutExchange = new FanoutExchange("teste.exchange.fanout");
        amqpAdmin.declareExchange(fanoutExchange);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue1).to(fanoutExchange));
        amqpAdmin.declareBinding(BindingBuilder.bind(queue2).to(fanoutExchange));

    }

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

}
