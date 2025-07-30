package com.rdm.usecase.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
@Embeddable
public class OpeningTime {
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;


    public OpeningTime(LocalTime startTime, LocalTime endTime) {
    }
}
