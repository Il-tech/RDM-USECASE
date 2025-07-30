package com.rdm.usecase.service;

import com.rdm.usecase.dtos.VehicleModelDto;
import com.rdm.usecase.mapper.VehicleModelMapper;
import com.rdm.usecase.models.VehicleModel;
import com.rdm.usecase.repository.VehicleModelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleModelServiceImpl implements VehicleModelService {

    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleModelMapper vehicleModelMapper;

    @Override
    public VehicleModelDto create(VehicleModelDto dto) {
        VehicleModel model = vehicleModelMapper.toEntity(dto);
        return vehicleModelMapper.toDto(vehicleModelRepository.save(model));
    }

    @Override
    public VehicleModelDto update(Long id, VehicleModelDto dto) {
        VehicleModel existing = vehicleModelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Model not found with ID: " + id));

        existing.setCode(dto.getMarque());

        return vehicleModelMapper.toDto(vehicleModelRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!vehicleModelRepository.existsById(id)) {
            throw new EntityNotFoundException("Model not found with ID: " + id);
        }
        vehicleModelRepository.deleteById(id);
    }

    @Override
    public VehicleModelDto getById(Long id) {
        return vehicleModelRepository.findById(id)
                .map(vehicleModelMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Model not found with ID: " + id));
    }

    @Override
    public List<VehicleModelDto> getAll() {
        return vehicleModelRepository.findAll().stream()
                .map(vehicleModelMapper::toDto)
                .collect(Collectors.toList());
    }
}