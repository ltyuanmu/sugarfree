package com.sugarfree.service.impl;

import com.google.common.base.*;
import com.google.common.collect.Lists;
import com.sugarfree.configuration.ShareProperties;
import com.sugarfree.dao.mapper.*;
import com.sugarfree.dao.model.*;
import com.sugarfree.service.ArticleService;
import com.sugarfree.service.PointService;
import com.sugarfree.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/11/19.
 */
@Service
@Transactional
@Slf4j
public class SubscriberServiceImpl implements SubscriberService{

    @Autowired
    private TSubscriberMapper tSubscriberMapper;
    @Autowired
    private WxMpService wxService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TWxUserMapper tWxUserMapper;
    @Autowired
    private ShareProperties shareProperties;
    @Autowired
    private PointService pointService;
    @Autowired
    private TSubscriberPushMapper tSubscriberPushMapper;
    @Autowired
    private TMenuMapper tMenuMapper;
    @Autowired
    private SubscriberDao subscriberDao;

    @Override
    public void insert(TSubscriber subscriber) {
        tSubscriberMapper.insertSelective(subscriber);
    }

    @Override
    public TSubscriber getSubscriberByUserId(int userId, int menuId) {
        TSubscriber tSubscriber = new TSubscriber();
        tSubscriber.setFkWxUserId(userId);
        tSubscriber.setFkMenuId(menuId);
        tSubscriber.setStatus("0");
        return tSubscriberMapper.selectOne(tSubscriber);
    }

    @Override
    public boolean sendTempleMessage(Integer articleId, Integer userId) throws WxErrorException {
        TArticle article = articleService.getArticleById(articleId);
        if(article==null){
            log.error("推送文章:{}为空",articleId);
            return false;
        }
        TWxUser tWxUser = this.tWxUserMapper.selectByPrimaryKey(userId);
        if(tWxUser == null) {
            log.error("推送用户:{}为空",userId);
            return false;
        }
        String url = this.shareProperties.getShareArticleUrl(article.getId(),tWxUser.getOpenId(),true);
        List<WxMpTemplateData> list =  new ArrayList<>();
        WxMpTemplateData first = new WxMpTemplateData("first",article.getTitle(),"#D2691E");
        WxMpTemplateData keyword1 = new WxMpTemplateData("keyword1","第"+article.getClassTime()+"课","#D2691E");
        WxMpTemplateData keyword2 = new WxMpTemplateData("keyword2",tWxUser.getNickname(),"#D2691E");
        WxMpTemplateData remark = new WxMpTemplateData("remark","\n点击阅读好吃到飞起来的烘焙内容","#D2691E");
        list.add(first);
        list.add(keyword1);
        list.add(keyword2);
        list.add(remark);
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .templateId(shareProperties.getTempletId())
                .toUser(tWxUser.getOpenId())
                .url(url)
                .topColor("#D2691E")
                .data(list).build();
        this.wxService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        return true;
    }

    @Override
    public String subscriberArticle(TWxUser wxUser, TMenu menu) throws WxErrorException {
        TSubscriber tSubscriber = this.getSubscriberByUserId(wxUser.getId(), menu.getId());
        if(null != tSubscriber){
            //用户已经订阅
            return "2";
        }
        if (menu.getPoint() > wxUser.getPoint())
        {
            //需要积分大于用户已有积分提示积分不够
            return "0";
        }else{
            //添加积分变更历史记录，扣除积分
            pointService.deletePoint(wxUser.getOpenId(),menu.getPoint(), "订阅"+menu.getName()+"扣除积分");
            //添加订阅记录
            TSubscriber subscriber = new TSubscriber();
            subscriber.setCreateTime(new Date());
            subscriber.setFkMenuId(menu.getId());
            subscriber.setFkWxUserId(wxUser.getId());
            subscriber.setStatus("0");
            subscriber.setLastClassTime(0);
            this.insert(subscriber);
            //判断是否为早上8点和晚上8点 如果是的话发送 否则不进行发送
            //任何时候需要及时推送
            /*Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(hour>=8&&hour<20){*/
                //进行推送消息 并且修改数据
                //获得订阅记录
                TSubscriber subsc = this.getSubscriberByUserId(wxUser.getId(), menu.getId());
                //获得文章
                TArticle article = this.articleService.getArticleByEnumIdClassTime(menu.getId(),1);
                if(article!=null){
                    //发送文章
                    boolean flag=this.sendTempleMessage(article.getId(),wxUser.getId());
                    if(flag){
                        //维护推送数据
                        TSubscriber modifySubscriber = new TSubscriber();
                        modifySubscriber.setId(subsc.getId());
                        modifySubscriber.setLastClassTime(1);
                        modifySubscriber.setEndTime(new Date());
                        this.tSubscriberMapper.updateByPrimaryKeySelective(modifySubscriber);
                        //添加推送历史
                        TSubscriberPush push = new TSubscriberPush();
                        push.setArticleTitle(article.getTitle());
                        push.setFkArticleId(article.getId());
                        push.setFkWxUserId(wxUser.getId());
                        push.setPushTime(modifySubscriber.getEndTime());
                        push.setStaus("1");
                        tSubscriberPushMapper.insertSelective(push);
                    }

                }
            /*}*/
        }
        return "1";
    }

