package org.lell.accent.service;

import com.google.common.collect.Lists;
import org.lell.accent.model.Challenge;
import org.lell.accent.model.Statistics;
import org.lell.accent.repository.ChallengeRepository;
import org.lell.accent.repository.VocabularyRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class StatisticsService {

    private final ChallengeRepository challengeRepository;
    private final VocabularyRepository vocabularyRepository;

    public StatisticsService(final ChallengeRepository challengeRepository, final VocabularyRepository vocabularyRepository) {
        this.challengeRepository = challengeRepository;
        this.vocabularyRepository = vocabularyRepository;
    }

    //TODO implement solved count
    public Statistics getCurrentStatistics() {
        final ArrayList<Challenge> challenges = Lists.newArrayList(challengeRepository.findAll());
        final long countActiveChallenges = challenges.stream().filter(Challenge::isActive).count();

        final Statistics statistics = new Statistics();
        statistics.setActiveChallengesCount(countActiveChallenges);
        statistics.setWordsCount(vocabularyRepository.count());
        statistics.setSolvedCount(0);

        return statistics;
    }
}
