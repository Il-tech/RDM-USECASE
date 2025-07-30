package com.rdm.usecase.service;


import com.rdm.usecase.dtos.VehicleModelDto;

import java.util.List;

public interface VehicleModelService {

        VehicleModelDto create(VehicleModelDto dto);

        VehicleModelDto update(Long id, VehicleModelDto dto);

        void delete(Long id);

        VehicleModelDto getById(Long id);

        List<VehicleModelDto> getAll();

}
