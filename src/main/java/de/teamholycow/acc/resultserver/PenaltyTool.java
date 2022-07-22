package de.teamholycow.acc.resultserver;

import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.penalty.LeaguePenalty;
import de.teamholycow.acc.resultserver.model.penalty.LeagueResult;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class PenaltyTool {

    public static final int PIT_STOP_IDENTIFIER = 2000;

    public static void main(String[] args) throws IOException {
        new PenaltyTool("SilverstoneR.json", "penalties_r2.json");
    }

    public PenaltyTool(String fileName, String penalties) throws IOException {
        JsonResult jsonResult = new JsonParser().parse(fileName);
        List<LeaguePenalty> leaguePenalties = new JsonParser().parseLeaguePenalties(penalties);

        Map<Integer, Long> driverPenaltyMap = leaguePenalties.stream().collect(Collectors.toMap(LeaguePenalty::getRaceNumber, LeaguePenalty::getTime));

        AtomicInteger position = new AtomicInteger(1);
        List<LeagueResult> leagueResultList = jsonResult.getSessionResult().getLeaderBoardLines().stream().map(l ->
                        LeagueResult.builder()
                                .raceNumber(l.getCar().getRaceNumber())
                                .firstName(l.getCurrentDriver().getFirstName())
                                .lastName(l.getCurrentDriver().getLastName())
                                .position(position.getAndIncrement())
                                .lastLapTime(l.getTiming().getTotalTime())
                                .laps(driverPenaltyMap.getOrDefault(l.getCar().getRaceNumber(), 0l) == -1 ? -1 : l.getTiming().getLapCount())
                                .penalty(driverPenaltyMap.getOrDefault(l.getCar().getRaceNumber(), 0l) * 1000)
                                .build()
                )
                .collect(Collectors.toList());

        position.set(1);
        leagueResultList = leagueResultList.stream().sorted(Comparator
                        .comparingLong(LeagueResult::getLaps)
                        .reversed()
                        .thenComparingLong(LeagueResult::getFinalLastLapTime))
                .peek(l -> l.setNewPosition(position.getAndIncrement())).collect(Collectors.toList());

        AtomicLong lastLap = new AtomicLong(0);
        AtomicLong lastTime = new AtomicLong(0);
        leagueResultList.forEach(l -> {
            String difference;
            if (l.getLaps() == -1) {
                difference = "DSQ";
            } else if (lastLap.get() != l.getLaps()) {
                difference = l.getLaps() + " Rounds";
                lastLap.set(l.getLaps());
            } else {
                difference = String.format("+%7.3fs", Math.round(l.getFinalLastLapTime() - lastTime.get()) / 1000.0);
            }

            lastTime.set(l.getFinalLastLapTime());

            //log.info("{}. {} {} {} ({}ms,{}R,{})", l.getFirstName().charAt(0), l.getLastName(), l.getPosition(), l.getNewPosition(), l.getFinalLastLapTime(), l.getLaps(), l.getPenalty());
            log.info(String.format("%2d\t%-17s\t%s", l.getNewPosition(), (l.getFirstName().charAt(0) + ". " + l.getLastName()), difference));
        });
    }
}
