package com.sugarfree.service;

import com.sugarfree.dao.model.TSubscriber;

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
     * @param openId
     * @return
     */
    TSubscriber getSubscriberByOpenId(int openId, int menuId);
}
