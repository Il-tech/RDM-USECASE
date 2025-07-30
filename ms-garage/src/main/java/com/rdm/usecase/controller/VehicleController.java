package com.rdm.usecase.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.rdm.usecase.dtos.VehicleDto;
import com.rdm.usecase.service.VehicleService;
import com.rdm.usecase.utils.ApisPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rdm.usecase.utils.ApisPaths.Vehicle.ADD;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/add")
    public ResponseEntity<VehicleDto> addVehicle(@RequestBody VehicleDto vehicleDto) {
        VehicleDto createdVehicle = vehicleService.addVehicleToGarage(vehicleDto);
        return ResponseEntity.ok(createdVehicle);
    }

    @PutMapping()
    public ResponseEntity<VehicleDto> updateVehicle(@RequestBody VehicleDto vehicleDto) {
        VehicleDto updatedVehicle = vehicleService.updateVehicleInGarage(vehicleDto);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{garageId}/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long garageId, @PathVariable Long vehicleId) {
        vehicleService.deleteVehicleFromGarage(garageId, vehicleId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/garage/{garageId}")
    public ResponseEntity<List<VehicleDto>> getVehiclesByGarage(@PathVariable Long garageId) {
        List<VehicleDto> vehicles = vehicleService.getVehiclesByGarage(garageId);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/model/{modelId}")
    public ResponseEntity<List<VehicleDto>> getVehiclesByModel(@PathVariable Long modelId) {
        List<VehicleDto> vehicles = vehicleService.getVehiclesByModelAcrossGarages(modelId);
        return ResponseEntity.ok(vehicles);
    }
}
