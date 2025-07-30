package com.rdm.usecase.config;

import com.rdm.usecase.models.*;
import com.rdm.usecase.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final GarageRepository garageRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final AccessoryRepository accessoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // 1. Create VehicleModel
        VehicleModel model = new VehicleModel();
        model.setCode("Clio");
      //  model.setMarque("Renault");
        model.setCode("CLIO-2024");
        vehicleModelRepository.save(model);

        // 2. Create Garage
        Garage garage = new Garage();
        garage.setName("Central Garage");
        garage.setAddress("123 Main St");
        garage.setEmail("central@garage.com");
        garage.setTelephone("0123456789");


        List<OpeningTime> openingTimes =  List.of(
                new OpeningTime(LocalTime.of(8, 0), LocalTime.of(17, 0))
        );
        garage.setOpeningTimes(openingTimes);

        garageRepository.save(garage);

        // 3. Create Vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setAnneeFabrication(2023);
        vehicle.setTypeCarburant("Diesel");
        vehicle.setGarage(garage);
        vehicle.setModel(model);
        vehicleRepository.save(vehicle);

        // 4. Create Accessory
        Accessory gps = new Accessory();
        gps.setNom("GPS");
        gps.setDescription("Built-in GPS navigation");
        gps.setPrix(new BigDecimal("150.00"));
        gps.setType("Tech");
        gps.setVehicle(vehicle);
        accessoryRepository.save(gps);

        System.out.println("data loaded: garage, vehicle, model, accessory");
    }
}