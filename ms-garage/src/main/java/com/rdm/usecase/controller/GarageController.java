package com.rdm.usecase.controller;

import com.rdm.usecase.dtos.GarageDto;
import com.rdm.usecase.models.Garage;
import com.rdm.usecase.service.GarageService;
import com.rdm.usecase.utils.ApisPaths;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rdm.usecase.utils.ApisPaths.Garage.BY_ID;

@RestController
@RequestMapping(ApisPaths.Garage.GARAGE_PATH)
public class GarageController {
        private final GarageService garageService;

        public GarageController(GarageService garageService) {
            this.garageService = garageService;
        }

        @PostMapping
        public ResponseEntity<Garage> createGarage(@RequestBody Garage garage) {
            return new ResponseEntity<>(garageService.createGarage(garage), HttpStatus.CREATED);
        }

        @PutMapping(BY_ID)
        public ResponseEntity<GarageDto> updateGarage(@RequestBody Garage garage) {
            return ResponseEntity.ok(garageService.updateGarage( garage));
        }

        @DeleteMapping(BY_ID)
        public ResponseEntity<Void> deleteGarage(@PathVariable Long id) {
            garageService.deleteGarage(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping(BY_ID)
        public ResponseEntity<Garage> getGarage(@PathVariable Long id) {
            return ResponseEntity.ok(garageService.getGarageById(id));
        }

        @GetMapping
        public ResponseEntity<Page<Garage>> getAllGarages(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "name") String sortBy,
                @RequestParam(defaultValue = "asc") String direction) {

            Pageable pageable = PageRequest.of(page, size,
                    direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

            return ResponseEntity.ok(garageService.filterGarage(pageable));
        }

    @GetMapping("/search")
    public List<Garage> findGaragesByVehicleTypeOrAccessoryName(
            @RequestParam(name = "vehicleType", required = false) String vehicleType,
            @RequestParam(name ="accessoryName", required = false) String accessoryName) {
        return garageService.findGaragesByVehicleTypeOrAccessoryName(vehicleType, accessoryName);
    }


}
