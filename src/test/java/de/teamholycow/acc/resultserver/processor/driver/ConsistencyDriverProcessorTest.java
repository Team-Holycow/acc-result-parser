package de.teamholycow.acc.resultserver.processor.driver;

import de.teamholycow.acc.resultserver.model.statistic.Driver;
import de.teamholycow.acc.resultserver.model.statistic.DriverStatistic;
import de.teamholycow.acc.resultserver.model.statistic.Lap;
import de.teamholycow.acc.resultserver.model.statistic.LapTime;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

class ConsistencyDriverProcessorTest {
	ConsistencyDriverProcessor consistencyDriverProcessor = new ConsistencyDriverProcessor();

	@Test
	void testSameTime() {
		var driverStatistic = new DriverStatistic();
		driverStatistic.setBestTime(10000);

		var laps = new ArrayList<Lap>();
		laps.add(Lap.builder().lapNumber(1).valid(false).lapTime(LapTime.builder().time(10000).build()).build());
		laps.add(Lap.builder().lapNumber(2).valid(true).lapTime(LapTime.builder().time(10000).build()).build());
		laps.add(Lap.builder().lapNumber(3).valid(true).lapTime(LapTime.builder().time(10000).build()).build());
		laps.add(Lap.builder().lapNumber(4).valid(true).lapTime(LapTime.builder().time(10000).build()).build());
		laps.add(Lap.builder().lapNumber(5).valid(false).lapTime(LapTime.builder().time(10000).build()).build());
		laps.add(Lap.builder().lapNumber(6).valid(true).lapTime(LapTime.builder().time(10000).build()).build());
		laps.add(Lap.builder().lapNumber(7).valid(true).lapTime(LapTime.builder().time(10000).build()).build());
		laps.add(Lap.builder().lapNumber(8).valid(true).lapTime(LapTime.builder().time(10000).build()).build());

		var driver = new Driver();
		driver.setDriverStatistic(driverStatistic);
		driver.setLaps(laps);

		consistencyDriverProcessor.process(null, driver, null);

		assertThat(driver.getDriverStatistic().getConsistency()).isEqualTo(10000.0);
		assertThat(driver.getDriverStatistic().getConsistencyPercentage()).isEqualTo(100.0);
	}

	@Test
	void testDifferentTime() {
		var driverStatistic = new DriverStatistic();
		driverStatistic.setBestTime(10000);

		var laps = new ArrayList<Lap>();
		laps.add(Lap.builder().lapNumber(1).valid(false).lapTime(LapTime.builder().time(10000).build()).build());
		laps.add(Lap.builder().lapNumber(2).valid(true).lapTime(LapTime.builder().time(10100).build()).build());
		laps.add(Lap.builder().lapNumber(3).valid(true).lapTime(LapTime.builder().time(10200).build()).build());
		laps.add(Lap.builder().lapNumber(4).valid(true).lapTime(LapTime.builder().time(10300).build()).build());
		laps.add(Lap.builder().lapNumber(5).valid(false).lapTime(LapTime.builder().time(9900).build()).build());
		laps.add(Lap.builder().lapNumber(6).valid(true).lapTime(LapTime.builder().time(9800).build()).build());
		laps.add(Lap.builder().lapNumber(7).valid(true).lapTime(LapTime.builder().time(9700).build()).build());
		laps.add(Lap.builder().lapNumber(8).valid(true).lapTime(LapTime.builder().time(10000).build()).build());

		var driver = new Driver();
		driver.setDriverStatistic(driverStatistic);
		driver.setLaps(laps);

		consistencyDriverProcessor.process(null, driver, null);

		assertThat(driver.getDriverStatistic().getConsistency()).isEqualTo(10000.0);
		assertThat(driver.getDriverStatistic().getConsistencyPercentage()).isEqualTo(100.0);
	}

	@Test
	void test66Percent() {
		var driverStatistic = new DriverStatistic();
		driverStatistic.setBestTime(50);

		var laps = new ArrayList<Lap>();
		laps.add(Lap.builder().lapNumber(1).valid(false).lapTime(LapTime.builder().time(150).build()).build());
		laps.add(Lap.builder().lapNumber(2).valid(true).lapTime(LapTime.builder().time(100).build()).build());
		laps.add(Lap.builder().lapNumber(3).valid(true).lapTime(LapTime.builder().time(100).build()).build());
		laps.add(Lap.builder().lapNumber(4).valid(true).lapTime(LapTime.builder().time(50).build()).build());
		laps.add(Lap.builder().lapNumber(5).valid(false).lapTime(LapTime.builder().time(60).build()).build());
		laps.add(Lap.builder().lapNumber(6).valid(true).lapTime(LapTime.builder().time(60).build()).build());
		laps.add(Lap.builder().lapNumber(7).valid(true).lapTime(LapTime.builder().time(80).build()).build());
		laps.add(Lap.builder().lapNumber(8).valid(true).lapTime(LapTime.builder().time(80).build()).build());

		var driver = new Driver();
		driver.setDriverStatistic(driverStatistic);
		driver.setLaps(laps);

		consistencyDriverProcessor.process(null, driver, null);

		assertThat(driver.getDriverStatistic().getConsistency()).isCloseTo(75.71, Percentage.withPercentage(1.0));
		assertThat(driver.getDriverStatistic().getConsistencyPercentage()).isCloseTo(66.0, Percentage.withPercentage(1.0));
	}
}