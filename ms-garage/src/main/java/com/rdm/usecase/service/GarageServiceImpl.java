package com.rdm.usecase.service;

import com.rdm.usecase.dtos.GarageDto;
import com.rdm.usecase.mapper.GarageMapper;
import com.rdm.usecase.models.Garage;
import com.rdm.usecase.repository.GarageRepository;
import com.rdm.usecase.repository.Specification.GarageSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;
    private final GarageMapper garageMapper;

    @Override
    public Garage createGarage(Garage garage) {
        return garageRepository.save(garage);
    }

    @Override
    public GarageDto updateGarage(Garage garage) {
        Garage existing = garageRepository.findById(garage.getId())
                .orElseThrow(() -> new EntityNotFoundException("Garage not found with ID: " + garage.getId()));

        existing.setName(garage.getName());
        existing.setAddress(garage.getAddress());
        existing.setTelephone(garage.getTelephone());
        existing.setEmail(garage.getEmail());
        existing.setOpeningTimes(garage.getOpeningTimes());

        Garage updated = garageRepository.save(existing);
        return garageMapper.toDto(updated);
    }

    @Override
    public void deleteGarage(Long id) {
        if (!garageRepository.existsById(id)) {
            throw new EntityNotFoundException("Garage not found with ID: " + id);
        }
        garageRepository.deleteById(id);
    }

    @Override
    public Garage getGarageById(Long id) {
        return garageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garage not found with ID: " + id));
    }

    @Override
    public Page<Garage> filterGarage(Pageable pageable) {
        return garageRepository.findAll(pageable);
    }

    @Override
    public List<Garage> findGaragesByVehicleTypeOrAccessoryName(String vehicleType, String accessoryName) {
        Specification<Garage> spec = GarageSpecification.hasVehicleTypeCarburant(vehicleType)
                .or(GarageSpecification.hasAccessoryWithName(accessoryName));
        return garageRepository.findAll(spec);
    }
}