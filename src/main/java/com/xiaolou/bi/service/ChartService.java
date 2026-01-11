package com.xiaolou.bi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaolou.bi.model.dto.chart.ChartAddRequest;
import com.xiaolou.bi.model.dto.chart.ChartQueryRequest;
import com.xiaolou.bi.model.dto.chart.GenChartByAiRequest;
import com.xiaolou.bi.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author l
* @description 针对表【chart(图表信息表)】的数据库操作Service
* @createDate 2026-01-10 16:37:25
*/
public interface ChartService extends IService<Chart> {

    void validChart(Chart chart, boolean add);

    QueryWrapper<Chart> getQueryWrapper(ChartQueryRequest chartQueryRequest);

    String getGenerateChartUserMessage(GenChartByAiRequest genChartByAiRequest, MultipartFile multipartFile);

}
