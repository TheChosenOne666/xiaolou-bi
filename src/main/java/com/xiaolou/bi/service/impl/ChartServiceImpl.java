package com.xiaolou.bi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaolou.bi.model.entity.Chart;
import com.xiaolou.bi.service.ChartService;
import com.xiaolou.bi.mapper.ChartMapper;
import org.springframework.stereotype.Service;

/**
* @author l
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2026-01-10 16:37:25
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}




