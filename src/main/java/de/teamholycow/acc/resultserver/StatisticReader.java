package de.teamholycow.acc.resultserver;

import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.statistic.StatisticResult;
import de.teamholycow.acc.resultserver.processor.driver.BasicDriverStatisticProcessor;
import de.teamholycow.acc.resultserver.processor.driver.ConsistencyDriverProcessor;
import de.teamholycow.acc.resultserver.processor.driver.DamageTimeProcessor;
import de.teamholycow.acc.resultserver.processor.driver.DriverLapsProcessor;
import de.teamholycow.acc.resultserver.processor.driver.DriverProcessor;
import de.teamholycow.acc.resultserver.processor.driver.PitStopDriverProcessor;
import de.teamholycow.acc.resultserver.processor.statistic.AddDriverProcessor;
import de.teamholycow.acc.resultserver.processor.statistic.ExtendDriverProcessor;
import de.teamholycow.acc.resultserver.processor.statistic.SessionInformationProcessor;
import de.teamholycow.acc.resultserver.processor.statistic.StatisticProcessor;

import java.io.IOException;
import java.util.List;

public class StatisticReader {
    private static final List<StatisticProcessor> STATISTIC_PROCESSORS = List.of(
            new SessionInformationProcessor(),
            new AddDriverProcessor(),
            new ExtendDriverProcessor()
    );

    private static final List<DriverProcessor> DRIVER_PREPROCESSORS = List.of(
            new DriverLapsProcessor(),
            new BasicDriverStatisticProcessor(),
            new ConsistencyDriverProcessor(),
            new PitStopDriverProcessor()
    );

    private static final List<DriverProcessor> DRIVER_POSTPROCESSORS = List.of(
            new DamageTimeProcessor()
    );


    public static void main(String[] args) throws IOException {
        var statisticReader = new StatisticReader();
        statisticReader.parse(args[0]);
    }

    public StatisticResult parse(String fileName) throws IOException {
        JsonResult jsonResult = new JsonParser().parse(fileName);

        StatisticResult statisticResult = new StatisticResult();
        STATISTIC_PROCESSORS.forEach(statisticProcessor -> statisticProcessor.process(statisticResult, jsonResult));

        statisticResult.getDrivers().forEach(driver -> {
            DRIVER_PREPROCESSORS.forEach(driverProcessor -> driverProcessor.process(statisticResult, driver, jsonResult));
        });

        statisticResult.getDrivers().forEach(driver -> {
            DRIVER_POSTPROCESSORS.forEach(driverProcessor -> driverProcessor.process(statisticResult, driver, jsonResult));
        });

        return statisticResult;
    }
}
