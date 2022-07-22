package de.teamholycow.acc.resultserver.processor.statistic;

import de.teamholycow.acc.data.SessionType;
import de.teamholycow.acc.data.Track;
import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.statistic.LapTime;
import de.teamholycow.acc.resultserver.model.statistic.StatisticResult;

public class SessionInformationProcessor implements StatisticProcessor {
    @Override
    public void process(StatisticResult statisticResult, JsonResult jsonResult) {
        statisticResult.setSessionType(SessionType.get(jsonResult.getSessionType()));
        statisticResult.setTrack(Track.get(jsonResult.getTrackName()));
        statisticResult.setServerName(jsonResult.getServerName());
        statisticResult.setWetSession(jsonResult.getSessionResult().getIsWetSession() == 1);
        statisticResult.setBestLap(LapTime.builder()
                .time(jsonResult.getSessionResult().getBestlap())
                .sectorOne(jsonResult.getSessionResult().getBestSplits()[0])
                .sectorTwo(jsonResult.getSessionResult().getBestSplits()[1])
                .sectorThree(jsonResult.getSessionResult().getBestSplits()[2])
                .build());
    }
}
