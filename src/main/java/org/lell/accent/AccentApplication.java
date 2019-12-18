package org.lell.accent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("org.lell.accent.model")
@SpringBootApplication
public class AccentApplication {

	public static void main(final String[] args) {
		SpringApplication.run(AccentApplication.class, args);
	}

}
