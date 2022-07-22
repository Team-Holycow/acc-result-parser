package de.teamholycow.acc.resultserver.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LapTime {
    private long time;
    private long sectorOne;
    private long sectorTwo;
    private long sectorThree;
}
