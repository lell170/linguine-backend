package org.lell.accent.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.lell.accent.model.Vocabulary;
import org.lell.accent.model.dto.Iwlinks;
import org.lell.accent.model.dto.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TranslationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Map<String, String> params;

    private static final String RESOURCE_URL = "https://en.wiktionary.org/w/api.php?action={action}&prop={prop}&format={format}&iwlimit={iwlimit}&iwprefix={iwprefix}&titles={titles}";
    private static final Logger logger = LoggerFactory.getLogger(TranslationService.class);

    TranslationService() {
        this.restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();

        params = new HashMap<>();
        params.put("action", "query");
        params.put("prop", "iwlinks");
        params.put("format", "json");
        params.put("iwlimit", "20");
        params.put("iwprefix", "de");
    }

    //TODO: handle exception and logging
    public void translate(final Vocabulary vocabulary) throws IOException {
        params.put("titles", vocabulary.getEn());

        final ResponseEntity<String> response = restTemplate.getForEntity(RESOURCE_URL, String.class, params);

        final JsonNode rootNode = objectMapper.readTree(response.getBody());
        final JsonNode pagesNode = rootNode.findValue("pages");

        if (pagesNode.elements().hasNext()) {
            final JsonNode translationNode = pagesNode.elements().next();
            if (translationNode.has("missing")) {
                logger.info("no translation found for {}", vocabulary.getEn());
            } else {
                final Translation translation = objectMapper.treeToValue(translationNode, Translation.class);
                final String translations = translation.getIwlinks()
                                                       .stream().map(Iwlinks::getTranslation)
                                                       .collect(Collectors.joining(", "));
                vocabulary.setDe(translations);
            }
        }
    }
}
