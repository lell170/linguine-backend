package org.lell.accent;

import org.lell.accent.service.VocabularyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

@EntityScan("org.lell.accent.model")
@SpringBootApplication
public class AccentApplication {

	public static void main(final String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(AccentApplication.class, args);
		context.getBean(VocabularyService.class).loadCsvVocabularies();
	}

}
