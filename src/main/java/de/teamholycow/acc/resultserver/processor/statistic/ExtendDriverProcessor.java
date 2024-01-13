package de.teamholycow.acc.resultserver.processor.statistic;

import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.statistic.Driver;
import de.teamholycow.acc.resultserver.model.statistic.Lap;
import de.teamholycow.acc.resultserver.model.statistic.LapTime;
import de.teamholycow.acc.resultserver.model.statistic.StatisticResult;

import java.util.concurrent.atomic.AtomicInteger;

public class ExtendDriverProcessor implements StatisticProcessor {
	@Override
	public void process(StatisticResult statisticResult, JsonResult jsonResult) {
		AtomicInteger lapNumber = new AtomicInteger(1);
		jsonResult.getLaps().forEach(jsonLap -> {
			Driver driver = statisticResult.getCarDriverLookup().get(jsonLap.getCarId());
			driver.getLaps().add(Lap.builder()
					.lapNumber(lapNumber.getAndIncrement())
					.lapTime(LapTime.builder()
							.time(jsonLap.getLaptime())
							.sectorOne(jsonLap.getSplits()[0])
							.sectorTwo(jsonLap.getSplits()[1])
							.sectorThree(jsonLap.getSplits()[2])
							.build())
					.valid(jsonLap.isValidForBest())
					.build());
		});
	}
}
