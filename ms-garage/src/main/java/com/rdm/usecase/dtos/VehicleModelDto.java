package com.rdm.usecase.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class VehicleModelDto {

        private Long id;
        private String marque;
        private String modele;

}
