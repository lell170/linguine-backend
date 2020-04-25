package org.lell.accent.repository;

import org.lell.accent.model.IgnoredVocabulary;
import org.springframework.data.repository.CrudRepository;

public interface IgnoredRepository extends CrudRepository<IgnoredVocabulary, Long> {
}
