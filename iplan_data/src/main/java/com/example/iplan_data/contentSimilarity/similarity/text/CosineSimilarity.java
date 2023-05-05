package com.example.iplan_data.contentSimilarity.similarity.text;

import com.example.iplan_data.contentSimilarity.similarity.util.AtomicFloat;
import com.example.iplan_data.contentSimilarity.tokenizer.Word;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Cosine similarity calculation
 * How: Cosine similarity, which evaluates the similarity of two vectors by calculating the cosine of the Angle between them
 * Principle of cosine Angle:
 * vector quantity a=(x1,y1),vector quantity b=(x2,y2)
 * similarity=a.b/|a|*|b|
 * a.b=x1x2+ |a|=sqrt[(x1)^2+(y1)^2],|b|=sqrt[(x2)^2+(y2)^2]
 *y1y2
 */
public class CosineSimilarity extends TextSimilarity {
    /**
     * Cosine similarity calculation
     */
    @Override
    public double getSimilarityImpl(List<Word> words1, List<Word> words2) {
        // Word frequency tags the weight of the words
        taggingWeightByFrequency(words1, words2);
        // Weight container
        Map<String, Float> weightMap1 = getFastSearchMap(words1);
        Map<String, Float> weightMap2 = getFastSearchMap(words2);
        Set<Word> words = new HashSet<>();
        words.addAll(words1);
        words.addAll(words2);
        AtomicFloat ab = new AtomicFloat();
        AtomicFloat aa = new AtomicFloat();
        AtomicFloat bb = new AtomicFloat();

        words.parallelStream()
                .forEach(word -> {
                    Float x1 = weightMap1.get(word.getName());
                    Float x2 = weightMap2.get(word.getName());
                    if (x1 != null && x2 != null) {
                        //x1x2
                        float oneOfTheDimension = x1 * x2;
                        //+
                        ab.addAndGet(oneOfTheDimension);
                    }
                    if (x1 != null) {
                        //(x1)^2
                        float oneOfTheDimension = x1 * x1;
                        //+
                        aa.addAndGet(oneOfTheDimension);
                    }
                    if (x2 != null) {
                        //(x2)^2
                        float oneOfTheDimension = x2 * x2;
                        //+
                        bb.addAndGet(oneOfTheDimension);
                    }
                });
        //|a|
        double aaa = Math.sqrt(aa.doubleValue());
        //|b|
        double bbb = Math.sqrt(bb.doubleValue());
        //|a|*|b|
        //double aabb = aaa * bbb;
        BigDecimal aabb = BigDecimal.valueOf(aaa).multiply(BigDecimal.valueOf(bbb));
        //similarity=a.b/|a|*|b|
        //double cos = ab.get() / aabb.doubleValue();
        double cos = BigDecimal.valueOf(ab.get()).divide(aabb, 9, BigDecimal.ROUND_HALF_UP).doubleValue();
        return cos;
    }


}
