package com.example.iplan_data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.iplan_data.entity.PlanData;
import com.example.iplan_data.entity.vo.HotWordsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * plan data table Mapper interface
 * </p>
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Mapper
public interface PlanDataMapper extends BaseMapper<PlanData> {

    /**
     * Search by hot words
     *
     * The username was fuzzy queried according to the hot word comparison topic,
     * and the number of the hot word (topic) was counted according to the username group,
     * and the number of the hot word (topic) was sorted in descending order by the frequency of occurrence
     * @param words
     * @return
     */
    @Select("SELECT t.user_name, count( t.user_name ) AS titleCount  " +
            "FROM plan_data t  WHERE t.title LIKE '%${words}%' " +
            "GROUP BY t.user_name  ORDER BY titleCount DESC")
    List<HotWordsVo> selectHotWordsByGroupByUserName(@Param("words") String words);

}
