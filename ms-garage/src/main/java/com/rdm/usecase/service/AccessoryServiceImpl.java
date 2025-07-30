package com.rdm.usecase.service;

import com.rdm.usecase.dtos.AccessoryDto;
import com.rdm.usecase.mapper.AccessoryMapper;
import com.rdm.usecase.models.Accessory;
import com.rdm.usecase.models.Vehicle;
import com.rdm.usecase.models.VehicleModel;
import com.rdm.usecase.repository.AccessoryRepository;
import com.rdm.usecase.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final AccessoryMapper accessoryMapper;
    private final VehicleRepository vehicleRepository;



    @Override
    public AccessoryDto addAccessory(Long vehicleId, AccessoryDto accessoryDto) {
        Vehicle  vehicle = vehicleRepository.findById(vehicleId).orElseThrow(()-> new EntityNotFoundException("Vehicle not found with Id:"+vehicleId));
        Accessory accessory = accessoryMapper.toEntity(accessoryDto,vehicle);
        return accessoryMapper.toDto(accessoryRepository.save(accessory));
    }

    @Override
    public AccessoryDto updateAccessory(Long id, AccessoryDto accessoryDto) {
        Accessory existing = accessoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accessory not found with id " + id));

        if (accessoryDto.getPrix() != null) {
            try {
                existing.setPrix(accessoryDto.getPrix());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid price format: " + accessoryDto.getPrix());
            }
        }

        if (accessoryDto.getDescription() != null) {
            existing.setDescription(accessoryDto.getDescription());
        }

        Accessory updated = accessoryRepository.save(existing);
        return accessoryMapper.toDto(updated);
    }

    @Override
    public void deleteAccessory(Long id) {
        if (!accessoryRepository.existsById(id)) {
            throw new RuntimeException("Accessory not found with id " + id);
        }
        accessoryRepository.deleteById(id);
    }

    @Override
    public List<AccessoryDto> listAccessoriesByVehicleId(Long vehicleId) {
        return accessoryRepository.findByVehicleId(vehicleId).stream()
                .map(accessoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
