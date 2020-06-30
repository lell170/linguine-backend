package org.lell.linguine.model;

import javax.persistence.*;

@Entity
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "vocabulary_id", referencedColumnName = "id")
    private Vocabulary vocabulary;

    private int retryCount;

    private boolean active;

    private int solvedCount;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(final Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(final int retry_count) {
        this.retryCount = retry_count;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public int getSolvedCount() {
        return solvedCount;
    }

    public void setSolvedCount(final int solvedCount) {
        this.solvedCount = solvedCount;
    }
}
