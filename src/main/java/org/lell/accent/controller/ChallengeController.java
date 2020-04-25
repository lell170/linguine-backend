package org.lell.accent.controller;

import org.lell.accent.model.Challenge;
import org.lell.accent.model.Vocabulary;
import org.lell.accent.service.ChallengeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/challenge", produces = "application/json")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(final ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping("/random")
    public Challenge getRandomChallenge() {
        return this.challengeService.getRandomChallenge();
    }

    @PostMapping("/add")
    public void addToChallenge(@RequestBody final Vocabulary vocabulary) {
        this.challengeService.addToChallenge(vocabulary);
    }

    @PostMapping("/ignore")
    public void ignoreChallenge(@RequestBody final Challenge challenge) {
        this.challengeService.ignoreChallenge(challenge);
    }

}
