package com.sugarfree.service;

import me.chanjar.weixin.common.exception.WxErrorException;

public interface ActivityService {
    /**
     * 通过用户id获得二维码底图id
     * @param userId 用户id
     * @return 返回微信媒体id
     */
    String getActivityImg(Integer userId) throws WxErrorException;

    /**
     * 添加活动助力表
     * @param activityJoinId
     * @param helpOpenId
     */
    void activityHelp(Integer activityJoinId,String helpOpenId) throws WxErrorException;

    /**
     * 通过活动参与id获得参与人openId
     * @param activityJoinId
     * @return
     */
    String getActivityOpenId(String activityJoinId);

}
