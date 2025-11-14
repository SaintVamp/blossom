package com.blossom.backend.base.param;

import lombok.Getter;

/**
 * 参数枚举
 *
 * @author xzzz
 */
public enum ParamEnum {

    /**
     * 文章的 web 端访问路径
     */
    WEB_ARTICLE_URL(false, 0,""),

    /**
     * 文章日志过期天数
     */
    ARTICLE_LOG_EXP_DAYS(false, 0,""),

    /**
     * 文章回收站过期天数
     */
    ARTICLE_RECYCLE_EXP_DAYS(false, 0,""),

    /**
     * 和风天气KEY
     */
    HEFENG_KEY(true, 20,""),

    /**
     * 和风天气 HOST
     */
    HEFENG_HOST(false, 100,""),

    /**
     * 开启和风天气
     */
    HEFENG_ENABLED(false, 5,""),

    /**
     * GITEE key
     */
    GITEE_ACCESS_TOKEN(true, 20,""),

    /**
     * 备份路径
     */
    BACKUP_PATH(false, 0,""),

    /**
     * 备份过期天数
     */
    BACKUP_EXP_DAYS(false, 0,""),

    /**
     * BLOSSOM 对象存储地址
     */
    BLOSSOM_OBJECT_STORAGE_DOMAIN(false, 0,"http://www.google.com/"),

    /**
     * 服务器JWT加密字符串
     */
    SERVER_JWT_SECRET(true, 9999,""),

    /**
     * 开启首页流量监控
     */
    SENTINEL_ENABLED(false, 5,""),

    /**
     * 过期时间 - 服务器
     */
    SERVER_MACHINE_EXPIRE(false, 0,""),

    /**
     * 过期时间 - 域名
     */
    SERVER_DOMAIN_EXPIRE(false, 0,""),

    /**
     * 过期时间 - HTTPS 证书
     */
    SERVER_HTTPS_EXPIRE(false, 0,""),

    /**
     * 过期时间 - 数据库
     */
    SERVER_DATABASE_EXPIRE(false, 0,""),

    /**
     * 开启企业微信通知
     */
    NOTICE_WORK_ENABLED(false, 5,""),

    /**
     * 开启邮箱通知
     */
    NOTICE_EMAIL_ENABLED(false, 5,""),

    /**
     * 开启IYUU通知
     */
    NOTICE_IYUU_ENABLED(false, 5,""),

    /**
     * IYUU密钥
     */
    NOTICE_IYUU_TOKEN(false, 0,""),

    /**
     * 邮件密码
     */
    NOTICE_EMAIL_SMTP_PWD(false, 0,""),

    /**
     * 邮件用户名
     */
    NOTICE_EMAIL_SMTP_USER(false, 0,""),

    /**
     * 邮件服务器
     */
    NOTICE_EMAIL_SMTP_SERVER(false, 0,""),

    /**
     * 企微手机号
     */
    NOTICE_WORK_MOBILE(false, 0,""),

    /**
     * 企微用户ID
     */
    NOTICE_WORK_USER_ID(false, 0,""),

    /**
     * 企微代理ID
     */
    NOTICE_WORK_AGENT_ID(false, 0,""),

    /**
     * 企微公司ID
     */
    NOTICE_WORK_CORP_ID(false, 0,""),

    /**
     * 企微公司密钥
     */
    NOTICE_WORK_CORP_SECRET(false, 0,""),

    /**
     * 企微TOKEN
     */
    NOTICE_WORK_TOKEN(false, 0,""),
    ;

    /**
     * 是否脱敏
     */
    @Getter
    private final Boolean masking;

    /**
     * 脱敏长度
     */
    @Getter
    private final Integer maskingLength;

    /**
     * 默认值
     */
    @Getter
    private final String defaultValue;

    ParamEnum(Boolean masking, Integer maskingLength, String defaultValue) {
        this.masking = masking;
        this.maskingLength = maskingLength;
        this.defaultValue = defaultValue;
    }
}
