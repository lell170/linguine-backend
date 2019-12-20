package org.lell.accent.service.translation;

import org.lell.accent.model.Vocabulary;
import org.lell.accent.service.translation.strategy.GoogleScriptTranslation;
import org.lell.accent.service.translation.strategy.TranslationStrategy;
import org.lell.accent.service.translation.strategy.WiktionaryTranslation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TranslationService {

    private final List<TranslationStrategy> translationStrategyList;

    TranslationService() {
        translationStrategyList = new ArrayList<>();
        translationStrategyList.add(new WiktionaryTranslation());
        translationStrategyList.add(new GoogleScriptTranslation());
    }

    public Optional<Vocabulary> translate(final Vocabulary vocabulary) {

        return translationStrategyList
                .stream()
                .filter(translationStrategy -> translationStrategy.translate(vocabulary).isPresent())
                .map(translationStrategy -> vocabulary).findAny();
    }
}
