package org.lell.linguine.controller;

import org.lell.linguine.service.StatisticsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/statistics", produces = "application/json")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // @GetMapping(path = "/current", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
   /* public Flux<Statistics> getCurrentStatistics() {
        return Flux.interval(Duration.ofSeconds(1))
                   .onBackpressureDrop()
                   .map(e -> this.statisticsService.getCurrentStatistics());
    } */

}
