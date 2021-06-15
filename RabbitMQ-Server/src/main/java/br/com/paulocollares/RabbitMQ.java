package br.com.paulocollares;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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

        Queue queue3 = new Queue("teste.fila.topic.1", true, false, false, arguments);
        amqpAdmin.declareQueue(queue3);
        Queue queue4 = new Queue("teste.fila.topic.2", true, false, false, arguments);
        amqpAdmin.declareQueue(queue4);
        TopicExchange topicExchange = new TopicExchange("teste.exchange.topic");
        amqpAdmin.declareExchange(topicExchange);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue3).to(topicExchange).with("*.orange.*"));
        amqpAdmin.declareBinding(BindingBuilder.bind(queue4).to(topicExchange).with("lazy.#"));

        Queue queue5 = new Queue("teste.fila.rpc", true, false, false, arguments);
        amqpAdmin.declareQueue(queue5);
        DirectExchange directExchange2 = new DirectExchange("teste.exchange.direct.rpc");
        amqpAdmin.declareExchange(directExchange2);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue5).to(directExchange2).with("rpc"));
    }

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

}
