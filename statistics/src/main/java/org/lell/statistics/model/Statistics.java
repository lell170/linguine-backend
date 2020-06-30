package org.lell.statistics.model;

public class Statistics {

    public Statistics(final long wordsCount, final long solvedCount, final long activeChallengesCount) {
        this.wordsCount = wordsCount;
        this.solvedCount = solvedCount;
        this.activeChallengesCount = activeChallengesCount;
    }

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
