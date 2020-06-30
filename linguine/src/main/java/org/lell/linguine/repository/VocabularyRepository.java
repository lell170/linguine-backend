package org.lell.linguine.repository;

import org.lell.linguine.model.Vocabulary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabularyRepository extends CrudRepository<Vocabulary, Long> {
}
