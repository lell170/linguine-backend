package org.lell.accent.service.translation.strategy;

import org.lell.accent.model.Vocabulary;

import java.util.Optional;

@FunctionalInterface
public interface TranslationStrategy {
    Optional<Vocabulary> translate(Vocabulary vocabulary);
}
