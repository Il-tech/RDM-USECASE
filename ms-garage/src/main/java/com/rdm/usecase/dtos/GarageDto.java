package com.rdm.usecase.dtos;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
public class GarageDto {
    private Long id;
    private String name;
    private String address;
    private String telephone;
    private String email;
    private Map<DayOfWeek, List<OpeningTimeDto>> horairesOuverture;
}

