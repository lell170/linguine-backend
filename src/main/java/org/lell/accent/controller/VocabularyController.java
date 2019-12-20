package org.lell.accent.controller;

import org.lell.accent.model.Vocabulary;
import org.lell.accent.service.VocabularyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

//TODO: use DTO instead of JPA Entities

@RestController
public class VocabularyController {

    private static final Logger logger = LoggerFactory.getLogger(VocabularyController.class);

    private final VocabularyService vocabularyService;

    public VocabularyController(final VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @GetMapping("/vocabulary/all")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vocabulary> allVocabularies() {
        return vocabularyService.getAllDictionaries();
    }

    @PutMapping("/vocabulary/update")
    @CrossOrigin(origins = "http://localhost:4200")
    public Vocabulary updateVocabulary(@RequestBody final Vocabulary vocabulary) throws IOException {
        logger.info("Request received - update vocabulary {}", vocabulary);
        //TODO: handle not founded vocabularies
        return vocabularyService.updateVocabulary(vocabulary);
    }

    @PostMapping("/vocabulary/drop")
    @CrossOrigin(origins = "http://localhost:4200")
    public void dropVocabulary(@RequestBody final Vocabulary vocabulary) {
        logger.info("request for deleting and ignore vocabulary received for {}", vocabulary.getEn());
        vocabularyService.dropAndIgnore(vocabulary);
    }

    @GetMapping("/vocabulary/random")
    @CrossOrigin(origins = "http://localhost:4200")
    public Vocabulary getRandomVocabulary() {
        //TODO: handle optional
        return vocabularyService.getRandomTranslated().orElseGet(Vocabulary::new);
    }
}
