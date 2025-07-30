package com.rdm.usecase.events;

import com.rdm.usecase.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.rdm.usecase.utils.ApisPaths.RabbitMQ.VEHICLE_QUEUE;

@Component
@Slf4j
public class VehicleEventListener {

    @RabbitListener(queues = VEHICLE_QUEUE)
    public void handleVehicleCreated(VehicleCreatedEvent event) {
        log.info("📥 Received VehicleCreatedEvent: ID={}, fuelType={}, year={}",
                String.valueOf(event.getVehicleId()), event.getCarburantType(), event.getYear());
    }
}