
package com.rdm.usecase.service;

import com.rdm.usecase.dtos.VehicleDto;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleServiceImplTest {

    @Mock
    private GarageValidator garageValidator;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private GarageRepository garageRepository;
    @Mock
    private VehicleModelRepository vehicleModelRepository;
    @Mock
    private VehicleMapper vehicleMapper;
    @Mock
    private VehicleEventPublisher vehicleEventPublisher;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddVehicleToGarage_success() {
        VehicleDto dto = new VehicleDto();
        dto.setGarageId(1L);
        dto.setModelId(2L);

        Garage garage = new Garage();
        VehicleModel model = new VehicleModel();
        Vehicle vehicle = new Vehicle();
        Vehicle saved = new Vehicle();

        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));
        when(vehicleModelRepository.findById(2L)).thenReturn(Optional.of(model));
        when(vehicleMapper.toEntity(dto, garage, model)).thenReturn(vehicle);
        when(vehicleRepository.save(vehicle)).thenReturn(saved);
        when(vehicleMapper.toDto(saved)).thenReturn(dto);
        doNothing().when(vehicleEventPublisher).publishVehicleCreated(any());

        VehicleDto result = vehicleService.addVehicleToGarage(dto);

        assertEquals(dto, result);
        verify(garageValidator).validateGarageAndQuota(garage);
    }

    @Test
    void testUpdateVehicle_success() {
        VehicleDto dto = new VehicleDto();
        dto.setId(1L);
        dto.setGarageId(1L);
        dto.setModelId(2L);

        Vehicle vehicle = new Vehicle();
        Garage garage = new Garage();
        VehicleModel model = new VehicleModel();

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));
        when(vehicleModelRepository.findById(2L)).thenReturn(Optional.of(model));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.toDto(vehicle)).thenReturn(dto);

        VehicleDto result = vehicleService.updateVehicleInGarage(dto);

        assertEquals(dto, result);
        verify(vehicleMapper).updateVehicle(vehicle, dto, garage, model);
    }

    @Test
    void testDeleteVehicleFromGarage_success() {
        Garage garage = new Garage();
        garage.setId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setGarage(garage);

        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        assertDoesNotThrow(() -> vehicleService.deleteVehicleFromGarage(1L, 1L));
        verify(vehicleRepository).delete(vehicle);
    }

    @Test
    void testGetVehiclesByGarage_success() {
        Garage garage = new Garage();
        garage.setId(1L);

        Vehicle vehicle = new Vehicle();
        VehicleDto dto = new VehicleDto();

        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));
        when(vehicleRepository.findByGarage(garage)).thenReturn(List.of(vehicle));
        when(vehicleMapper.toDto(vehicle)).thenReturn(dto);

        List<VehicleDto> result = vehicleService.getVehiclesByGarage(1L);
        assertEquals(1, result.size());
    }

    @Test
    void testGetVehiclesByModel_success() {
        VehicleModel model = new VehicleModel();
        Vehicle vehicle = new Vehicle();
        VehicleDto dto = new VehicleDto();

        when(vehicleModelRepository.findById(2L)).thenReturn(Optional.of(model));
        when(vehicleRepository.findByModel(model)).thenReturn(List.of(vehicle));
        when(vehicleMapper.toDto(vehicle)).thenReturn(dto);

        List<VehicleDto> result = vehicleService.getVehiclesByModelAcrossGarages(2L);
        assertEquals(1, result.size());
    }

    @Test
    void testGetVehiclesByModel_modelNotFound() {
        when(vehicleModelRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> vehicleService.getVehiclesByModelAcrossGarages(99L));
    }
}
