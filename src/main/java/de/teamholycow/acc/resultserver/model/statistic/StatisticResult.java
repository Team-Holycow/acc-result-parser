package de.teamholycow.acc.resultserver.model.statistic;

import de.teamholycow.acc.data.SessionType;
import de.teamholycow.acc.data.Track;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;

@Data
public class StatisticResult {
    private SessionType sessionType;
    private Track track;
    private String serverName;
    private LapTime bestLap;
    private boolean wetSession;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private OptionalLong minStopTime = OptionalLong.empty();


    private List<Driver> drivers = new ArrayList<>();
    private Map<Long, Driver> carDriverLookup = new HashMap<>();

    public final Driver getDriver(long carId) {
        return carDriverLookup.get(carId);
    }

}
