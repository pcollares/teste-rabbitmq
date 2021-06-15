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

    int start = 0;

    @Async
    @Scheduled(fixedDelay = 1_000, initialDelay = 1_000)
    public void enviar() {
        int c = ++start;

        //Envia para um rpc
        System.out.println("RPC #" + c);
        String json = (String) rabbitMQ.getRabbitTemplate().convertSendAndReceive("teste.exchange.direct.rpc", "rpc", 1);
        System.out.println(json);
        System.out.println("\\RPC #" + c);
    }

}
