package com.rdm.usecase.controller;

import com.rdm.usecase.dtos.VehicleModelDto;
import com.rdm.usecase.service.VehicleModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicleModel")
@RequiredArgsConstructor
public class VehicleModelController {

    private final VehicleModelService vehicleModelService;

    @PostMapping("/add")
    public ResponseEntity<VehicleModelDto> create(@RequestBody VehicleModelDto dto) {
        return ResponseEntity.ok(vehicleModelService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VehicleModelDto> update(@PathVariable Long id, @RequestBody VehicleModelDto dto) {
        return ResponseEntity.ok(vehicleModelService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleModelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleModelDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleModelService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<VehicleModelDto>> getAll() {
        return ResponseEntity.ok(vehicleModelService.getAll());
    }
}
