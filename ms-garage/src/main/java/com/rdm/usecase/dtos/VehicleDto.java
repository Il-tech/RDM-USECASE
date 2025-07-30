package com.rdm.usecase.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class VehicleDto {
     private Long id;
     private String typeCarburant;
     private Long modelId;
     private Integer year;
     private Long garageId;


}
