package com.rdm.usecase.validation;

import com.rdm.usecase.exceptions.QuotaExceededException;
import com.rdm.usecase.models.Garage;
import com.rdm.usecase.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import static com.rdm.usecase.utils.Consts.MAX_VEHICLES_PER_GARAGE;

@Service
public class GarageValidator {

    private final VehicleRepository vehicleRepository;
  //  private static final int MAX_VEHICLES_PER_GARAGE = 50;

    public GarageValidator(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void validateGarageAndQuota(Garage garage) {
        long count = vehicleRepository.countByGarageId(garage.getId());
        if (count >= MAX_VEHICLES_PER_GARAGE) {
            throw new QuotaExceededException("Quota de 50 véhicules atteint pour ce garage");
        }
    }
}

