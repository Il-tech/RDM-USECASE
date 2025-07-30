package com.rdm.usecase.repository.Specification;

import com.rdm.usecase.models.Accessory;
import com.rdm.usecase.models.Garage;
import com.rdm.usecase.models.Vehicle;
import com.rdm.usecase.models.VehicleModel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class GarageSpecification {

    public static Specification<Garage> hasVehicleTypeCarburant(String typeCarburant) {
        return (root, query, cb) -> {
            Join<Garage, Vehicle> vehicles = root.join("vehicles");
            return cb.equal(vehicles.get("typeCarburant"), typeCarburant);
        };
    }

    public static Specification<Garage> hasAccessoryWithName(String accessoryName) {
        return (root, query, cb) -> {
            Join<Garage, Vehicle> vehicles = root.join("vehicles");
            Join<Vehicle, Accessory> accessories = vehicles.join("accessories");
            return cb.equal(accessories.get("nom"), accessoryName);
        };
    }
}