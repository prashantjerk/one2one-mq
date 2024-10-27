package com.example.mqueue.one2one;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class One2oneReceiver {
    @RabbitListener(queues = "hello")
    public void receive(String message) {
        System.out.println("[x] Received '" + message + "'");
    }
}
