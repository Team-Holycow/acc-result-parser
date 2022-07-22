package de.teamholycow.acc.resultserver.model.penalty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class LeagueResult {
    private int raceNumber;
    private String firstName;
    private String lastName;
    private int position;
    private int newPosition;
    private long lastLapTime;
    private long laps;

    @Builder.Default
    private long penalty = 0;

    public long getFinalLastLapTime() {
        return lastLapTime + penalty;
    }
}
