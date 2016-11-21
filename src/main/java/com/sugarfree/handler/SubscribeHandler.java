package com.sugarfree.handler;

import com.sugarfree.builder.ImageBuilder;
import com.sugarfree.builder.TextBuilder;
import com.sugarfree.constant.Enum;
import com.sugarfree.service.PointService;
import com.sugarfree.service.WxUserSubscribeService;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Map;

/**
 * @author Binary Wang
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private WxUserSubscribeService wxUserSubscribeService;
    @Autowired
    private PointService pointService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService weixinService,
            WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        WxMpUser userWxInfo = weixinService.getUserService()
            .userInfo(wxMessage.getFromUser(), null);

        if (userWxInfo != null) {
            // 可以添加关注用户到本地
            //获得用户二维码
            WxMpQrCodeTicket wxUserQRImage = wxUserSubscribeService.getWxUserQRImage(wxMessage.getFromUser());
            //上传用户二维码
            File file = weixinService.getQrcodeService().qrCodePicture(wxUserQRImage);
            WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
            this.wxUserSubscribeService.saveWxUser(userWxInfo,wxUserQRImage);
            //更新积分 qrscene_oGQqrxGgynJzYJDjrWmhoUWhDSOc
            String recommendId = "";
            if(StringUtils.isNotEmpty(wxMessage.getEventKey())&&wxMessage.getEventKey().startsWith("qrscene_")){
                recommendId = wxMessage.getEventKey().substring(wxMessage.getEventKey().indexOf("_")+1);
                pointService.updatePoint(recommendId, Enum.PointEvent.RECOMMEND,userWxInfo.getOpenId());
            }
            pointService.updatePoint(userWxInfo.getOpenId(), Enum.PointEvent.SUBSCRIBE,recommendId);
            //发送消息
            StringBuilder contentSB = new StringBuilder("");
            contentSB.append("这是你的专属二维码。欢迎把这个二维码分享给你的朋友，朋友扫码关注后，你")
                    .append("们可以各得到一个积分。积分可用于订阅烘焙课程。").append("\n")
                    .append("希望你在香甜世界里学得开心~");
            WxMpKefuMessage keFuMessage=WxMpKefuMessage.TEXT().content(contentSB.toString()).toUser(userWxInfo.getOpenId()).build();
            weixinService.getKefuService().sendKefuMessage(keFuMessage);
            return new ImageBuilder().build(uploadResult.getMediaId(),wxMessage,weixinService);
        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
            throws Exception {
        //TODO
        return null;
    }

}
