package com.rdm.usecase.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.rdm.usecase.utils.ApisPaths.RabbitMQ.VEHICLE_EXCHANGE;
import static com.rdm.usecase.utils.ApisPaths.RabbitMQ.VEHICLE_ROUTING_KEY;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleEventPublisher {

    private final RabbitTemplate rabbitTemplate;

   // private final ObjectMapper objectMapper;

    public void publishVehicleCreated(VehicleCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                VEHICLE_EXCHANGE,
                VEHICLE_ROUTING_KEY,
                event
        );

        log.info(" Published vehicle event: " + event);
    }
}