package com.xiaolou.bi.manager;

import com.xiaolou.bi.common.ErrorCode;
import com.xiaolou.bi.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * 提供RedissonLimiter限流基础服务
 */
@Service
public class RedissonManager {

    @Resource
    private RedissonClient redissonClient;


    public void doRateLimit(String key){
        // 创建一个限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 设置限流器速率为每秒2个请求
        rateLimiter.trySetRate(RateType.OVERALL, 2, Duration.ofSeconds(1));
        // 每当一个请求过来，尝试获取一个令牌
        boolean canOp = rateLimiter.tryAcquire(1);
        if (!canOp){
            //TOO_MANY_REQUEST_ERROR(42900, "请求过于频繁")
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST_ERROR, "操作太频繁，请稍后再试");
        }
    }
}
