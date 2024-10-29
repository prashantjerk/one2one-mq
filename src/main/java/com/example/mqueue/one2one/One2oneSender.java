package com.example.mqueue.one2one;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Profile("sender")
@Service
public class One2oneSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Queue queue;

    @Autowired
    public One2oneSender(@Qualifier("queue1") Queue queue) {
        this.queue = queue;
    }

    public void startChat() {
        new Thread(this::sendLoop).start();
    }

    private void sendLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a message to start or type 'exit' to quit.");

        while (true) {
            String message = scanner.nextLine();
            if ("exit".equalsIgnoreCase(message)) {
                System.out.println("Sender exited!");
                break;
            }
            this.rabbitTemplate.convertAndSend("direct", "key1", message);
            System.out.println("Sent: " + message);
        }
        scanner.close();
    }

    @RabbitListener(queues = "Q2")
    public void receive(String message) {
        System.out.println("Received: " + message);
    }
}
