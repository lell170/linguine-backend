package org.lell.accent.controller;

import org.lell.accent.model.Statistics;
import org.lell.accent.service.StatisticsService;
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
