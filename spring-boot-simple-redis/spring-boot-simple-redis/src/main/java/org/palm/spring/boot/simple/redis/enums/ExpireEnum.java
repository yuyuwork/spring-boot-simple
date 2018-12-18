package org.palm.spring.boot.simple.redis.enums;

import java.util.concurrent.TimeUnit;

/**
 * 过期时间相关枚举
 *
 * @author
 * @date 2018/12/14 20:49
 */
public enum ExpireEnum {

    //未读消息的有效期为30天
    UNREAD_MSG(30L, TimeUnit.DAYS),
    //自动增长的有效期为30秒钟
    AUTO_INCREMENT(30L,TimeUnit.SECONDS),
    ;

    /** 过期时间 */
    private Long time;
    /** 时间单位 */
    private TimeUnit timeUnit;

    ExpireEnum(Long time, TimeUnit timeUnit) {
        this.time = time;
        this.timeUnit = timeUnit;
    }

    public Long getTime() {
        return time;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

}
