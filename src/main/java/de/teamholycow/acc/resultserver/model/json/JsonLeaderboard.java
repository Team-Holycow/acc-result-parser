package de.teamholycow.acc.resultserver.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonLeaderboard {
    private JsonCar car;
    private JsonDriver currentDriver;
    private int currentDriverIndex;
    private JsonTiming timing;
    private int missingMandatoryPitstop;
    private List<Double> driverTotalTimes;
}
