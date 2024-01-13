package de.teamholycow.acc.resultserver.processor.driver;

import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.statistic.Driver;
import de.teamholycow.acc.resultserver.model.statistic.DriverStatistic;
import de.teamholycow.acc.resultserver.model.statistic.Lap;
import de.teamholycow.acc.resultserver.model.statistic.StatisticResult;

import java.util.List;
import java.util.OptionalDouble;

public class BasicDriverStatisticProcessor implements DriverProcessor {
	private static final int SLOW_TIME_OFFSET = 10000;

	@Override
	public void process(StatisticResult statisticResult, Driver driver, JsonResult jsonResult) {
		DriverStatistic driverStatistic = new DriverStatistic();

		driverStatistic.setLaps(driver.getLaps().size());
		driverStatistic.setInvalidLaps((int) driver.getLaps().stream().filter(l -> !l.isValid()).count());

		var avgLapTime = calcAvgTime(driver.getLaps());
		var bestLapTime = calcBestTime(driver.getLaps());
		var optimalTime = calcOptimalTime(driver.getLaps());

		driverStatistic.setAvgTime(avgLapTime);
		driverStatistic.setBestTime(bestLapTime);
		driverStatistic.setOptimalTime(optimalTime);
		driverStatistic.setSlowLaps(calcSlowLaps(driver.getLaps(), avgLapTime));

		driver.setDriverStatistic(driverStatistic);
	}

	private long calcOptimalTime(List<Lap> laps) {
		var sector1 = laps.stream().filter(Lap::isValid).mapToLong(l -> l.getLapTime().getSectorOne()).min().orElse(-1);
		var sector2 = laps.stream().filter(Lap::isValid).mapToLong(l -> l.getLapTime().getSectorTwo()).min().orElse(-1);
		var sector3 = laps.stream().filter(Lap::isValid).mapToLong(l -> l.getLapTime().getSectorThree()).min().orElse(-1);

		if (sector1 > 0 && sector2 > 0 && sector3 > 0) {
			return sector1 + sector2 + sector3;
		}

		return -1;
	}

	private long calcBestTime(List<Lap> laps) {
		return laps.stream().filter(Lap::isValid).mapToLong(l -> l.getLapTime().getTime()).min().orElse(0);
	}

	private long calcAvgTime(List<Lap> laps) {
		OptionalDouble avgTime = laps.stream()
				.filter(Lap::isValid)
				.mapToLong(l -> l.getLapTime().getTime())
				.average();

		return avgTime.isEmpty() ? -1 : Double.valueOf(avgTime.getAsDouble()).longValue();
	}

	private int calcSlowLaps(List<Lap> laps, long avgTime) {
		return (int) laps.stream()
				.filter(lap -> avgTime + SLOW_TIME_OFFSET < lap.getLapTime().getTime())
				.count();
	}
}
