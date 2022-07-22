package de.teamholycow.acc.resultserver.processor.statistic;

import de.teamholycow.acc.data.CarClass;
import de.teamholycow.acc.data.CarModel;
import de.teamholycow.acc.data.CupCategory;
import de.teamholycow.acc.data.Nationality;
import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.statistic.Car;
import de.teamholycow.acc.resultserver.model.statistic.Driver;
import de.teamholycow.acc.resultserver.model.statistic.StatisticResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDriverProcessor implements StatisticProcessor {
    @Override
    public void process(StatisticResult statisticResult, JsonResult jsonResult) {
        List<Driver> drivers = new ArrayList<>();
        Map<Long, Driver> carDriverLookup = new HashMap<>();

        jsonResult.getSessionResult().getLeaderBoardLines().forEach(jsonLeaderboard -> {
            Car car = Car.builder()
                    .carId(jsonLeaderboard.getCar().getCarId())
                    .carModel(CarModel.get(jsonLeaderboard.getCar().getCarModel()))
                    .carClass(CarClass.get(jsonLeaderboard.getCar().getCarGroup()))
                    .cupCategory(CupCategory.get(jsonLeaderboard.getCar().getCupCategory()))
                    .nationality(Nationality.get(jsonLeaderboard.getCar().getNationality()))
                    .raceNumber(jsonLeaderboard.getCar().getRaceNumber())
                    .teamName(jsonLeaderboard.getCar().getTeamName())
                    .build();

            Driver driver = Driver.builder()
                    .playerId(jsonLeaderboard.getCurrentDriver().getPlayerId())
                    .firstName(jsonLeaderboard.getCurrentDriver().getFirstName())
                    .lastName(jsonLeaderboard.getCurrentDriver().getLastName())
                    .shortName(jsonLeaderboard.getCurrentDriver().getShortName())
                    .car(car)
                    .build();

            drivers.add(driver);
            carDriverLookup.put(car.getCarId(), driver);
        });

        statisticResult.setDrivers(drivers);
        statisticResult.setCarDriverLookup(carDriverLookup);
    }
}
