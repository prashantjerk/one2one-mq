package com.example.mqueue.one2one;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Profile("receiver")
@Service
public class One2oneReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private DirectExchange exchange;
    private Queue queue1;
    private Queue queue2;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    public One2oneReceiver(DirectExchange exchange, @Qualifier("queue1") Queue queue1, @Qualifier("queue2") Queue queue2) {
        this.exchange = exchange;
        this.queue1 = queue1;
        this.queue2 = queue2;
    }

    @PostConstruct
    private void binding() {
        amqpAdmin.declareQueue(queue1);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue1).to(exchange).with("key1"));
        amqpAdmin.declareQueue(queue2);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue2).to(exchange).with("key2"));

        new Thread(this::sendLoop).start();
    }

    @RabbitListener(queues = "Q1")
    public void receive(String message) {
        System.out.println("Received: " + message);
    }

    private void sendLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a message to reply or type 'exit' to quit.");

        while (true) {
            String message = scanner.nextLine();
            if ("exit".equalsIgnoreCase(message)) {
                System.out.println("Receiver exited!");
                break;
            }
            this.rabbitTemplate.convertAndSend("direct", "key2", message);
            System.out.println("Sent: " + message);
        }
        scanner.close();
     }
}
