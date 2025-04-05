package org.example.paymenttest.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    public static final String CHAT_QUEUE = "chat_queue";
    public static final String CHAT_EXCHANGE = "chat_exchange";
    public static final String CHAT_ROUTING_KEY = "chat_routing_key";

    @Bean
    public Queue chatQueue() {
        return QueueBuilder.durable(CHAT_QUEUE).build();
    }

    @Bean
    public TopicExchange chatExchange(){
        return new TopicExchange(CHAT_EXCHANGE, true, false);
    }

    @Bean
    public Binding chatBinding(Queue chatQueue, TopicExchange chatExchange){
        return BindingBuilder
                .bind(chatQueue)
                .to(chatExchange)
                .with(CHAT_ROUTING_KEY);
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }



    @Bean
    public ApplicationRunner runner(AmqpAdmin amqpAdmin) {
        return args -> {
            amqpAdmin.declareQueue(chatQueue());
            amqpAdmin.declareExchange(chatExchange());
            amqpAdmin.declareBinding(chatBinding(chatQueue(), chatExchange()));
            System.out.println("✅ RabbitMQ 설정 완료");
        };
    }

}
