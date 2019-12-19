package org.lell.accent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Translation {

    @JsonProperty("pageid")
    private String pageId;

    @JsonProperty("ns")
    private String ns;

    @JsonProperty("title")
    private String title;

    @JsonProperty("iwlinks")
    private List<Iwlinks> iwlinks;

    public Translation() {
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(final String pageId) {
        this.pageId = pageId;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(final String ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public List<Iwlinks> getIwlinks() {
        return iwlinks;
    }

    public void setIwlinks(final List<Iwlinks> iwlinks) {
        this.iwlinks = iwlinks;
    }
}
