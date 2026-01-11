package com.xiaolou.bi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaolou.bi.common.ErrorCode;
import com.xiaolou.bi.constant.CommonConstant;
import com.xiaolou.bi.exception.BusinessException;
import com.xiaolou.bi.exception.ThrowUtils;
import com.xiaolou.bi.model.dto.chart.ChartAddRequest;
import com.xiaolou.bi.model.dto.chart.ChartQueryRequest;
import com.xiaolou.bi.model.dto.chart.GenChartByAiRequest;
import com.xiaolou.bi.model.entity.Chart;
import com.xiaolou.bi.service.ChartService;
import com.xiaolou.bi.mapper.ChartMapper;
import com.xiaolou.bi.utils.ExcelUtils;
import com.xiaolou.bi.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
* @author l
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2026-01-10 16:37:25
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

    @Override
    public void validChart(Chart chart, boolean add) {
        if (chart == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String goal = chart.getGoal();
        String name = chart.getName();
        String chartData = chart.getChartData();
        String chartType = chart.getChartType();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(goal, chartData, chartType), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "图表名称过长");
        }
        if (StringUtils.isNotBlank(chartType) && chartType.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "图表类型过长");
        }
    }

    /**
     * 获取查询包装类
     *
     * @param chartQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Chart> getQueryWrapper(ChartQueryRequest chartQueryRequest) {
        QueryWrapper<Chart> queryWrapper = new QueryWrapper<>();
        if (chartQueryRequest == null) {
            return queryWrapper;
        }
        Long id = chartQueryRequest.getId();
        String goal = chartQueryRequest.getGoal();
        String name = chartQueryRequest.getName();
        String chartType = chartQueryRequest.getChartType();
        Long userId = chartQueryRequest.getUserId();
        String sortField = chartQueryRequest.getSortField();
        String sortOrder = chartQueryRequest.getSortOrder();

        queryWrapper.eq(ObjectUtils.isNotEmpty(id) && id > 0, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(goal), "goal", goal);
        queryWrapper.eq(StringUtils.isNotBlank(chartType), "chartType", chartType);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public String getGenerateChartUserMessage(GenChartByAiRequest genChartByAiRequest, MultipartFile multipartFile) {
        String goal = genChartByAiRequest.getGoal();
        String name = genChartByAiRequest.getName();
        String chartType = genChartByAiRequest.getChartType();
        String result = ExcelUtils.excelToCsv(multipartFile);
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(goal).append("\n");
        userMessage.append(name).append("\n");
        userMessage.append(chartType).append("\n");
        userMessage.append(result).append("\n");
        return userMessage.toString();
    }

    public static final String GENERATE_CHART_SYSTEM_PROMPT =
            "你是一个专业的数据分析师。请严格按照以下要求处理数据：\n" +
                    "\n" +
                    "我会提供以下内容：\n" +
                    "1、分析需求：\n" +
                    "{具体的数据分析需求或目标}\n" +
                    "2、图表名称：\n" +
                    "{图表的标题名称}\n" +
                    "3、图表类型：\n" +
                    "{指定的图表类型，如：折线图、柱状图、饼图等}\n" +
                    "4、原始数据：\n" +
                    "{CSV格式的数据，使用逗号作为分隔符}\n" +
                    "\n" +
                    "输出要求：\n" +
                    "1. 必须严格按照以下分隔符格式输出内容\n" +
                    "2. 图表配置必须是标准的JSON格式，可以直接被Echarts V5使用\n" +
                    "3. 分析结论部分必须且只能包含：\n" +
                    "   - 具体的数据变化描述\n" +
                    "   - 趋势说明\n" +
                    "4. 严禁在分析结论中包含：\n" +
                    "   - 任何分析过程\n" +
                    "   - 思考过程\n" +
                    "   - 中间步骤\n" +
                    "   - '从数据可以看出'、'数据显示'等引导词\n" +
                    "   - 'reasoningContent='等任何额外内容\n" +
                    "   - 任何解释性文字\n" +
                    "5. 分析结论必须是简洁的数据描述，直接呈现结果\n" +
                    "\n" +
                    "输出格式示例：\n" +
                    "【【【【【\n" +
                    "{Echarts配置JSON}\n" +
                    "【【【【【\n" +
                    "{包含具体数据变化和趋势的结论，尽量分析地详细点}\n" +
                    "【【【【【";

}




