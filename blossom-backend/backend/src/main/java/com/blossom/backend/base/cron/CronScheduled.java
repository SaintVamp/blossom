package com.blossom.backend.base.cron;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CronScheduled {
    private static final Logger log = LoggerFactory.getLogger(CronScheduled.class);

    @Autowired
    private CronService cronService;

    /**
     * 进行定时通知任务的触发
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void cronNotice() {
        log.info("定时任务开始执行");
        cronService.notice();
    }
}
