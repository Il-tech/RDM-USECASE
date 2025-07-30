
package com.rdm.usecase.service;

import com.rdm.usecase.dtos.AccessoryDto;
import com.rdm.usecase.mapper.AccessoryMapper;
import com.rdm.usecase.models.Accessory;
import com.rdm.usecase.models.Vehicle;
import com.rdm.usecase.repository.AccessoryRepository;
import com.rdm.usecase.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccessoryServiceImplTest {

    @Mock
    private AccessoryRepository accessoryRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private AccessoryMapper accessoryMapper;

    @InjectMocks
    private AccessoryServiceImpl accessoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAccessory_success() {
        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        AccessoryDto dto = new AccessoryDto();
        Accessory accessory = new Accessory();
        Accessory saved = new Accessory();

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(accessoryMapper.toEntity(dto, vehicle)).thenReturn(accessory);
        when(accessoryRepository.save(accessory)).thenReturn(saved);
        when(accessoryMapper.toDto(saved)).thenReturn(dto);

        AccessoryDto result = accessoryService.addAccessory(vehicleId, dto);

        assertEquals(dto, result);
    }

    @Test
    void testAddAccessory_vehicleNotFound() {
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
                accessoryService.addAccessory(99L, new AccessoryDto()));
    }

    @Test
    void testUpdateAccessory_success() {
        Long id = 1L;
        Accessory accessory = new Accessory();
        accessory.setId(id);

        AccessoryDto dto = new AccessoryDto();
        dto.setPrix(BigDecimal.valueOf(100));
        dto.setDescription("Updated Desc");

        when(accessoryRepository.findById(id)).thenReturn(Optional.of(accessory));
        when(accessoryRepository.save(accessory)).thenReturn(accessory);
        when(accessoryMapper.toDto(accessory)).thenReturn(dto);

        AccessoryDto result = accessoryService.updateAccessory(id, dto);

        assertEquals("Updated Desc", result.getDescription());
    }

    @Test
    void testUpdateAccessory_notFound() {
        when(accessoryRepository.findById(88L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () ->
                accessoryService.updateAccessory(88L, new AccessoryDto()));
    }

    @Test
    void testDeleteAccessory_success() {
        when(accessoryRepository.existsById(1L)).thenReturn(true);
        accessoryService.deleteAccessory(1L);
        verify(accessoryRepository).deleteById(1L);
    }

    @Test
    void testDeleteAccessory_notFound() {
        when(accessoryRepository.existsById(2L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> accessoryService.deleteAccessory(2L));
    }

    @Test
    void testListAccessoriesByVehicleId() {
        Accessory accessory = new Accessory();
        AccessoryDto dto = new AccessoryDto();

        when(accessoryRepository.findByVehicleId(1L)).thenReturn(List.of(accessory));
        when(accessoryMapper.toDto(accessory)).thenReturn(dto);

        List<AccessoryDto> result = accessoryService.listAccessoriesByVehicleId(1L);

        assertEquals(1, result.size());
    }
}
