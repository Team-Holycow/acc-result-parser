package de.teamholycow.acc.resultserver.processor.driver;

import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.statistic.Driver;
import de.teamholycow.acc.resultserver.model.statistic.StatisticResult;

public interface DriverProcessor {
    void process(StatisticResult statisticResult, Driver driver, JsonResult jsonResult);
}
