package org.lell.accent.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.apache.commons.io.FileUtils;
import org.lell.accent.model.Book;
import org.lell.accent.model.Vocabulary;
import org.lell.accent.repository.VocabularyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class VocabularyService {

    private final VocabularyRepository vocabularyRepository;
    private static final Random RANDOM = new Random();
    private static final String TRUE_WORDS = "^([^0-9$&+;=@#|“\"'<>^*.\\[\\]?!:()%-]){2,}([?!,.A-z{1}])$";
    private static final String LAST_CHARACTER_IS_SPECIFIC = "([^A-z])";

    private static final Logger logger = LoggerFactory.getLogger(VocabularyService.class);

    @Value("${ignore.file.path:var/ignore.txt}")
    private String ignoreFile;

    public VocabularyService(final VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    public void addNewWords(final Book book) {
        final List<Vocabulary> currentList = Streams.stream(vocabularyRepository.findAll()).collect(Collectors.toList());
        final Set<String> wordsOfBook = filterWords(book);

        // create only book_to_dictionary entries and remove word from collection
        currentList.stream()
                   .filter(vocabulary -> wordsOfBook.contains(vocabulary.getEn()))
                   .forEach(vocabulary -> wordsOfBook.remove(vocabulary.getEn()));

        // create and save new dictionary entries
        final List<Vocabulary> vocabularies = wordsOfBook.stream()
                                                         .map(word -> {
                                                             final Vocabulary newVocabularyEntry = new Vocabulary();
                                                             newVocabularyEntry.setEn(word);
                                                             return newVocabularyEntry;
                                                         }).collect(Collectors.toList());
        vocabularyRepository.saveAll(vocabularies);
    }

    private Set<String> filterWords(final Book book) {
        return Stream.of(book.getContent().split("\\s+"))
                     .filter(string -> string.matches(TRUE_WORDS))
                     .map(string -> string.replaceAll(LAST_CHARACTER_IS_SPECIFIC, ""))
                     .map(String::toLowerCase)
                     .collect(Collectors.toSet());
    }

    public List<Vocabulary> getAllDictionaries() {
        return (List<Vocabulary>) vocabularyRepository.findAll();
    }

    public Optional<Vocabulary> getRandom() {
        final ArrayList<Vocabulary> vocabularies = Lists.newArrayList(vocabularyRepository.findAll());
        if (!vocabularies.isEmpty()) {
            return vocabularies.stream()
                               .skip(RANDOM.nextInt(vocabularies.size()))
                               .findFirst();
        } else {
            return Optional.empty();
        }
    }

    public Vocabulary updateVocabulary(final Vocabulary vocabulary) {
        if(!vocabulary.isKnow()) {
            //TODO: add translation
        }
        return vocabularyRepository.save(vocabulary);
    }

    public Optional<Vocabulary> getById(final Long id) {
        return vocabularyRepository.findById(id);
    }

    public void dropAndIgnore(final Vocabulary vocabulary) {
        final File file = new File(ignoreFile);
        try {
            logger.info("vocabulary {} will be added to ignore list", vocabulary.getEn());
            FileUtils.writeLines(file, Collections.singletonList(vocabulary.getEn()), true);
        } catch (final IOException e) {
            logger.error("error occurred while updating ignore file", e);
        }
        vocabularyRepository.delete(vocabulary);
    }
}
