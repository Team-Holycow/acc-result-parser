package de.teamholycow.acc.resultserver.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonLap {
    private long carId;
    private int driverIndex;
    private long laptime;

    @JsonProperty(value = "isValidForBest")
    private boolean validForBest;
    private long[] splits;
}
