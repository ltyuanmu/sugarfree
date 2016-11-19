package com.sugarfree.service.impl;

import com.sugarfree.dao.mapper.TSubscriberMapper;
import com.sugarfree.dao.model.TSubscriber;
import com.sugarfree.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */
@Service
public class SubscriberServiceImpl implements SubscriberService{

    @Autowired
    private TSubscriberMapper subscriberMapper;

    @Override
    public void insert(TSubscriber subscriber) {
        subscriberMapper.insert(subscriber);
    }

    @Override
    public TSubscriber getSubscriberByOpenId(int openId, int menuId) {
        TSubscriber tSubscriber = new TSubscriber();
        tSubscriber.setFkWxUserId(openId);
        tSubscriber.setFkMenuId(menuId);
        return subscriberMapper.selectOne(tSubscriber);
    }
}
