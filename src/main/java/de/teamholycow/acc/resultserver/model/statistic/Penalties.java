package de.teamholycow.acc.resultserver.model.statistic;

import de.teamholycow.acc.resultserver.model.json.JsonPenalty;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class Penalties {
    private final List<JsonPenalty> penalties;

    public Optional<JsonPenalty> get(long carId, int lap) {
        return penalties.stream()
                .filter(p -> p.getCarId() == carId && p.getClearedInLap() == lap)
                .findFirst();
    }
}
