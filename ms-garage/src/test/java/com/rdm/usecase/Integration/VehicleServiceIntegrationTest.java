package com.rdm.usecase.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdm.usecase.dtos.VehicleDto;
import com.rdm.usecase.models.Garage;
import com.rdm.usecase.models.VehicleModel;
import com.rdm.usecase.repository.GarageRepository;
import com.rdm.usecase.repository.VehicleModelRepository;
import com.rdm.usecase.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private VehicleModelRepository vehicleModelRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Garage garage;
    private VehicleModel model;

    @BeforeEach
    void setup() {
        vehicleRepository.deleteAll();
        garageRepository.deleteAll();
        vehicleModelRepository.deleteAll();

        garage = new Garage();
        garage.setName("Garage X");
        garage.setAddress("123 Street");
        garage.setEmail("garage@example.com");
        garage.setTelephone("123456789");
        garageRepository.save(garage);

        model = new VehicleModel();
        model.setCode("CLIO2024");
        vehicleModelRepository.save(model);
    }

    @Test
    void testAddVehicleToGarage() throws Exception {
        VehicleDto dto = new VehicleDto();
        dto.setYear(2024);
        dto.setTypeCarburant("Essence");
        dto.setGarageId(garage.getId());
        dto.setModelId(model.getId());

        mockMvc.perform(post("/api/vehicles/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeCarburant").value("Essence"))
                .andExpect(jsonPath("$.garageId").value(garage.getId()));
    }

    @Test
    void testGetVehiclesByGarage() throws Exception {
        VehicleDto dto = new VehicleDto();
        dto.setYear(2024);
        dto.setTypeCarburant("Essence");
        dto.setGarageId(garage.getId());
        dto.setModelId(model.getId());

        mockMvc.perform(post("/api/vehicles/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/vehicles/garage/" + garage.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].typeCarburant").value("Essence"));
    }
}
