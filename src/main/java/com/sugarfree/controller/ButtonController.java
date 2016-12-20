package com.sugarfree.controller;

import com.google.gson.Gson;
import com.sugarfree.dao.model.TArticle;
import com.sugarfree.service.ArticleService;
import com.sugarfree.service.FormInfoService;
import com.sugarfree.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    private FormInfoService formInfoService;

    @PostMapping(value = "/send/user/{userId}/article/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendTemplateMessage(@PathVariable Integer articleId,@PathVariable Integer userId,String uuid) throws WxErrorException {
        if(!"7ef91c4b34ab4cfebe5cb806f1ae8648".equals(uuid)){
            log.error("非法调用");
            return ResponseEntity.status(400).build();
        }
        boolean flag = this.subscriberService.sendTempleMessage(articleId, userId);
        if(flag){
            log.info("send success");
            return ResponseEntity.ok().build();
        }else{
            log.info("send fail");
            return ResponseEntity.status(500).build();
        }
    }

    //会飞的黄油粉丝信息登记回调

    @PostMapping(value = "/gson/form/call", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity formGsonCall(@RequestBody Object object) throws WxErrorException {
        String content = new Gson().toJson(object);
        log.info(content);
        formInfoService.saveFormInfo(content);
        return ResponseEntity.ok().build();
    }

}
