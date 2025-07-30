package com.rdm.usecase.repository;

import com.rdm.usecase.models.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {

    List<Accessory> findByVehicleId(Long id);
}
