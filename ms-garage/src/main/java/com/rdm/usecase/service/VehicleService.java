package com.rdm.usecase.service;

import com.rdm.usecase.dtos.VehicleDto;

import java.util.List;

public interface VehicleService {

    VehicleDto addVehicleToGarage(VehicleDto vehicleDto);

    VehicleDto updateVehicleInGarage( VehicleDto vehicleDto);

    void deleteVehicleFromGarage(Long garageId, Long vehicleId);

    List<VehicleDto> getVehiclesByGarage(Long garageId);

    List<VehicleDto> getVehiclesByModelAcrossGarages(Long model);
}
