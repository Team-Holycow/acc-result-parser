package de.teamholycow.acc.resultserver.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverPitStop {
    private int round;
    private long stopTime;

    @Builder.Default
    private long dmgTime = 0;

    @Builder.Default
    private PitStopReason pitStopReason = PitStopReason.NORMAL;
}
