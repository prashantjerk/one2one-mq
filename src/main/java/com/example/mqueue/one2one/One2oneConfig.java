package com.example.mqueue.one2one;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("one2one")
@Configuration
public class One2oneConfig {

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("direct");
    }

    @Bean
    public Queue queue1() {
        return new Queue("Q1");
    }

    @Bean
    public Queue queue2() {
        return new Queue("Q2");
    }
}