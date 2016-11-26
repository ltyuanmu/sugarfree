package com.sugarfree.service.impl;

import com.sugarfree.configuration.ShareProperties;
import com.sugarfree.dao.mapper.TSubscriberMapper;
import com.sugarfree.dao.mapper.TWxUserMapper;
import com.sugarfree.dao.model.TArticle;
import com.sugarfree.dao.model.TSubscriber;
import com.sugarfree.dao.model.TWxUser;
import com.sugarfree.service.ArticleService;
import com.sugarfree.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */
@Service
@Transactional
@Slf4j
public class SubscriberServiceImpl implements SubscriberService{

    @Autowired
    private TSubscriberMapper subscriberMapper;
    @Autowired
    private WxMpService wxService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TWxUserMapper tWxUserMapper;
    @Autowired
    private ShareProperties shareProperties;

    @Override
    public void insert(TSubscriber subscriber) {
        subscriberMapper.insert(subscriber);
    }

    @Override
    public TSubscriber getSubscriberByUserId(int userId, int menuId) {
        TSubscriber tSubscriber = new TSubscriber();
        tSubscriber.setFkWxUserId(userId);
        tSubscriber.setFkMenuId(menuId);
        return subscriberMapper.selectOne(tSubscriber);
    }

    @Override
    public void sendTempleMessage(Integer articleId, Integer userId) throws WxErrorException {
        TArticle article = articleService.getArticleById(articleId);
        if(article==null){
            log.error("推送文章:{}为空",articleId);
            return;
        }
        TWxUser tWxUser = this.tWxUserMapper.selectByPrimaryKey(userId);
        if(tWxUser == null) {
            log.error("推送用户:{}为空",userId);
            return;
        }
        String url = this.shareProperties.getShareArticleUrl(article.getId(), null, tWxUser.getOpenId());
        List<WxMpTemplateData> list =  new ArrayList<>();
        WxMpTemplateData firrst = new WxMpTemplateData("first",article.getTitle(),"#D2691E");
        WxMpTemplateData keywod1 = new WxMpTemplateData("keyword1","第"+article.getClassTime()+"课","#D2691E");
        WxMpTemplateData keywod2 = new WxMpTemplateData("keyword2",tWxUser.getNickname(),"#D2691E");
        WxMpTemplateData remark = new WxMpTemplateData("first","\n点击阅读好吃到飞起来的烘焙内容","#D2691E");
        list.add(firrst);
        list.add(keywod1);
        list.add(keywod2);
        list.add(remark);
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .templateId("_FTxMyVjVDgHJlJO6RK1fZuW3EgNYr8q8KpH5BHC4AQ")
                .toUser(tWxUser.getOpenId())
                .url(url)
                .topColor("#D2691E")
                .data(list).build();
        this.wxService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    }
}
