package org.lell.linguine.controller;

import org.lell.linguine.model.Statistics;
import org.lell.linguine.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/statistics", produces = "application/json")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/current")
    public Statistics getCurrentStatistics() {
        return this.statisticsService.getCurrentStatistics();
    }

}
