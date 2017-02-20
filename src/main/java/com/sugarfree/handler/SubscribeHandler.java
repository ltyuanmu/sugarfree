package com.sugarfree.handler;

import com.sugarfree.builder.ImageBuilder;
import com.sugarfree.builder.TextBuilder;
import com.sugarfree.configuration.ShareProperties;
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
    @Autowired
    private ShareProperties shareProperties;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService weixinService,
            WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        WxMpUser userWxInfo = weixinService.getUserService()
            .userInfo(wxMessage.getFromUser());

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
                //添加如果这两个关注已经添加积分则不能在添加积分
                if(pointService.isSubscriberAddPoint(recommendId,userWxInfo.getOpenId())){
                    pointService.updatePoint(recommendId, Enum.PointEvent.RECOMMEND,userWxInfo.getOpenId());
                    //获得当前推荐人的积分
                    int point = this.pointService.getPointByOpenId(recommendId);
                    WxMpKefuMessage keFuMessage=WxMpKefuMessage.TEXT().content("积分变动提醒,你当前积分为"+point).toUser(recommendId).build();
                    weixinService.getKefuService().sendKefuMessage(keFuMessage);
                }
            }
            pointService.updatePoint(userWxInfo.getOpenId(), Enum.PointEvent.SUBSCRIBE,recommendId);
            //发送消息
            StringBuilder contentSB = new StringBuilder(userWxInfo.getNickname());
            /*contentSB.append(",你来啦~是闻着黄油的香味找到这儿的吗？").append("\n")
                    .append("这里是一间创造美好食物的厨房，也是体验、分享生活的好地方。")
                    .append("你可以发现来自世界各地的烘焙美食，探索和食物之间的美味关系。")
                    .append("如果你恰好喜欢烘焙，这里也有成套的烘焙秘方，你更是来对了地方！").append("\n")
                    .append("来了，就好好享受这趟烘焙之旅吧。");*/
            //需求变更 文字
            contentSB.append(",你来啦~是闻着黄油的香味找到这儿的吗？").append("\n").append("\n")
                    .append("这里是一间创造美好食物的厨房。欢迎你来和黄油一起探索美食、生活的可能性。")
                    .append("\n").append("\n")
                    .append("「专栏推荐」").append("\n").append("\n")
                    .append("/:sun跟着安琪学烘焙：教你从0开始系统入门烘焙").append("\n")
                    .append(shareProperties.getServerUrl()).append("/link/1001").append("\n")
                    .append("/:sun拍出好滋味：教你用手机拍出烘焙美食大片").append("\n")
                    .append(shareProperties.getServerUrl()).append("/link/1002").append("\n")
                    .append("/:sun烘焙地图：跟着黄油一起逛世界").append("\n")
                    .append(shareProperties.getServerUrl()).append("/link/1004");
            WxMpKefuMessage keFuMessage=WxMpKefuMessage.TEXT().content(contentSB.toString()).toUser(userWxInfo.getOpenId()).build();
            weixinService.getKefuService().sendKefuMessage(keFuMessage);
            //发送二维码消息
            WxMpKefuMessage keFuOmageMessage=WxMpKefuMessage.IMAGE().mediaId(uploadResult.getMediaId()).toUser(userWxInfo.getOpenId()).build();
            weixinService.getKefuService().sendKefuMessage(keFuOmageMessage);
            //发送最后的消息
            StringBuilder sb = new StringBuilder();
            //获得订阅的积分
            int subscribePoint = this.pointService.getPointByEvent(Enum.PointEvent.SUBSCRIBE);
            int recommendPoint=this.pointService.getPointByEvent(Enum.PointEvent.RECOMMEND);

            sb.append("这是你的专属二维码。欢迎把这个二维码分享给朋友，朋友扫码关注后，你可以获得")
                    .append(recommendPoint).append("个积分。")
                    .append("积分可用于订阅菜单栏左下角的专栏。").append("\n").append("\n")
                    .append("你的初始积分为").append(subscribePoint).append("分。").append("\n")
                    .append("即刻订阅专栏，开始你的烘焙之旅吧。");
            return new TextBuilder().build(sb.toString(),wxMessage,weixinService);
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
