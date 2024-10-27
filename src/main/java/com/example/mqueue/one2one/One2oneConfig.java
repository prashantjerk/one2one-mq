package com.example.mqueue.one2one;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"one2one", "message_queue"})
@Configuration
public class One2oneConfig {

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("direct");
    }
    @Bean
    public Queue queue() {
        return new Queue("one2oneQ");
    }
}