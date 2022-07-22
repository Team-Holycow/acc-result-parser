package de.teamholycow.acc.resultserver.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Lap {
    private LapTime lapTime;
    private boolean valid;
}
