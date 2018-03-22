package com.sugarfree.service;

import com.sugarfree.dao.model.TMenu;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/16
 */
public interface MenuService {
    /**
     * 更新菜单样式
     */
    void updateMenu() throws UnsupportedEncodingException, WxErrorException;

    TMenu getMenuById(int menuId);

    /**
     * 获得url连接
     * @param menuId
     * @return
     */
    String getMenuLink(int menuId) throws UnsupportedEncodingException;
}
