package de.teamholycow.acc.resultserver.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResult {
    private String sessionType;
    private String trackName;
    private int sessionIndex;
    private int raceWeekendIndex;
    private String metaData;
    private String serverName;
    private JsonSessionResult sessionResult;
    private List<JsonLap> laps;
    private List<JsonPenalty> penalties;
}

