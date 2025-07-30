package com.rdm.usecase.repository;

import com.rdm.usecase.dtos.GarageDto;
import com.rdm.usecase.models.Garage;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GarageRepository  extends JpaRepository<Garage, Long>, JpaSpecificationExecutor<Garage> {

}
