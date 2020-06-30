package org.lell.linguine.controller;

import org.lell.linguine.model.Vocabulary;
import org.lell.linguine.service.IgnoredService;
import org.lell.linguine.service.VocabularyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO: use DTO instead of JPA Entities

@RestController
@RequestMapping(path = "/api/vocabulary", produces = "application/json")
public class VocabularyController {

    private static final Logger logger = LoggerFactory.getLogger(VocabularyController.class);

    private final VocabularyService vocabularyService;
    private final IgnoredService ignoredService;

    public VocabularyController(final VocabularyService vocabularyService, final IgnoredService ignoredService) {
        this.ignoredService = ignoredService;
        this.vocabularyService = vocabularyService;
    }

    @PutMapping(path = "/update", consumes = "application/json")
    public ResponseEntity<Vocabulary> updateVocabulary(@RequestBody final Vocabulary vocabulary) {
        logger.info("Request received - update vocabulary {}", vocabulary);
        //TODO: handle not founded vocabularies
        return new ResponseEntity<>(vocabularyService.updateVocabulary(vocabulary), HttpStatus.OK);
    }

    @PostMapping("/ignore")
    public void dropVocabulary(@RequestBody final Vocabulary vocabulary ) {
        logger.info("ignore vocabulary {}", vocabulary);
        ignoredService.ignoreVocabulary(vocabulary);
    }

    @GetMapping("/random")
    public Vocabulary getRandomVocabulary() {
        //TODO: handle optional
        return vocabularyService.getRandomTranslated().orElseGet(Vocabulary::new);
    }
}
