package com.rdm.usecase.service;
import com.rdm.usecase.dtos.AccessoryDto;

import java.util.List;

public interface AccessoryService {

    AccessoryDto addAccessory(Long vehicleId, AccessoryDto accessoryDto);

    AccessoryDto updateAccessory(Long id, AccessoryDto accessoryDto);

    void deleteAccessory(Long id);

    List<AccessoryDto> listAccessoriesByVehicleId(Long vehicleId);
}
