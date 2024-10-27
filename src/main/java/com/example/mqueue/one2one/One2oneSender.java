package com.example.mqueue.one2one;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
    public One2oneSender(Queue queue) {
        this.queue = queue;
    }

    public void send() {
        Scanner scanner = new Scanner(System.in);
        String message;

        System.out.println("Type the message or type 'exit' to quit.");

        while (!(message = scanner.nextLine()).equalsIgnoreCase("exit")) {
            this.rabbitTemplate.convertAndSend("direct", "one2one", message);
            System.out.println("[x] Sent '" + message + "'");
            System.out.println("Type the message or type 'exit' to quit.");
        }

        System.out.println("Exited!");
        scanner.close();
    }

}
