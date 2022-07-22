package de.teamholycow.acc.resultserver.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Driver {
    // direct driver attributes
    private String firstName;
    private String lastName;
    private String shortName;
    private String playerId;

    // mapped car
    private Car car;
    private DriverStatistic driverStatistic;

    @Builder.Default
    private List<Lap> laps = new ArrayList<>();
}
