package de.teamholycow.acc.resultserver.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonPenalty {
    private long carId;
    private int driverIndex;
    private String reason;
    private String penalty;
    private int penaltyValue;
    private int violationInLap;
    private int clearedInLap;
}
