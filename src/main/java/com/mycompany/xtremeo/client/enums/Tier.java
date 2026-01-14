package com.mycompany.xtremeo.client.enums;


public enum Tier {
    BRONZE("Bronze", 0, 499),
    SILVER("Silver", 500, 999),
    GOLD("Gold", 1000, 1999),
    PLATINUM("Platinum", 2000, 2999),
    DIAMOND("Diamond", 3000, 4999),
    MASTER("Master", 5000, Integer.MAX_VALUE);

    private final String displayName;
    private final int minScore;
    private final int maxScore;

    Tier(String displayName, int minScore, int maxScore) {
        this.displayName = displayName;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMinScore() {
        return minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public static Tier fromScore(int score) {
        for (Tier tier : values()) {
            if (score >= tier.minScore && score <= tier.maxScore) {
                return tier;
            }
        }
        return BRONZE;
    }

    @Override
    public String toString() {
        return displayName + " Tier";
    }
}

