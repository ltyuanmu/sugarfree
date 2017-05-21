package com.sugarfree.service;

import com.sugarfree.dao.model.TWxUser;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.catalina.User;

import java.util.Map;

/**
 * @ClassName: ${}
 * @Description: 微信用户关注服务
 * @author: LT
 * @date: 2016/11/17
 */
public interface WxUserSubscribeService {

    /**
     * 保存用户信息
     * @param userWxInfo
     * @param qrCodeTicket
     */
    TWxUser saveWxUser(WxMpUser userWxInfo,WxMpQrCodeTicket qrCodeTicket);

    /**
     * 获得用户的二维码
     * @param openId
     * @return
     */
    WxMpQrCodeTicket getWxUserQRImage(String openId);

    /**
     * 用户获得临时二维码
     * @param wxUserId
     * @return
     */
    WxMpQrCodeTicket getWxUserIMPQRImage(Integer wxUserId);
    /**
     * 取消关注
     * @param openId
     */
    void unSubscribeWxUser(String openId);

    /**
     * 通过openId 更新用户
     * @param openId
     * @param tWxUser
     */
    void updateWxUserByOpenId(String openId, TWxUser tWxUser);

    /**
     * 通过openId 获得用户
     * @param openId
     * @return
     */
    TWxUser getWxUserByOpenId(String openId);

    /**
     * 通过用户id获得用户
     * @param wxUserId
     * @return
     */
    TWxUser getWxUserByWxUserId(Integer wxUserId);
}
