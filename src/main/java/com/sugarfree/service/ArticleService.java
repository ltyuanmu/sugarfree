package com.sugarfree.service;

import com.sugarfree.dao.model.TArticle;
import com.sugarfree.dao.model.TWxUser;

import java.util.List;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/16
 */
public interface ArticleService {
    //通过菜单id 查看文章详情(初始化的文章)
    /**
     * 通过菜单id获得文章简介详情
     * @param enumId
     * @return
     */
    TArticle getArticleByEnumId(Integer enumId);
    //通过用户id和菜单id查看该用户的订阅情况
    List<TArticle> getArticleList(int userId, int menuId);
    /**
     * 通过文章id获得文章详情
     * @param id
     * @return
     */
    TArticle getArticleById(Integer id);

    /**
     * 通过菜单id和课次获得文章信息
     */
    TArticle getArticleByEnumIdClassTime(Integer enumId,Integer classTime);

    /**
     * 更新文章统计
     * @param tWxUser 用户名
     * @param tArticle 文章id
     * @param type false:是自己打开 true:分享的文章打开
     */
    void updateArticleStat(TWxUser tWxUser,TArticle tArticle,boolean type);
}
