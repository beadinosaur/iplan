package com.example.iplan_data.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.iplan_data.contentSimilarity.tokenizer.Tokenizer;
import com.example.iplan_data.contentSimilarity.tokenizer.Word;
import com.example.iplan_data.entity.PlanData;
import com.example.iplan_data.entity.TitleFrequency;
import com.example.iplan_data.mapper.PlanDataMapper;
import com.example.iplan_data.mapper.TitleFrequencyMapper;
import com.example.iplan_data.service.ITitleFrequencyService;
import com.example.iplan_data.util.CheckUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * Agenda topic noun word frequency statistics table service implementation class
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-21
 */
@Service
public class TitleFrequencyServiceImpl extends ServiceImpl<TitleFrequencyMapper, TitleFrequency> implements ITitleFrequencyService {
    @Resource
    private TitleFrequencyMapper titleFrequencyMapper;
    @Resource
    private PlanDataMapper planDataMapper;

    @Override
    public void saveOrUpdate() {
        //Set up query
        List<PlanData> planDataList = planDataMapper.selectList(new QueryWrapper<PlanData>());
        Map<Word, Integer> tm = new HashMap<>();
        //The nouns in each topic are divided into words, and the frequency of occurrence is calculated and encapsulated into a map
        for (int i = 0; i < planDataList.size(); i++) {
            //Divide the topic into words
            List<Word> seg = Tokenizer.segment(planDataList.get(i).getTitle());
            //Encapsulate word segmentation into map, and accumulate the occurrence frequency of each word
            for (int x = 0; x < seg.size(); x++) {
                //If the part of speech is a noun, it is included in the aggregate
                if (seg.get(x).toString().endsWith("/n")) {
                    if (!tm.containsKey(seg.get(x))) {
                        tm.put(seg.get(x), 1);
                    } else {
                        //Statistical word frequency
                        int count = tm.get(seg.get(x)) + 1;
                        tm.put(seg.get(x), count);
                    }
                }
            }
        }
        //Take out the Chinese word segmentation with part-of-speech suffixes from pure Chinese
        List<Map.Entry<Word, Integer>> list = new ArrayList(tm.entrySet());
        Collections.sort(list, (o1, o2) -> (o1.getValue() - o2.getValue()));
        String reg = "[^\u4e00-\u9fa5]";

        //Insert word frequency statistics into a table
        TitleFrequency titleFrequency = new TitleFrequency();
        for (int k = 0; k < tm.size(); k++) {
            //Remove null characters with hot words

            String word = list.get(k).getKey().toString().replaceAll(reg, "");
            Integer frequency = list.get(k).getValue();
            TitleFrequency titleFrequency1 = titleFrequencyMapper.selectOne(new QueryWrapper<TitleFrequency>().lambda()
                    .eq(!StringUtils.isEmpty(word), TitleFrequency::getWords, word));

            if (CheckUtil.isEmpty(titleFrequency1)) {
                //Insert hot word
                titleFrequency.setWords(word);
                //Insert hot word frequency
                titleFrequency.setFrequency(frequency);
                titleFrequencyMapper.insert(titleFrequency);
            } else {
                //If the hot word already exists, the word frequency of the hot word is updated
                titleFrequencyMapper.updateById(titleFrequency1);
                UpdateWrapper<TitleFrequency> updateWrapper = new UpdateWrapper<>();
                //With hot words as a condition to follow the new
                updateWrapper.eq("words", word);
                //Update word frequency of hot words
                titleFrequency.setFrequency(frequency);
                titleFrequencyMapper.update(titleFrequency, updateWrapper);
            }
        }
    }


}
