package com.rdm.usecase.mapper;

import com.rdm.usecase.dtos.OpeningTimeDto;
import com.rdm.usecase.models.OpeningTime;
import org.springframework.stereotype.Component;

@Component
public class OpeningTimeMapper {

        public OpeningTimeDto toDto(OpeningTime entity) {
            if (entity == null) return null;

            OpeningTimeDto dto = new OpeningTimeDto();
            dto.setStartTime(entity.getStartTime());
            dto.setEndTime(entity.getEndTime());
            return dto;
        }

        public OpeningTime toEntity(OpeningTimeDto dto) {
            if (dto == null) return null;

            OpeningTime entity = new OpeningTime();
            entity.setStartTime(dto.getStartTime());
            entity.setEndTime(dto.getEndTime());
            return entity;
        }

}
