package com.rdm.usecase.controller;

import com.rdm.usecase.dtos.AccessoryDto;
import com.rdm.usecase.service.AccessoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rdm.usecase.utils.ApisPaths.Garage;
import static com.rdm.usecase.utils.ApisPaths.Accessory.*;
import static com.rdm.usecase.utils.ApisPaths.Vehicle.ADD;
import static com.rdm.usecase.utils.ApisPaths.Vehicle.BY_ID;

@RestController
@RequestMapping(ACCESSORY_PATH)
public class AccessoryController {

    private final AccessoryService accessoryService;

    public AccessoryController(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    @PostMapping("/vehicle/{vehicleId}")
    public ResponseEntity<AccessoryDto> addAccessory(@PathVariable Long vehicleId,@Valid @RequestBody AccessoryDto accessoryDto) {
        AccessoryDto created = accessoryService.addAccessory(vehicleId,accessoryDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping(BY_ID)
    public ResponseEntity<AccessoryDto> updateAccessory(@PathVariable Long id,
                                                        @Valid @RequestBody AccessoryDto accessoryDto) {
        AccessoryDto updated = accessoryService.updateAccessory(id, accessoryDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Void> deleteAccessory(@PathVariable Long id) {
        accessoryService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<List<AccessoryDto>> listByVehicle(@PathVariable Long vehicleId) {
        List<AccessoryDto> accessories = accessoryService.listAccessoriesByVehicleId(vehicleId);
        return ResponseEntity.ok(accessories);
    }
}
