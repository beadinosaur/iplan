package com.example.iplan_data.contentSimilarity.similarity.text;

import com.example.iplan_data.contentSimilarity.similarity.ISimilarity;
import com.example.iplan_data.contentSimilarity.tokenizer.Word;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public interface ITextSimilarity extends ISimilarity {

    float thresholdRate = 0.5f;

    /**
     * Similarity score between word list 1 and word list 2
     */
    double getSimilarity(List<Word> words1, List<Word> words2);

    default double getSimilarity(Map<Word, Float> weights1, Map<Word, Float> weights2) {
        List<List<Word>> words = Arrays.asList(weights1, weights2).parallelStream()
                .map(weights -> weights.keySet().parallelStream()
                        .map(word -> {
                            word.setWeight(weights.get(word));
                            return word;
                        })
                        .collect(Collectors.toList())).collect(Collectors.toList());

        return getSimilarity(words.get(0), words.get(1));
    }


}
