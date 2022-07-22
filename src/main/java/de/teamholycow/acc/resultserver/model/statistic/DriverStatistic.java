package de.teamholycow.acc.resultserver.model.statistic;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DriverStatistic {

    private int laps;
    private long optimalTime;
    private long bestTime;
    private long avgTime;
    private int slowLaps;
    private int invalidLaps;
    private List<DriverPitStop> pitStops = new ArrayList<>();
    private Double consistency;
    private Double consistencyPercentage;

    public int getValidLaps() {
        return laps - invalidLaps;
    }

    public long getPitStopTime() {
        return getPitStops().stream().mapToLong(t -> t.getStopTime() - t.getDmgTime()).sum();
    }

    public long getDamageTime() {
        return getPitStops().stream().mapToLong(DriverPitStop::getDmgTime).sum();
    }
}
