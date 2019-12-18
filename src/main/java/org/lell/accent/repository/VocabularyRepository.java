package org.lell.accent.repository;

import org.lell.accent.model.Vocabulary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabularyRepository extends CrudRepository<Vocabulary, Long> {
}
