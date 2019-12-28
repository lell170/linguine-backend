package org.lell.accent.service.translation;

import org.lell.accent.model.Vocabulary;
import org.lell.accent.service.translation.strategy.GoogleScriptTranslation;
import org.lell.accent.service.translation.strategy.TranslationStrategy;
import org.lell.accent.service.translation.strategy.WiktionaryTranslation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TranslationService {

    private final TranslationStrategy wiktionaryTranslation;
    private final TranslationStrategy googleScriptTranslation;

    TranslationService() {
        wiktionaryTranslation = new WiktionaryTranslation();
        googleScriptTranslation = new GoogleScriptTranslation();
    }

    public Optional<Vocabulary> translate(final Vocabulary vocabulary) {
        return wiktionaryTranslation.translate(vocabulary)
                                    .or(() -> googleScriptTranslation.translate(vocabulary));
    }
}
