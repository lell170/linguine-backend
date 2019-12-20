package org.lell.accent.service.translation.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.lell.accent.model.Vocabulary;
import org.lell.accent.model.dto.Iwlinks;
import org.lell.accent.model.dto.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class WiktionaryTranslation implements TranslationStrategy {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Map<String, String> params;

    private static final String RESOURCE_URL = "https://en.wiktionary.org/w/api.php?action={action}&prop={prop}&format={format}&iwlimit={iwlimit}&iwprefix={iwprefix}&titles={titles}";
    private static final Logger logger = LoggerFactory.getLogger(WiktionaryTranslation.class);

    public WiktionaryTranslation() {
        this.restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();

        params = new HashMap<>();
        params.put("action", "query");
        params.put("prop", "iwlinks");
        params.put("format", "json");
        params.put("iwlimit", "20");
        params.put("iwprefix", "de");
    }

    @Override
    public Optional<Vocabulary> translate(final Vocabulary vocabulary) {
        params.put("titles", vocabulary.getEn());
        try {
            final ResponseEntity<String> response = restTemplate.getForEntity(RESOURCE_URL, String.class, params);
            if (response.getBody() != null) {
                final Optional<String> translationFromResponse = getTranslationFromResponse(response);
                if(translationFromResponse.isPresent()) {
                    vocabulary.setDe(translationFromResponse.get());
                } else {
                    return Optional.empty();
                }
            }
        } catch (final JsonProcessingException e) {
            logger.error("error occurred while json processing", e);
        }
        return Optional.of(vocabulary);
    }

    private Optional<String> getTranslationFromResponse(final ResponseEntity<String> response) throws JsonProcessingException {
        final JsonNode rootNode = objectMapper.readTree(Objects.requireNonNull(response.getBody()));
        final JsonNode pagesNode = rootNode.findValue("pages");

        if (pagesNode.elements().hasNext()) {
            final JsonNode translationNode = pagesNode.elements().next();
            if (translationNode.has("missing") || !translationNode.has("iwlinks")) {
                return Optional.empty();
            } else {
                final Translation translation = objectMapper.treeToValue(translationNode, Translation.class);
                final String translations = translation.getIwlinks()
                                                       .stream().map(Iwlinks::getTranslation)
                                                       .collect(Collectors.joining(", "));
                return Optional.of(translations);
            }
        }
        return Optional.empty();
    }
}
