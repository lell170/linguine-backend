package org.lell.accent.controller;

import org.lell.accent.model.Vocabulary;
import org.lell.accent.service.VocabularyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: use DTO instead of JPA Entities

@RestController
@RequestMapping(path = "/api/vocabulary", produces = "application/json")
public class VocabularyController {

    private static final Logger logger = LoggerFactory.getLogger(VocabularyController.class);

    private final VocabularyService vocabularyService;

    public VocabularyController(final VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vocabulary>> allVocabularies() {
        return new ResponseEntity<>(vocabularyService.getAllDictionaries(), HttpStatus.OK);
    }

    @PutMapping(path = "/update", consumes = "application/json")
    public ResponseEntity<Vocabulary> updateVocabulary(@RequestBody final Vocabulary vocabulary) {
        logger.info("Request received - update vocabulary {}", vocabulary);
        //TODO: handle not founded vocabularies
        return new ResponseEntity<>(vocabularyService.updateVocabulary(vocabulary), HttpStatus.OK);
    }

    @PutMapping("/drop")
    public void dropVocabulary(@RequestBody final Vocabulary vocabulary) {
        logger.info("request for deleting and ignore vocabulary received for {}", vocabulary);
        vocabularyService.dropAndIgnore(vocabulary);
    }

    @GetMapping("/random")
    public Vocabulary getRandomVocabulary() {
        //TODO: handle optional
        return vocabularyService.getRandomTranslated().orElseGet(Vocabulary::new);
    }
}
