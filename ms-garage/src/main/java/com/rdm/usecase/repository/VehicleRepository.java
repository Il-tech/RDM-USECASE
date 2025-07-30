package com.rdm.usecase.repository;

import com.rdm.usecase.models.Garage;
import com.rdm.usecase.models.Vehicle;
import com.rdm.usecase.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    List<Vehicle> findByGarage(Garage garage);

    List<Vehicle> findByModel(VehicleModel modele);

    Long countByGarageId(Long id);
}
