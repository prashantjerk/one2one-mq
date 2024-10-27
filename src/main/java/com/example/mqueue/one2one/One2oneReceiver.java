package com.example.mqueue.one2one;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("receiver")
@Service
public class One2oneReceiver {
    private DirectExchange exchange;

    private Queue queue;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    public One2oneReceiver(DirectExchange exchange, Queue queue) {
        this.exchange = exchange;
        this.queue = queue;
    }

    @PostConstruct
    private void binding() {
        amqpAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("one2one");
        amqpAdmin.declareBinding(binding);
    }

    @RabbitListener(queues = "one2oneQ")
    public void receive(String message) {
        System.out.println("[x] Received '" + message + "'");
    }
}
