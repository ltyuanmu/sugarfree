package com.sugarfree.handler;

import com.sugarfree.builder.ImageBuilder;
import com.sugarfree.builder.TextBuilder;
import com.sugarfree.configuration.ShareProperties;
import com.sugarfree.dao.model.TWxUser;
import com.sugarfree.service.PointService;
import com.sugarfree.service.WxUserSubscribeService;
import com.sugarfree.utils.UrlImageUtil;
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
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MsgHandler extends AbstractHandler {

    @Autowired
    private PointService pointService;
    @Autowired
    private ShareProperties shareProperties;
    @Autowired
    private WxUserSubscribeService wxUserSubscribeService;
    @Autowired
    private UrlImageUtil urlImageUtil;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService weixinService,
            WxSessionManager sessionManager) throws WxErrorException {

        if (!wxMessage.getMsgType().equals(WxConsts.XML_MSG_EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.getKefuService().kfOnlineList()
                    .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(wxMessage.getContent())){
            return null;
        }

        /*if("积分".equals(wxMessage.getContent())){
            //int point = pointService.getPointByOpenId(wxMessage.getFromUser());
            //String message = "您的积分为:"+point;
            String message = this.pointService.getPointMag(wxMessage.getFromUser());
            return new TextBuilder().build(message,wxMessage,weixinService);
        }else*/ if(StringUtils.isNotEmpty(wxMessage.getContent())&&wxMessage.getContent().startsWith("DHJF_")){
            String content = wxMessage.getContent();
            String code = content.replaceFirst("DHJF_","");
            String message = pointService.addPointForVoucher(wxMessage.getFromUser(), code);
            return new TextBuilder().build(message,wxMessage,weixinService);
        }/*else if("二维码".equals(wxMessage.getContent())||"我的二维码".equals(wxMessage.getContent())) {
            //获得个人专属二维码
            TWxUser wxUser = this.wxUserSubscribeService.getWxUserByOpenId(wxMessage.getFromUser());
            //个人二维码如果不是永久则返回临时二维码
            File file;
            if(StringUtils.isNotEmpty(wxUser.getQrTicket())){
                WxMpQrCodeTicket wxUserQRImage = new WxMpQrCodeTicket();
                wxUserQRImage.setUrl(wxUser.getQrUrl());
                wxUserQRImage.setTicket(wxUser.getQrTicket());
                file = weixinService.getQrcodeService().qrCodePicture(wxUserQRImage);
            }else{
                WxMpQrCodeTicket wxUserIMPQRImage = this.wxUserSubscribeService.getWxUserIMPQRImage(wxUser.getId());
                file = weixinService.getQrcodeService().qrCodePicture(wxUserIMPQRImage);
            }
            //添加低图拼接
            try {
                File baseMap = pointService.getBaseMap();
                file = urlImageUtil.joinImage(file, baseMap);
                logger.info("file size:{}",file.length());
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
            WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
            String message = uploadResult.getMediaId();
            return new ImageBuilder().build(message, wxMessage, weixinService);
        }*/else if("巧克力烘焙".equals(wxMessage.getContent())){
            return new TextBuilder().build("https://pan.baidu.com/s/1nv37FXf",wxMessage,weixinService);
        }else if("原料".equals(wxMessage.getContent())){
            File file = new File("/home/sugarfree/wx_front/img/yuanliao.jpg");
            if(!file.exists()){
                return null;
            }
            WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
            String message = uploadResult.getMediaId();
            return new ImageBuilder().build(message, wxMessage, weixinService);
        }else if("巧克力".equals(wxMessage.getContent())){
            File file = new File("/home/sugarfree/wx_front/img/qiaokeli.jpg");
            if(!file.exists()){
                return null;
            }
            WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
            String message = uploadResult.getMediaId();
            return new ImageBuilder().build(message, wxMessage, weixinService);
        }else if("和果子".equals(wxMessage.getContent())){
            File file = new File("/home/sugarfree/wx_front/img/heguozi.jpg");
            if(!file.exists()){
                return null;
            }
            WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
            String message = uploadResult.getMediaId();
            return new ImageBuilder().build(message, wxMessage, weixinService);
        }else if("慕斯".equals(wxMessage.getContent())){
            File file = new File("/home/sugarfree/wx_front/img/musi.jpg");
            if(!file.exists()){
                return null;
            }
            WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
            String message = uploadResult.getMediaId();
            return new ImageBuilder().build(message, wxMessage, weixinService);
        }else if("猫头鹰饼干".equals(wxMessage.getContent())){
            File file = new File("/home/sugarfree/wx_front/img/maotouyingbinggan.jpg");
            if(!file.exists()){
                return null;
            }
            WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
            String message = uploadResult.getMediaId();
            return new ImageBuilder().build(message, wxMessage, weixinService);
        }else if("车轮泡芙".equals(wxMessage.getContent())){
            File file = new File("/home/sugarfree/wx_front/img/chelunpaofu.jpg");
            if(!file.exists()){
                return null;
            }
            WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
            String message = uploadResult.getMediaId();
            return new ImageBuilder().build(message, wxMessage, weixinService);
        }else if("烧烧果实".equals(wxMessage.getContent())){
            File file = new File("/home/sugarfree/wx_front/img/shaoshaoguoshi.png");
            if(!file.exists()){
                return null;
            }
            WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
            String message = uploadResult.getMediaId();
            return new ImageBuilder().build(message, wxMessage, weixinService);
        }else if("百万富翁".equals(wxMessage.getContent())){
            File file = new File("/home/sugarfree/wx_front/img/baiwanfuweng.png");
            if(!file.exists()){
                return null;
            }
            WxMediaUploadResult uploadResult = weixinService.getMaterialService().mediaUpload("image", file);
            String message = uploadResult.getMediaId();
            return new ImageBuilder().build(message, wxMessage, weixinService);
        }else if("烘焙书".equals(wxMessage.getContent())){
            return new TextBuilder().build("https://pan.baidu.com/s/1i4Jei3N",wxMessage,weixinService);
        }else if("积分课程".equals(wxMessage.getContent())){
            StringBuilder contentSB = new StringBuilder();
            contentSB.append("跟着安琪学烘焙：").append(shareProperties.getServerUrl()).append("/link/1001").append("\n")
                    .append("烘焙地图：").append(shareProperties.getServerUrl()).append("/link/1004");
            return new TextBuilder().build(contentSB.toString(),wxMessage,weixinService);
        }else{
          //  StringBuilder sb = new StringBuilder();
          //  sb.append("<a href=\"").append(shareProperties.getServerUrl()).append("/link/1101").append("\">").append("了解会飞的黄油").append("</a>").append("\n");
          //  return new TextBuilder().build(sb.toString(),wxMessage,weixinService);
            return null;
        }
        //TODO 组装回复消息
        //String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
        //return new TextBuilder().build(content, wxMessage, weixinService);
    }

}
