package com.sugarfree.handler;

import com.sugarfree.builder.TextBuilder;
import com.sugarfree.service.PointService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MenuHandler extends AbstractHandler {
    @Autowired
    private PointService pointService;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService weixinService,
            WxSessionManager sessionManager) {

        String msg = String.format("type:%s, event:%s, key:%s",
            wxMessage.getMsgType(), wxMessage.getEvent(),
            wxMessage.getEventKey());
        if (WxConsts.EVT_VIEW.equals(wxMessage.getEvent())) {
            return null;
        }
        if (WxConsts.EVT_CLICK.equals(wxMessage.getEvent())){
            if("MY_POINT".equals(wxMessage.getEventKey())){
                int point = pointService.getPointByOpenId(wxMessage.getFromUser());
                String message = "您的积分为:"+point+"!";
                return new TextBuilder().build(message,wxMessage,weixinService);
            }
        }

        return WxMpXmlOutMessage.TEXT().content(msg)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
    }

}
