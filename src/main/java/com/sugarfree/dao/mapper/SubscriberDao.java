package com.sugarfree.dao.mapper;

import com.sugarfree.dao.model.ArticlePushBean;
import com.sugarfree.outvo.MenuOutVo;

import java.util.List;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2017/1/25
 */
public interface SubscriberDao {
    /**
     * 获得需要推送的订阅信息
     */
    List<ArticlePushBean> getSubscriberArticle();

    /**
     * 通过用户id获得菜单列表 获得订阅量,菜单内容,菜单图片
     * @param wxUserId 用户id
     * @return 返回MenuOutVo列表
     */
    List<MenuOutVo> getMenuList(Integer wxUserId);

    /**
     * 获得用户已阅读文章数
     * @param wxUserId
     * @return
     */
    Integer getArticleReadNumByUserId(Integer wxUserId);
}
