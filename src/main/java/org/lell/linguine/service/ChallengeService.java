package org.lell.linguine.service;

import com.google.common.collect.Lists;
import org.lell.linguine.model.Challenge;
import org.lell.linguine.model.Vocabulary;
import org.lell.linguine.repository.ChallengeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    private static final Random RANDOM = new Random();

    public ChallengeService(final ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public void addToChallenge(final Vocabulary vocabulary) {
        final Challenge challenge = new Challenge();
        challenge.setActive(true);
        challenge.setVocabulary(vocabulary);
        challengeRepository.save(challenge);
    }

    public Challenge getRandomChallenge() {
        final ArrayList<Challenge> challenges = Lists.newArrayList(this.challengeRepository.findAll());
        if (!challenges.isEmpty()) {
            final List<Challenge> activeChallenges = challenges.stream()
                                                               .filter(Challenge::isActive)
                                                               .collect(Collectors.toList());

            return activeChallenges.get(RANDOM.nextInt(activeChallenges.size()));
        }
        return new Challenge();
    }

    public void ignoreChallenge(final Challenge challenge) {
        challenge.setActive(false);
        challengeRepository.save(challenge);
    }

}
