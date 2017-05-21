package com.sugarfree.service;

import com.sugarfree.constant.Enum;
import com.sugarfree.dao.model.TCsol;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.io.File;

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
    void deletePoint(String openId,int point,String content);

    /**
     * 通过积分劵 添加积分
     * @param openId 用户openId
     * @param voucherCode 积分码
     * @return 得到的信息
     */
    String addPointForVoucher(String openId,String voucherCode) throws WxErrorException;

    /**
     * 是否订阅专注添加积分
     * @param openId openId
     * @param recommendId 被邀请人的id
     * @return 返回结果
     */
    boolean isSubscriberAddPoint(String openId,String recommendId);

    /**
     * 对应所有用户的兑换券 添加积分
     * @param openId
     * @param csol
     * @return
     */
    String addPointForOneVoucher(String openId,TCsol csol) throws WxErrorException;

    /**
     * 通过openId获得该用户积分回复消息
     * @param openId
     * @return
     */
    String getPointMag(String openId) throws WxErrorException;


    /**
     * 获得底图
     */
    File getBaseMap();
}
