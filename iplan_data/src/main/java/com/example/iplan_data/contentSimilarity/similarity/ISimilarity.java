package com.example.iplan_data.contentSimilarity.similarity;

/**
 * Calculating similarity
 */
public interface ISimilarity {
    /**
     * Calculating similarity
     *
     * @param word1
     * @param word2
     * @return
     */
    double getSimilarity(String word1, String word2);
}
