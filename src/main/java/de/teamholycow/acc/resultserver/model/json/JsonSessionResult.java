package de.teamholycow.acc.resultserver.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSessionResult {
    private long bestlap;
    private long[] bestSplits;
    private int isWetSession;
    private int type;
    private List<JsonLeaderboard> leaderBoardLines;
}
