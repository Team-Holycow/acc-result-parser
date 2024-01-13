package de.teamholycow.acc.resultserver.processor.driver;

import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.statistic.Driver;
import de.teamholycow.acc.resultserver.model.statistic.Lap;
import de.teamholycow.acc.resultserver.model.statistic.StatisticResult;

import java.util.List;
import java.util.OptionalDouble;

public class ConsistencyDriverProcessor implements DriverProcessor {
    private final static int CONSISTENCY_LAP_OFFSET = 21000;
    private final static boolean IGNORE_FIRST_ROUND = true;
    private final static boolean INCLUDE_ONLY_VALID_ROUNDS = false;

    @Override
    public void process(StatisticResult statisticResult, Driver driver, JsonResult jsonResult) {
        var driverStatistic = driver.getDriverStatistic();

        var consistency = getConsistency(driver.getLaps(), driverStatistic.getBestTime());
        driverStatistic.setConsistency(consistency.orElse(0.0));
        driverStatistic.setConsistencyPercentage(getConsistencyPercentage(driverStatistic.getBestTime(), consistency));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private double getConsistencyPercentage(long bestLap, OptionalDouble consistency) {
        if (consistency.isEmpty()) {
            return 0.0;
        }

        var consistencyPercentage = 100 * (bestLap / consistency.getAsDouble());
        return consistencyPercentage > 100 ? 100 : consistencyPercentage < 0 ? 0 : consistencyPercentage;
    }

    public OptionalDouble getConsistency(List<Lap> laps, long bestLap) {
        // Not enough laps
        if (laps.stream().filter(Lap::isValid).count() < 3) {
            return OptionalDouble.empty();
        }

        return laps.stream()
                .filter(l -> isValidConsistencyLap(l, bestLap))
                .mapToLong(l -> l.getLapTime().getTime())
                .average();
    }

    private boolean isValidConsistencyLap(Lap lap, long bestLap) {
        if (INCLUDE_ONLY_VALID_ROUNDS && !lap.isValid()) {
            return false;
        }

        if (IGNORE_FIRST_ROUND && 1 == lap.getLapNumber()) {
            return false;
        }

        return lap.getLapTime().getTime() <= bestLap + CONSISTENCY_LAP_OFFSET;
    }
}
