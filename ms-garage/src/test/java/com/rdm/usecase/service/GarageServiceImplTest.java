
package com.rdm.usecase.service;

import com.rdm.usecase.dtos.GarageDto;
import com.rdm.usecase.mapper.GarageMapper;
import com.rdm.usecase.models.Garage;
import com.rdm.usecase.repository.GarageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GarageServiceImplTest {

    @Mock
    private GarageRepository garageRepository;

    @Mock
    private GarageMapper garageMapper;

    @InjectMocks
    private GarageServiceImpl garageService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGarage() {
        Garage garage = new Garage();
        garage.setName("Test Garage");

        when(garageRepository.save(garage)).thenReturn(garage);

        Garage result = garageService.createGarage(garage);

        assertEquals("Test Garage", result.getName());
        verify(garageRepository).save(garage);
    }

    @Test
    void testUpdateGarage_success() {
        Garage garage = new Garage();
        garage.setId(1L);
        garage.setName("Updated Garage");

        Garage existing = new Garage();
        existing.setId(1L);
        existing.setName("Old Garage");

        when(garageRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(garageRepository.save(existing)).thenReturn(existing);

        GarageDto dto = new GarageDto();
        dto.setName("Updated Garage");

        when(garageMapper.toDto(existing)).thenReturn(dto);

        GarageDto result = garageService.updateGarage(garage);

        assertEquals("Updated Garage", result.getName());
        verify(garageRepository).save(existing);
    }

    @Test
    void testUpdateGarage_notFound() {
        Garage garage = new Garage();
        garage.setId(99L);

        when(garageRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> garageService.updateGarage(garage));
    }

    @Test
    void testDeleteGarage_success() {
        when(garageRepository.existsById(1L)).thenReturn(true);
        garageService.deleteGarage(1L);
        verify(garageRepository).deleteById(1L);
    }

    @Test
    void testDeleteGarage_notFound() {
        when(garageRepository.existsById(2L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> garageService.deleteGarage(2L));
    }

    @Test
    void testGetGarageById_success() {
        Garage garage = new Garage();
        garage.setId(1L);

        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));

        Garage result = garageService.getGarageById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetGarageById_notFound() {
        when(garageRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> garageService.getGarageById(2L));
    }

    @Test
    void testFilterGarage() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Garage> page = new PageImpl<>(List.of(new Garage()));
        when(garageRepository.findAll(pageable)).thenReturn(page);

        Page<Garage> result = garageService.filterGarage(pageable);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testFindGaragesByVehicleTypeOrAccessoryName() {
        Specification<Garage> spec = mock(Specification.class);
        List<Garage> expectedList = List.of(new Garage());

        when(garageRepository.findAll(any(Specification.class))).thenReturn(expectedList);

        List<Garage> result = garageService.findGaragesByVehicleTypeOrAccessoryName("Diesel", "GPS");
        assertEquals(1, result.size());
    }
}
