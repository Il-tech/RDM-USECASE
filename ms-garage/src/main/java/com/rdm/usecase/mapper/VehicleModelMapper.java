package com.rdm.usecase.mapper;

import com.rdm.usecase.dtos.VehicleModelDto;
import com.rdm.usecase.models.VehicleModel;
import org.springframework.stereotype.Component;

@Component
public class VehicleModelMapper {

    public VehicleModelDto toDto(VehicleModel model) {
        if (model == null) return null;

        VehicleModelDto dto = new VehicleModelDto();
        dto.setId(model.getId());
        dto.setMarque(model.getCode());
        return dto;
    }

    public VehicleModel toEntity(VehicleModelDto dto) {
        if (dto == null) return null;

        VehicleModel model = new VehicleModel();
        model.setId(dto.getId());
        model.setCode(dto.getMarque());
        return model;
    }

    public void updateEntityFromDto(VehicleModelDto dto, VehicleModel model) {
        if (dto.getMarque() != null) {
            model.setCode(dto.getMarque());
        }

    }
}
