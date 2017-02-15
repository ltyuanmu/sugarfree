package com.sugarfree.quartz;

import com.sugarfree.service.SubscriberService;
import lombok.extern.log4j.Log4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ScheduledTasks
 * @Description: 轮训任务
 * @author: LT
 * @date: 2017/1/25
 */
@Component
@Configurable
@EnableScheduling
@Log4j
public class ScheduledTasks {

    @Autowired
    private SubscriberService subscriberService;

    @Scheduled(cron = "0 0 9 * * *")
    public void subscriberArticlePush(){
        log.info("subscriberArticlePush start");
        this.subscriberService.subscriberArticlePush();
        log.info("subscriberArticlePush end");
    }
}
