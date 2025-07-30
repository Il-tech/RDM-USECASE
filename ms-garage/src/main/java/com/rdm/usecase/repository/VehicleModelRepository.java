package com.rdm.usecase.repository;

import com.rdm.usecase.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleModelRepository extends JpaRepository<VehicleModel,Long> {
    VehicleModel findByCode(String Code);
}
