package com.sugarfree.service;

import com.sugarfree.constant.Enum;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/16
 */
public interface PointService {

    //订阅消耗积分

    /**
     * 获得我的积分
     * @param openId
     * @return
     */
    int getPointByOpenId(String openId);

    /**
     * 通过事件查看需要多少积分
     * @param pointEvent
     * @return
     */
    int getPointByEvent(Enum.PointEvent pointEvent);

    /**
     * 更新用户积分
     * @param openId
     * @param pointEvent
     */
    void updatePoint(String openId,Enum.PointEvent pointEvent,String recommendOrInviteOpenId);

    /**
     * 订阅扣除用户积分
     * @param openId
     * @param point 扣除的积分
     */
    void updatePoint(String openId,int point,String content);

}
