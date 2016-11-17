package com.sugarfree.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 消息驱动 配置
 * Created by haijiang.mo@newtouch.cn on 2016/8/30.
 */
@Configuration
public class MessageResourceConfiguration {

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames(baseNames());
        return messageSource;
    }

    /**
     * 获取文言配置
     * @return 国际化配置文件
     */
    private String[] baseNames(){
        return new String[]{
                "messages/message"
        };
    }
}
