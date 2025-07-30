package com.rdm.usecase.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.rdm.usecase.utils.ApisPaths.RabbitMQ.*;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange vehicleExchange() {
        return new DirectExchange(VEHICLE_EXCHANGE);
    }
    @Bean
    public Queue vehicleQueue() {
        return new Queue(VEHICLE_QUEUE);
    }
    @Bean
    public Binding bindingVehicleQueue(Queue vehicleQueue, DirectExchange vehicleExchange) {
        return BindingBuilder.bind(vehicleQueue).to(vehicleExchange).with(VEHICLE_ROUTING_KEY);
    }
}
