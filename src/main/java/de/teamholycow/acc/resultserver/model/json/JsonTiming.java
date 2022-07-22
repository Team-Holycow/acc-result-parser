package de.teamholycow.acc.resultserver.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonTiming {
    private long lastLap;
    private long[] lastSplits;
    private long bestLap;
    private long[] bestSplits;
    private long totalTime;
    private long lapCount;
    private long lastSplitId;
}
