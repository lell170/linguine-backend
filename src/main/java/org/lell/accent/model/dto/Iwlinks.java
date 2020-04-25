package org.lell.accent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Iwlinks {

    @JsonProperty("prefix")
    private String language;

    @JsonProperty("*")
    private String translation;
    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(final String translation) {
        this.translation = translation;
    }
}
