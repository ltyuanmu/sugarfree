package com.sugarfree.service;

import com.sugarfree.dao.model.TMenu;
import com.sugarfree.dao.model.TSubscriber;
import com.sugarfree.dao.model.TWxUser;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */
public interface SubscriberService {

    /**
     * 添加订阅记录
     * @param subscriber
     */
    void insert(TSubscriber subscriber);

    /**
     * 根据用户id获取订阅信息
     * @param userId
     * @return
     */
    TSubscriber getSubscriberByUserId(int userId, int menuId);

    /**
     * 发送模板消息
     * @param articleId
     * @param userId
     */
    boolean sendTempleMessage(Integer articleId,Integer userId)throws WxErrorException;

    /**
     * 订阅文章
     * @param wxUser
     * @param menu
     * @return 0:积分不够 1.订阅成功 2.已订阅
     * @throws WxErrorException
     */
    String subscriberArticle(TWxUser wxUser,TMenu menu)throws WxErrorException;

    /**
     * 订阅文章
     * @param wxUser
     * @param menu
     * @return 0:取消订阅失败 1.取消订阅成功
     * @throws WxErrorException
     */
    String unSubscriberArticle(TWxUser wxUser,TMenu menu)throws WxErrorException;

    /**
     * 通过用户获得订阅的菜单集合
     * @param wxUser
     * @return
     * @throws WxErrorException
     */
    List<TMenu> getSubscriberList(TWxUser wxUser)throws WxErrorException;

    /**
     * 订阅文章推送
     */
    void subscriberArticlePush();

}
