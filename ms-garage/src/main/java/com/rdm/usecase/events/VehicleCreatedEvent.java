package com.rdm.usecase.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class VehicleCreatedEvent {
    private Long vehicleId;
    private String CarburantType;
    private String year;

}
