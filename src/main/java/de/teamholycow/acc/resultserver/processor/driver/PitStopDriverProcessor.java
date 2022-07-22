package de.teamholycow.acc.resultserver.processor.driver;

import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.statistic.Driver;
import de.teamholycow.acc.resultserver.model.statistic.DriverPitStop;
import de.teamholycow.acc.resultserver.model.statistic.Lap;
import de.teamholycow.acc.resultserver.model.statistic.StatisticResult;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PitStopDriverProcessor implements DriverProcessor {
    public static final int PIT_STOP_IDENTIFIER = 2000;

    @Override
    public void process(StatisticResult statisticResult, Driver driver, JsonResult jsonResult) {
        var driverStatistic = driver.getDriverStatistic();
        driverStatistic.setPitStops(getPitTime(driver.getLaps(), driverStatistic.getConsistencyPercentage()));
    }

    public List<DriverPitStop> getPitTime(List<Lap> laps, double consistencyFactor) {
        List<DriverPitStop> driverPitStopList = new ArrayList<>();

        var sectorOneBest = laps.stream().mapToLong(l -> l.getLapTime().getSectorOne()).min().orElse(0);
        var sectorTwoBest = laps.stream().mapToLong(l -> l.getLapTime().getSectorTwo()).min().orElse(0);
        var sectorThreeBest = laps.stream().mapToLong(l -> l.getLapTime().getSectorThree()).min().orElse(0);

        var sectorOneAvg = laps.stream().mapToLong(l -> l.getLapTime().getSectorOne()).filter(t -> t < +sectorOneBest + 2000).average().orElse(0);
        var sectorTwoAvg = laps.stream().mapToLong(l -> l.getLapTime().getSectorTwo()).filter(t -> t < +sectorTwoBest + 2000).average().orElse(0);
        var sectorThreeAvg = laps.stream().mapToLong(l -> l.getLapTime().getSectorThree()).filter(t -> t < +sectorThreeBest + 2000).average().orElse(0);

        for (int i = 0; i < laps.size(); i++) {
            //last round
            if (i + 1 >= laps.size()) {
                continue;
            }

            var lastSector = laps.get(i).getLapTime().getSectorThree();
            var firstSectorNextRound = laps.get(i + 1).getLapTime().getSectorOne();

            if (lastSector >= (sectorThreeBest + PIT_STOP_IDENTIFIER) && firstSectorNextRound >= (sectorOneBest + PIT_STOP_IDENTIFIER)) {
                var pitTime = (lastSector - sectorThreeAvg) + (firstSectorNextRound - sectorOneAvg);
                if (pitTime < +10000) {
                    continue;
                }

                log.info("found pitstop in round {} with a time of {}s", i, pitTime);
                driverPitStopList.add(DriverPitStop.builder()
                        .round(i)
                        .stopTime(Double.valueOf(pitTime).longValue())
                        .build());
            }

        }

        return driverPitStopList;
    }
}
