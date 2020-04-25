package org.lell.linguine.service.translation.strategy;

import org.lell.linguine.model.Vocabulary;

import java.util.Optional;

@FunctionalInterface
public interface TranslationStrategy {
    Optional<Vocabulary> translate(Vocabulary vocabulary);
}
