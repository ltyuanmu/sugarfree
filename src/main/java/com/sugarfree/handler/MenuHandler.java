package com.sugarfree.handler;

import com.google.common.cache.CacheLoader;
import com.sugarfree.builder.ImageBuilder;
import com.sugarfree.builder.TextBuilder;
import com.sugarfree.dao.model.TWxUser;
import com.sugarfree.service.PointService;
import com.sugarfree.service.WxUserSubscribeService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Binary Wang
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MenuHandler extends AbstractHandler {
    @Autowired
    private PointService pointService;
    @Autowired
    private WxUserSubscribeService wxUserSubscribeService;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService weixinService,
            WxSessionManager sessionManager) throws WxErrorException {

        String msg = String.format("type:%s, event:%s, key:%s",
            wxMessage.getMsgType(), wxMessage.getEvent(),
            wxMessage.getEventKey());
        if (WxConsts.EVT_VIEW.equals(wxMessage.getEvent())) {
            return null;
        }
        if (WxConsts.EVT_CLICK.equals(wxMessage.getEvent())){
            if("MY_POINT".equals(wxMessage.getEventKey())){
                int point = pointService.getPointByOpenId(wxMessage.getFromUser());
                String message = "您的积分为:"+point;
                return new TextBuilder().build(message,wxMessage,weixinService);
            }else if("MY_QR_CODE".equals(wxMessage.getEventKey())) {
                //获得个人专属二维码
                TWxUser wxUser = this.wxUserSubscribeService.getWxUserByOpenId(wxMessage.getFromUser());
                if(StringUtils.isNotEmpty(wxUser.getQrTicket())){
                    WxMpQrCodeTicket wxUserQRImage = new WxMpQrCodeTicket();
                    wxUserQRImage.setUrl(wxUser.getQrUrl());
                    wxUserQRImage.setTicket(wxUser.getQrTicket());
                    File file = weixinService.getQrcodeService().qrCodePicture(wxUserQRImage);
                    WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
                    String message = uploadResult.getMediaId();
                    return new ImageBuilder().build(message, wxMessage, weixinService);
                }else{
                    WxMpQrCodeTicket wxUserIMPQRImage = this.wxUserSubscribeService.getWxUserIMPQRImage(wxUser.getId());
                    File file = weixinService.getQrcodeService().qrCodePicture(wxUserIMPQRImage);
                    WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
                    String message = uploadResult.getMediaId();
                    return new ImageBuilder().build(message, wxMessage, weixinService);
                }
            }else if("ASK_ME".equals(wxMessage.getEventKey())) {
                //获得想我提问
                File file = new File("/home/sugarfree/wx_front/img/xiangwotiwen.jpg");
                WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
                String message = uploadResult.getMediaId();
                return new ImageBuilder().build(message, wxMessage, weixinService);
            }
        }

        return WxMpXmlOutMessage.TEXT().content(msg)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
    }

  /*  public static void main(String[] args) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/img/xiangwotiwen.png");
        InputStream stream = resource.getInputStream();
    }*/
}
