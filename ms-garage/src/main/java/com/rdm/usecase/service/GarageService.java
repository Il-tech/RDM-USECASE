package com.rdm.usecase.service;

import com.rdm.usecase.dtos.GarageDto;
import com.rdm.usecase.models.Garage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GarageService {

    Garage createGarage(Garage garage);

    GarageDto updateGarage(Garage garage);

    void deleteGarage(Long id);

    Garage getGarageById(Long id);

    Page<Garage> filterGarage(Pageable pageable);

    List<Garage> findGaragesByVehicleTypeOrAccessoryName(String vehicleType, String accessoryName);
}