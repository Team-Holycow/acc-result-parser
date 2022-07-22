package de.teamholycow.acc.resultserver.processor.statistic;

import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.statistic.StatisticResult;

public interface StatisticProcessor {
    void process(StatisticResult statisticResult, JsonResult jsonResult);
}
