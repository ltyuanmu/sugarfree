package com.sugarfree.controller;

import com.sugarfree.dao.model.TArticle;
import com.sugarfree.service.ArticleService;
import com.sugarfree.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName: 按钮视图
 * @Description:
 * @author: LT
 * @date: 2016/11/16
 */
@RestController
@Slf4j
public class ButtonController {

    @Autowired
    private SubscriberService subscriberService;

    @PostMapping(value = "/send/article/{articleId}/user/{userId}")
    public ResponseEntity sendTemplateMessage(@PathVariable Integer articleId,Integer userId) throws WxErrorException {
        this.subscriberService.sendTempleMessage(articleId,userId);
        return ResponseEntity.ok().build();
    }
}
