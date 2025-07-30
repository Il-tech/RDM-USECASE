package com.rdm.usecase.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AccessoryDto {
    private Long id;
    private BigDecimal prix;
    private String nom;
    private String description;
    private Long vehicleId;
    private String type;
}
