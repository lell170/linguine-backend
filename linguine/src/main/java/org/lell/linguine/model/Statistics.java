package org.lell.linguine.model;

public class Statistics {

    private long wordsCount;

    private long solvedCount;

    private long activeChallengesCount;

    public long getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(final long wordsCount) {
        this.wordsCount = wordsCount;
    }

    public long getSolvedCount() {
        return solvedCount;
    }

    public void setSolvedCount(final long solvedCount) {
        this.solvedCount = solvedCount;
    }

    public long getActiveChallengesCount() {
        return activeChallengesCount;
    }

    public void setActiveChallengesCount(final long activeChallengesCount) {
        this.activeChallengesCount = activeChallengesCount;
    }
}
