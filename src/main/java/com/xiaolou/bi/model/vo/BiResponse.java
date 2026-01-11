package com.xiaolou.bi.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data

public class BiResponse implements Serializable {

    /**
     * 生成的图表数据
     */
    private String genChart;

    /**
     * 生成的分析结论
     */
    private String genResult;

    /**
     * 图表 id
     */
    private Long chartId;

    private static final long serialVersionUID = 1L;
}
