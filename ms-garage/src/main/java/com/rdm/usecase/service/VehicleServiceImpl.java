package com.rdm.usecase.service;

import com.rdm.usecase.dtos.VehicleDto;
import com.rdm.usecase.events.VehicleCreatedEvent;
import com.rdm.usecase.events.VehicleEventPublisher;
import com.rdm.usecase.mapper.VehicleMapper;
import com.rdm.usecase.models.Garage;
import com.rdm.usecase.models.Vehicle;
import com.rdm.usecase.models.VehicleModel;
import com.rdm.usecase.repository.GarageRepository;
import com.rdm.usecase.repository.VehicleModelRepository;
import com.rdm.usecase.repository.VehicleRepository;
import com.rdm.usecase.validation.GarageValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final GarageValidator garageValidator;
    private final VehicleRepository vehicleRepository;
    private final GarageRepository garageRepository;
    private final VehicleMapper vehicleMapper;
   private final VehicleEventPublisher vehicleEventPublisher;
    private final VehicleModelRepository vehicleModelRepository;

    @Override
    public VehicleDto addVehicleToGarage(VehicleDto vehicleDto)  {
        Garage garage = garageRepository.findById(vehicleDto.getGarageId())
                .orElseThrow(() -> new EntityNotFoundException("Garage not found with ID: " + vehicleDto.getGarageId()));

        garageValidator.validateGarageAndQuota(garage);

        VehicleModel vehicleModel = vehicleModelRepository.findById(vehicleDto.getModelId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle model not found with ID: " + vehicleDto.getModelId()));

        Vehicle vehicle = vehicleMapper.toEntity(vehicleDto, garage, vehicleModel);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        VehicleCreatedEvent event = VehicleCreatedEvent.builder()
                .vehicleId(savedVehicle.getId())
                .CarburantType(savedVehicle.getTypeCarburant())
                .year(String.valueOf(savedVehicle.getAnneeFabrication()))
                .build();

        vehicleEventPublisher.publishVehicleCreated(event);
        return vehicleMapper.toDto(savedVehicle);
    }

    @Override
    public VehicleDto updateVehicleInGarage(VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with ID: " + vehicleDto.getId()));

        Garage garage = garageRepository.findById(vehicleDto.getGarageId())
                .orElseThrow(() -> new EntityNotFoundException("Garage not found with ID: " + vehicleDto.getGarageId()));

        VehicleModel vehicleModel = vehicleModelRepository.findById(vehicleDto.getModelId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle model not found with ID: " + vehicleDto.getModelId()));

        vehicleMapper.updateVehicle(vehicle, vehicleDto, garage, vehicleModel);
        return vehicleMapper.toDto(vehicleRepository.save(vehicle));
    }

    @Override
    public void deleteVehicleFromGarage(Long garageId, Long vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with ID: " + vehicleId));

        if (!vehicle.getGarage().getId().equals(garageId)) {
            throw new IllegalStateException("Vehicle does not belong to the given garage.");
        }

        vehicleRepository.delete(vehicle);
    }

    @Override
    public List<VehicleDto> getVehiclesByGarage(Long garageId) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new EntityNotFoundException("Garage not found with ID: " + garageId));

        return vehicleRepository.findByGarage(garage).stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDto> getVehiclesByModelAcrossGarages(Long  modelCode) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(modelCode).orElseThrow(()->
                new EntityNotFoundException("Vehicle model not found with code: " + modelCode));

        return vehicleRepository.findByModel(vehicleModel).stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }
}