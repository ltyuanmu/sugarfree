package com.sugarfree.service;

import com.sugarfree.dao.model.TMenu;

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
    void updateMenu() throws UnsupportedEncodingException;

    TMenu getMenuById(int menuId);

}
