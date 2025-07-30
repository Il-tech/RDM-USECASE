package com.rdm.usecase.mapper;

import com.rdm.usecase.dtos.VehicleDto;
import com.rdm.usecase.models.Garage;
import com.rdm.usecase.models.Vehicle;
import com.rdm.usecase.models.VehicleModel;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleDto toDto(Vehicle entity) {
        if (entity == null) return null;

        VehicleDto dto = new VehicleDto();
        dto.setId(entity.getId());
        dto.setModelId(entity.getModel().getId());
        dto.setYear(entity.getAnneeFabrication());
        dto.setTypeCarburant(entity.getTypeCarburant());
        dto.setGarageId(entity.getGarage().getId());
        return dto;
    }

    public Vehicle toEntity(VehicleDto dto, Garage garage, VehicleModel model) {
        if (dto == null || garage == null) return null;

        Vehicle vehicle = new Vehicle();

        vehicle.setModel(model);
        vehicle.setAnneeFabrication(dto.getYear());
        vehicle.setTypeCarburant(dto.getTypeCarburant());
        vehicle.setGarage(garage);
        return vehicle;
    }public void updateVehicle(Vehicle entity, VehicleDto dto, Garage garage, VehicleModel model) {
        if (dto == null || entity == null) return;

        if (dto.getYear() != null) {
            entity.setAnneeFabrication(dto.getYear());
        }

        if (dto.getTypeCarburant() != null) {
            entity.setTypeCarburant(dto.getTypeCarburant());
        }

        if (garage != null) {
            entity.setGarage(garage);
        }

        if (model != null) {
            entity.setModel(model);
        }
    }


}