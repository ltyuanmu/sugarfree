package com.sugarfree.service.impl;

import com.sugarfree.configuration.WechatMpProperties;
import com.sugarfree.dao.mapper.TMenuMapper;
import com.sugarfree.dao.mapper.TMenuNewMapper;
import com.sugarfree.dao.model.TMenu;
import com.sugarfree.dao.model.TMenuNew;
import com.sugarfree.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.URLEditor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/16
 */
@Service
@Slf4j
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private TMenuMapper tMenuMapper;

    @Autowired
    private TMenuNewMapper tMenuNewMapper;

    @Autowired
    private WxMpService wxService;

    @Autowired
    private WechatMpProperties wechatMpProperties;

    @Override
    public void updateMenu() throws UnsupportedEncodingException, WxErrorException {
        List<TMenuNew> tMenus = tMenuNewMapper.selectAll();
        WxMenu wxMeun = this.generateWxMenu(tMenus);
        System.out.println(wxMeun.toJson());
        this.wxService.getMenuService().menuCreate(wxMeun);

    }

    @Override
    public TMenu getMenuById(int menuId) {
        TMenu menu = new TMenu();
        menu.setId(menuId);
        return tMenuMapper.selectOne(menu);
    }

    @Override
    public String getMenuLink(int menuId) throws UnsupportedEncodingException {
        TMenu menu = getMenuById(menuId);
        if(menu==null){
            throw new RuntimeException("link is not exits");
        }
        //type为一 则需要重定向 和微信封装
        if("1".equals(menu.getType())){
            String templateUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=_APPID_&redirect_uri=_MENUURL_&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
            templateUrl = templateUrl.replaceAll("_APPID_",wechatMpProperties.getAppId());
            String url = URLEncoder.encode(menu.getUrl(), "UTF-8");
            return templateUrl.replaceAll("_MENUURL_", url);
        }else{
            return menu.getUrl();
        }
    }

    /**
     * 生成微信菜单 (待完善)
     * @param tMenus
     * @return
     */
    private WxMenu generateWxMenu(List<TMenuNew> tMenus) throws UnsupportedEncodingException {
        List<TMenuNew> buttonList = new ArrayList<>();
        Map<String,List<TMenuNew>> map = new HashMap<>();
        for(TMenuNew menu:tMenus){
            if(!"0".equals(menu.getDeleteState())){
                continue;
            }
            if (menu.getId()<100){
                buttonList.add(menu);
            }else{
                String parentId = String.valueOf(menu.getId()).substring(0, 2);
                if(map.get(parentId)==null){
                    List<TMenuNew> subList = new ArrayList<>();
                    map.put(parentId,subList);
                }
                map.get(parentId).add(menu);
            }
        }
        Collections.sort(buttonList,(m1,m2) -> {
            return m1.getMenuSort()-m2.getMenuSort();
        });

        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton> buttons = new ArrayList();

        for(TMenuNew menu:buttonList){
            WxMenuButton button  = new WxMenuButton();
            button.setName(menu.getName());
            if("2".equals(menu.getType())){
                button.setType("view");
                button.setUrl(menu.getUrl());
            }
            if("1".equals(menu.getType())){
                button.setType("view");
                String templateUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=_APPID_&redirect_uri=_MENUURL_&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
                templateUrl = templateUrl.replaceAll("_APPID_",wechatMpProperties.getAppId());
                String url = URLEncoder.encode(menu.getUrl(), "UTF-8");
                url = templateUrl.replaceAll("_MENUURL_", url);
                button.setUrl(url);
            }
            List<TMenuNew> subMenus = map.get(String.valueOf(menu.getId()));
            if(!CollectionUtils.isEmpty(subMenus)){
                Collections.sort(subMenus,(m1,m2) -> {
                    return m1.getMenuSort()-m2.getMenuSort();
                });
            }
            List<WxMenuButton> subButtons = new ArrayList();
            String templateUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=_APPID_&redirect_uri=_MENUURL_&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
            templateUrl = templateUrl.replaceAll("_APPID_",wechatMpProperties.getAppId());
            if(!CollectionUtils.isEmpty(subMenus)){
                for(TMenuNew subMenu :subMenus){
                    WxMenuButton subButton  = new WxMenuButton();
                    subButton.setName(subMenu.getName());
                    String type = subMenu.getType();
                    switch (type){
                        case "1":
                            subButton.setType("view");
                            String url = URLEncoder.encode(subMenu.getUrl(), "UTF-8");
                            url = templateUrl.replaceAll("_MENUURL_", url);
                            subButton.setUrl(url);
                            break;
                        case "2":
                            subButton.setType("view");
                            subButton.setUrl(subMenu.getUrl());
                            break;
                        case "3":
                            subButton.setType("click");
                            subButton.setKey("MY_POINT");
                            break;
                        case "4":
                            subButton.setType("click");
                            subButton.setKey("MY_QR_CODE");
                            break;
                        case "5":
                            subButton.setType("click");
                            subButton.setKey("ASK_ME");
                            break;
                        default:
                            break;
                    }
                    //没有type 目前不允许 添加入子菜单
                    if(StringUtils.isNotEmpty(type)){
                        subButtons.add(subButton);
                    }
                }
            }
            button.setSubButtons(subButtons);
            buttons.add(button);
        }
        wxMenu.setButtons(buttons);
        return wxMenu;
    }
}
