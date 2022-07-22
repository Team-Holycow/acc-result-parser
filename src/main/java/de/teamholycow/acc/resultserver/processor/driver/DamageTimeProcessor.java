package de.teamholycow.acc.resultserver.processor.driver;

import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.statistic.Driver;
import de.teamholycow.acc.resultserver.model.statistic.DriverPitStop;
import de.teamholycow.acc.resultserver.model.statistic.StatisticResult;

import java.util.List;
import java.util.OptionalLong;

public class DamageTimeProcessor implements DriverProcessor {

    @Override
    public void process(StatisticResult statisticResult, Driver driver, JsonResult jsonResult) {
        if (statisticResult.getMinStopTime().isEmpty()) {
            statisticResult.setMinStopTime(OptionalLong.of(getMinPitStopTime(statisticResult.getDrivers())));
        }
        calculateDamageTime(driver.getDriverStatistic().getPitStops(), statisticResult.getMinStopTime().getAsLong());
    }


    private void calculateDamageTime(List<DriverPitStop> pitStops, long minPitStopTime) {
        for (DriverPitStop driverPitStop : pitStops) {
            if (driverPitStop.getStopTime() == minPitStopTime) {
                continue;
            }

            var dmgTime = driverPitStop.getStopTime() - (minPitStopTime * (110.0f / 100.0f));

            driverPitStop.setDmgTime(dmgTime <= 0 ? 0 : Float.valueOf(dmgTime).longValue());
        }
    }

    private long getMinPitStopTime(List<Driver> drivers) {
        if (drivers.size() < 2) {
            return 0;
        }

        return drivers.stream()
                .flatMap(d -> d.getDriverStatistic().getPitStops().stream())
                .filter(p -> p.getRound() > 0)
                .mapToLong(DriverPitStop::getStopTime)
                .min().orElse(0);
    }
}
