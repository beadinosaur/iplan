package com.example.iplan_data.contentSimilarity.similarity.text;

import com.example.iplan_data.contentSimilarity.similarity.util.StringUtil;
import com.example.iplan_data.contentSimilarity.tokenizer.Tokenizer;
import com.example.iplan_data.contentSimilarity.tokenizer.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * text similarity
 */
public abstract class TextSimilarity implements ITextSimilarity {


    @Override
    public double getSimilarity(String text1, String text2) {

        if (StringUtil.isBlank(text1) && StringUtil.isBlank(text2)) {
            return 1.0;
        }
        if (StringUtil.isBlank(text1) || StringUtil.isBlank(text2)) {
            return 0.0;
        }
        if (text1.equalsIgnoreCase(text2)) {
            return 1.0;
        }
        List<Word> words1 = Tokenizer.segment(text1);
        List<Word> words2 = Tokenizer.segment(text2);
        return getSimilarity(words1, words2);
    }

    @Override
    public double getSimilarity(List<Word> words1, List<Word> words2) {
        if (StringUtil.isBlank(words1) && StringUtil.isBlank(words2)) {
            return 1.0;
        }
        if (StringUtil.isBlank(words1) || StringUtil.isBlank(words2)) {
            return 0.0;
        }

        double score = getSimilarityImpl(words1, words2);
        return score;

    }


    protected abstract double getSimilarityImpl(List<Word> words1, List<Word> words2);



    protected void taggingWeightByFrequency(List<Word> words1, List<Word> words2) {
        if (words1.get(0).getWeight() != null || words2.get(0).getWeight() != null) {
            return;
        }
        Map<String, AtomicInteger> frequency1 = getFrequency(words1);
        Map<String, AtomicInteger> frequency2 = getFrequency(words2);

        // Label weights
        words1.parallelStream().forEach(word -> word.setWeight(frequency1.get(word.getName()).floatValue()));
        words2.parallelStream().forEach(word -> word.setWeight(frequency2.get(word.getName()).floatValue()));
    }

    /**
     * Count word frequency
     */
    private Map<String, AtomicInteger> getFrequency(List<Word> words) {
        Map<String, AtomicInteger> freq = new HashMap<>();
        words.forEach(i -> freq.computeIfAbsent(i.getName(), k -> new AtomicInteger()).incrementAndGet());
        return freq;
    }

    /**
     * Construct the weight fast search container
     *
     * @param words
     * @return
     */
    protected Map<String, Float> getFastSearchMap(List<Word> words) {
        Map<String, Float> weightMap = new ConcurrentHashMap<>();
        if (words == null) return weightMap;
        words.parallelStream().forEach(i -> {
            if (i.getWeight() != null) {
                weightMap.put(i.getName(), i.getWeight());
            } else {
            }
        });
        return weightMap;
    }
}
