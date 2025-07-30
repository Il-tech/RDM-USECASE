package com.rdm.usecase.mapper;

import com.rdm.usecase.dtos.GarageDto;
import com.rdm.usecase.dtos.OpeningTimeDto;
import com.rdm.usecase.models.Garage;
import com.rdm.usecase.models.OpeningTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GarageMapper {

    @Autowired
    private OpeningTimeMapper openingTimeMapper;

    public GarageDto toDto(Garage garage) {
        if (garage == null) return null;

        GarageDto dto = new GarageDto();
        dto.setId(garage.getId());
        dto.setName(garage.getName());
        dto.setAddress(garage.getAddress());
        dto.setTelephone(garage.getTelephone());
        dto.setEmail(garage.getEmail());

        Map<DayOfWeek, List<OpeningTimeDto>> horaires = garage.getOpeningTimes() .stream()
                .collect(Collectors.groupingBy(
                        OpeningTime::getDayOfWeek,
                        Collectors.mapping(
                                ot -> new OpeningTimeDto(ot.getStartTime(), ot.getEndTime()),
                                Collectors.toList()
                        )
                ));
        dto.setHorairesOuverture(horaires);

        return dto;
    }
    public Garage toEntity(GarageDto dto) {
        if (dto == null) return null;

        Garage garage = new Garage();
        garage.setName(dto.getName());
        garage.setAddress(dto.getAddress());
        garage.setTelephone(dto.getTelephone());
        garage.setEmail(dto.getEmail());

        List<OpeningTime> horaires = new ArrayList<>();

        if (dto.getHorairesOuverture() != null) {
            dto.getHorairesOuverture().forEach((day, openingDtoList) -> {
                List<OpeningTime> times = openingDtoList.stream()
                        .map(openingDto -> new OpeningTime(openingDto.getStartTime(), openingDto.getEndTime()))
                        .collect(Collectors.toList());
                horaires.addAll(times);
            });
        }

        garage.setOpeningTimes(horaires);
        return garage;
    }

}