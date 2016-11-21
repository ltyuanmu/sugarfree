package com.sugarfree.handler;

import com.sugarfree.builder.TextBuilder;
import com.sugarfree.service.PointService;
import com.sugarfree.utils.JsonUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService weixinService,
            WxSessionManager sessionManager)    {

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
        if("积分".equals(wxMessage.getContent())){
            int point = pointService.getPointByOpenId(wxMessage.getFromUser());
            String message = "您的积分为:"+point+"!";
            return new TextBuilder().build(message,wxMessage,weixinService);
        }else if(wxMessage.getContent().startsWith("DHJF_")){
            String content = wxMessage.getContent();
            String code = content.replaceFirst("DHJF_","");
            String message = pointService.addPointForVoucher(wxMessage.getFromUser(), code);
            return new TextBuilder().build(message,wxMessage,weixinService);
        }else{
            return null;
        }
        //TODO 组装回复消息
        //String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
        //return new TextBuilder().build(content, wxMessage, weixinService);
    }

}
