package org.lell.linguine.repository;

import org.lell.linguine.model.IgnoredVocabulary;
import org.springframework.data.repository.CrudRepository;

public interface IgnoredRepository extends CrudRepository<IgnoredVocabulary, Long> {
}
