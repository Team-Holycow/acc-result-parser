package de.teamholycow.acc.resultserver.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCar {
    private long carId;
    private int RaceNumber;
    private int carModel;
    private int cupCategory;
    private String carGroup;
    private String teamName;
    private int nationality;
    private long carGuid;
    private long teamGuid;
    private List<JsonDriver> drivers;
}
