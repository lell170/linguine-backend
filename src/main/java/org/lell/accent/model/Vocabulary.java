package org.lell.accent.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String en;
    private String de;
    private boolean know;

    public Vocabulary() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(final String word) {
        this.en = word;
    }

    public String getDe() {
        return de;
    }

    public void setDe(final String de) {
        this.de = de;
    }

    public boolean isKnow() {
        return know;
    }

    public void setKnow(final boolean know) {
        this.know = know;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Vocabulary that = (Vocabulary) o;
        return Objects.equals(en, that.en);
    }

    @Override
    public int hashCode() {
        return Objects.hash(en);
    }
}
