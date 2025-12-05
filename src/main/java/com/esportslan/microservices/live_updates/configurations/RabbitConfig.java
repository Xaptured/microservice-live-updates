package com.esportslan.microservices.live_updates.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "tournament.events";
    public static final String QUEUE = "live_updates_queue";
    public static final String ROUTING_PATTERN = "liveupdate.*";

    @Bean
    public TopicExchange tournamentEventsExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue liveUpdatesQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding bindLiveUpdatesQueue(Queue liveUpdatesQueue, TopicExchange tournamentEventsExchange) {
        return BindingBuilder.bind(liveUpdatesQueue).to(tournamentEventsExchange).with(ROUTING_PATTERN);
    }
}
