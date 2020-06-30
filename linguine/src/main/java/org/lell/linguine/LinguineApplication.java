package org.lell.linguine;

import org.lell.linguine.service.VocabularyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

@EntityScan("org.lell.linguine.model")
@SpringBootApplication
public class LinguineApplication {

	public static void main(final String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(LinguineApplication.class, args);
		context.getBean(VocabularyService.class).loadCsvVocabularies();
	}

}
