package org.lell.linguine.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.lell.linguine.model.Book;
import org.lell.linguine.model.Vocabulary;
import org.lell.linguine.repository.VocabularyRepository;
import org.lell.linguine.service.translation.TranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class VocabularyService {

    private final VocabularyRepository vocabularyRepository;
    private final TranslationService translationService;
    private final IgnoredService ignoredService;
    private final FileService fileService;

    private static final Random RANDOM = new Random();
    private static final String TRUE_WORDS = "^([^0-9$&+;=@#|“\"'<>^*.\\[\\]?!:()%-]){2,}([?!,.A-z{1}])$";
    private static final String LAST_CHARACTER_IS_SPECIFIC = "([^A-z])";

    @Value("classpath:data/english_words_sorted.csv")
    Resource englishWordsCsvFile;

    private static final Logger logger = LoggerFactory.getLogger(VocabularyService.class);

    public VocabularyService(
            final FileService fileService,
            final IgnoredService ignoredService,
            final VocabularyRepository vocabularyRepository,
            final TranslationService translationService) {
        this.ignoredService = ignoredService;
        this.vocabularyRepository = vocabularyRepository;
        this.translationService = translationService;
        this.fileService = fileService;
    }

    public Optional<Vocabulary> getRandomTranslated() {
        final Optional<Vocabulary> randomVocabulary = getRandomVocabulary();
        if (randomVocabulary.isPresent() && !StringUtils.hasLength(randomVocabulary.get().getDe())) {
            // recursive call if translation not found. (Java 9 "or" option)
            final Optional<Vocabulary> translatedVocabulary = translationService.translate(randomVocabulary.get())
                                                                                .or(this::getRandomTranslated);
            if (translatedVocabulary.isPresent()) {
                vocabularyRepository.save(translatedVocabulary.get());
                return translatedVocabulary;
            }
        } else if (randomVocabulary.isPresent() && StringUtils.hasLength(randomVocabulary.get().getDe())) {
            return randomVocabulary;
        }
        return Optional.empty();
    }

    public Optional<Vocabulary> getRandomVocabulary() {
        final ArrayList<Vocabulary> vocabularies = Lists.newArrayList(vocabularyRepository.findAll());
        if (!vocabularies.isEmpty()) {
            return vocabularies.stream()
                               .skip(RANDOM.nextInt(vocabularies.size()))
                               .findFirst();
        }
        return Optional.empty();
    }

    public Vocabulary updateVocabulary(final Vocabulary vocabulary) {
        return vocabularyRepository.save(vocabulary);
    }

    public void delete(final Vocabulary vocabulary) {
        vocabularyRepository.delete(vocabulary);
    }

    public void addNewWordsFromBook(final Book book) {
        final Set<String> wordsOfBook = filterBookWords(book);

        vocabularyRepository.saveAll(this.filterVocabularies(wordsOfBook));
    }

    private Set<String> filterBookWords(final Book book) {
        return Stream.of(book.getContent().split("\\s+"))
                     .filter(string -> string.matches(TRUE_WORDS))
                     .map(string -> string.replaceAll(LAST_CHARACTER_IS_SPECIFIC, ""))
                     .map(String::toLowerCase)
                     .collect(Collectors.toSet());
    }

    private Set<Vocabulary> filterVocabularies(final Set<String> words) {
        final List<Vocabulary> currentList = Streams.stream(vocabularyRepository.findAll()).collect(Collectors.toList());
        final List<String> ignored = ignoredService.getAllIgnoredWords();

        currentList.stream()
                   .filter(vocabulary -> words.contains(vocabulary.getEn()))
                   .filter(vocabulary -> ignored.contains(vocabulary.getEn()))
                   .forEach(vocabulary -> words.remove(vocabulary.getEn()));

        return words.stream()
                    .map(word -> {
                        final Vocabulary newVocabularyEntry = new Vocabulary();
                        newVocabularyEntry.setEn(word);
                        return newVocabularyEntry;
                    }).collect(Collectors.toSet());

    }

    //TODO handle exception...
    public void loadCsvVocabularies() {
        try {
            final Set<String> linesForCsvFile = fileService.getLinesForCsvFile(englishWordsCsvFile.getFile());
            vocabularyRepository.saveAll(this.filterVocabularies(linesForCsvFile));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
