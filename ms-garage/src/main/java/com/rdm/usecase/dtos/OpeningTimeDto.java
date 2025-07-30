package com.rdm.usecase.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class OpeningTimeDto {

        private LocalTime startTime;
        private LocalTime endTime;
    }


