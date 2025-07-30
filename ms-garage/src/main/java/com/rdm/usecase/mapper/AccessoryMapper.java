package com.rdm.usecase.mapper;

import com.rdm.usecase.dtos.AccessoryDto;
import com.rdm.usecase.models.Accessory;
import com.rdm.usecase.models.Vehicle;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class AccessoryMapper {

    public AccessoryDto toDto(Accessory entity) {
        if (entity == null) return null;

        AccessoryDto dto = new AccessoryDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setDescription(entity.getDescription());
        dto.setPrix(entity.getPrix());
        dto.setType(entity.getType());
        dto.setVehicleId(entity.getVehicle().getId());
        return dto;
    }

    public Accessory toEntity(AccessoryDto dto, Vehicle vehicle) {
        if (dto == null || vehicle == null) return null;

        Accessory entity = new Accessory();
        entity.setNom(dto.getNom());
        entity.setDescription(dto.getDescription());
        entity.setPrix(dto.getPrix());
        entity.setType(dto.getType());
        entity.setVehicle(vehicle);
        return entity;
    }
}