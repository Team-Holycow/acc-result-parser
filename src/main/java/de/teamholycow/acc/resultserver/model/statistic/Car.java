package de.teamholycow.acc.resultserver.model.statistic;

import de.teamholycow.acc.data.CarClass;
import de.teamholycow.acc.data.CarModel;
import de.teamholycow.acc.data.CupCategory;
import de.teamholycow.acc.data.Nationality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private long carId;
    private int raceNumber;
    private CarModel carModel;
    private CupCategory cupCategory;
    private CarClass carClass;
    private String teamName;
    private Nationality nationality;
}
