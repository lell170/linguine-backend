package org.lell.statistics.controller;


import org.lell.statistics.model.Statistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/api/statistics", produces = "text/plain")
public class StatisticsController {

    // stub implementation...
    List<Statistics> statisticsList = new ArrayList<>();

    StatisticsController() {
        statisticsList.add(new Statistics(12, 22, (long) (Math.random() * 100)));
        statisticsList.add(new Statistics(12, (long) (Math.random() * 100), (long) (Math.random() * 100)));
        statisticsList.add(new Statistics(12, 22, (long) (Math.random() * 100)));
        statisticsList.add(new Statistics(12, (long) (Math.random() * 100), (long) (Math.random() * 100)));
        statisticsList.add(new Statistics(12, 22, (long) (Math.random() * 100)));
        statisticsList.add(new Statistics(12, 22, (long) (Math.random() * 100)));
    }

    @GetMapping(path = "/current")
    public Flux<String> getCurrentStatistics() {
        return Flux.fromStream(Stream.of("Hello", "World"));
    }
}
