package com.example.mqueue.one2one;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"one2one", "message_queue"})
@Configuration
public class One2oneConfig {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Profile("receiver")
    @Bean
    public One2oneReceiver receiver() {
        return new One2oneReceiver();
    }

    @Profile("sender")
    @Bean
    public One2oneSender sender() {
        return new One2oneSender();
    }
}