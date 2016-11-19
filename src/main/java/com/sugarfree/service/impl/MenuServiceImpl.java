package com.sugarfree.service.impl;

import com.sugarfree.configuration.WechatMpProperties;
import com.sugarfree.dao.mapper.TMenuMapper;
import com.sugarfree.dao.model.TMenu;
import com.sugarfree.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.URLEditor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/16
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private TMenuMapper tMenuMapper;

    @Autowired
    private WxMpService wxService;

    @Autowired
    private WechatMpProperties wechatMpProperties;

    @Override
    public void updateMenu() throws UnsupportedEncodingException {
        List<TMenu> tMenus = tMenuMapper.selectAll();
        WxMenu wxMeun = this.generateWxMenu(tMenus);
        System.out.println(wxMeun.toJson());
        try {
            this.wxService.getMenuService().menuCreate(wxMeun);
        } catch (WxErrorException e) {
            log.error(e.getMessage(),e);
        }
    }

    /**
     * 生成微信菜单 (待完善)
     * @param tMenus
     * @return
     */
    private WxMenu generateWxMenu(List<TMenu> tMenus) throws UnsupportedEncodingException {
        List<TMenu> buttonList = new ArrayList<>();
        Map<String,List<TMenu>> map = new HashMap<>();
        for(TMenu menu:tMenus){
            if(!"0".equals(menu.getDeleteState())){
                continue;
            }
            if (menu.getId()<100){
                buttonList.add(menu);
            }else{
                String parentId = String.valueOf(menu.getId()).substring(0, 2);
                if(map.get(parentId)==null){
                    List<TMenu> subList = new ArrayList<>();
                    map.put(parentId,subList);
                }
                map.get(parentId).add(menu);
            }
        }
        Collections.sort(buttonList,(m1,m2) -> {
            return m1.getId()-m2.getId();
        });

        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton> buttons = new ArrayList();

        for(TMenu menu:buttonList){
            WxMenuButton button  = new WxMenuButton();
            button.setName(menu.getName());
            List<TMenu> subMenus = map.get(String.valueOf(menu.getId()));
            Collections.sort(subMenus,(m1,m2) -> {
                return m1.getId()-m2.getId();
            });
            List<WxMenuButton> subButtons = new ArrayList();
            String templateUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=_APPID_&redirect_uri=_MENUURL_&response_type=code&scope=snsapi_base#wechat_redirect";
            templateUrl = templateUrl.replaceAll("_APPID_",wechatMpProperties.getAppId());
            for(TMenu subMenu :subMenus){
                WxMenuButton subButton  = new WxMenuButton();
                subButton.setName(subMenu.getName());
                String type = subMenu.getType();
                switch (type){
                    case "1":
                    case "2":
                        subButton.setType("view");
                        String url = URLEncoder.encode(subMenu.getUrl(), "UTF-8");
                        url = templateUrl.replaceAll("_MENUURL_", url);
                        subButton.setUrl(url);
                        break;
                    case "3":
                        subButton.setType("click");
                        subButton.setKey("MY_POINT");
                        break;
                    default:
                        break;
                }
                subButtons.add(subButton);
            }
            button.setSubButtons(subButtons);
            buttons.add(button);
        }
        wxMenu.setButtons(buttons);
        return wxMenu;
    }
}