    @Override
    public String unSubscriberArticle(TWxUser wxUser, TMenu menu) throws WxErrorException {
        TSubscriber tSubscriber = this.getSubscriberByUserId(wxUser.getId(), menu.getId());
        //取消订阅 删除订阅
        TSubscriber unSubscriber = new TSubscriber();
        unSubscriber.setId(tSubscriber.getId());
        unSubscriber.setStatus("1");
        this.tSubscriberMapper.updateByPrimaryKeySelective(unSubscriber);
        return "1";
    }

    @Override
    public List<TMenu> getSubscriberList(TWxUser wxUser) throws WxErrorException {
        TSubscriber subscriber = new TSubscriber();
        subscriber.setStatus("0");
        subscriber.setFkWxUserId(wxUser.getId());
        List<TSubscriber> list = this.tSubscriberMapper.select(subscriber);
        if(CollectionUtils.isEmpty(list)){
            return Lists.newArrayList();
        }else{
            List<Integer> menuList  = list.stream().map(TSubscriber::getFkMenuId).distinct().collect(Collectors.toList());
            Example example = new Example(TMenu.class);
            example.createCriteria().andIn("id",menuList).andNotEqualTo("menuDesc","");
            example.orderBy("id").asc();
            List<TMenu> tMenus = this.tMenuMapper.selectByExample(example);
            return Optional.ofNullable(tMenus).orElse(Lists.newArrayList());
        }
    }

    @Override
    public void subscriberArticlePush(){
        //获得需要推送的订阅信息
        List<ArticlePushBean> list = this.subscriberDao.getSubscriberArticle();
        for(ArticlePushBean articlePushBean:list){
            //log.info("subscriberArticlePush start,userId:{},articleId:{}",articlePushBean.getFkWxUserId(),articlePushBean.getFkArticleId());
            try {
                TArticle article = this.articleService.getArticleById(articlePushBean.getFkArticleId());
                if(article!=null){
                    //发送文章
                    boolean flag = this.sendTempleMessage(article.getId(),articlePushBean.getFkWxUserId());
                    if(flag){
                        //维护推送数据
                        TSubscriber modifySubscriber = new TSubscriber();
                        modifySubscriber.setId(articlePushBean.getFkSubscriberId());
                        modifySubscriber.setLastClassTime(articlePushBean.getClassTime());
                        modifySubscriber.setEndTime(new Date());
                        this.tSubscriberMapper.updateByPrimaryKeySelective(modifySubscriber);
                        //添加推送历史
                        TSubscriberPush push = new TSubscriberPush();
                        push.setArticleTitle(article.getTitle());
                        push.setFkArticleId(article.getId());
                        push.setFkWxUserId(articlePushBean.getFkWxUserId());
                        push.setPushTime(modifySubscriber.getEndTime());
                        push.setStaus("1");
                        tSubscriberPushMapper.insertSelective(push);
                        log.info("subscriberArticlePush successful,userId:{},articleId:{}",articlePushBean.getFkWxUserId(),articlePushBean.getFkArticleId());
                    }else{
                        log.error("subscriberArticlePush fail,userId:{},articleId:{}",articlePushBean.getFkWxUserId(),articlePushBean.getFkArticleId());
                    }
                }
            } catch (WxErrorException e) {
                log.error(e.getMessage(),e);
                log.error("subscriberArticlePush fail,userId:{},articleId:{}",articlePushBean.getFkWxUserId(),articlePushBean.getFkArticleId());
            }
            //log.info("subscriberArticlePush end,userId:{},articleId:{}",articlePushBean.getFkWxUserId(),articlePushBean.getFkArticleId());
        }
    }
}
