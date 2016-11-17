package com.sugarfree.service;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.catalina.User;

/**
 * @ClassName: ${}
 * @Description: 微信用户关注服务
 * @author: LT
 * @date: 2016/11/17
 */
public interface WxUserSubscribeService {
    //用户关注保存用户信息
    void saveWxUser(WxMpUser userWxInfo);

    //获得用户的二维码
    String getWxUserQRImage(String openId);
}
