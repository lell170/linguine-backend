package org.lell.statistics.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StatisticsRepository extends ReactiveCrudRepository<String, Long> {
}
