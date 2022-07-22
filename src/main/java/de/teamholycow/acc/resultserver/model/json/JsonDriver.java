package de.teamholycow.acc.resultserver.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonDriver {
    private String firstName;
    private String lastName;
    private String shortName;
    private String playerId;
}
