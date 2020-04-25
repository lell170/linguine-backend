package org.lell.accent.service;

import com.google.common.collect.Lists;
import org.lell.accent.model.IgnoredVocabulary;
import org.lell.accent.model.Vocabulary;
import org.lell.accent.repository.IgnoredRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IgnoredService {

    private final IgnoredRepository ignoredRepository;
    private final VocabularyService vocabularyService;

    IgnoredService(final IgnoredRepository ignoredRepository, @Lazy final VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
        this.ignoredRepository = ignoredRepository;
    }

    public void ignoreVocabulary(final Vocabulary vocabulary) {
        final IgnoredVocabulary ignoredVocabulary = new IgnoredVocabulary();
        ignoredVocabulary.setWord(vocabulary.getEn());
        ignoredRepository.save(ignoredVocabulary);
        vocabularyService.delete(vocabulary);
    }

    public List<String> getAllIgnoredWords() {
        final ArrayList<IgnoredVocabulary> allIgnoredVocabulary = Lists.newArrayList(ignoredRepository.findAll());
        return allIgnoredVocabulary.stream().map(IgnoredVocabulary::getWord).collect(Collectors.toList());
    }
}
