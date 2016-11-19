package com.sugarfree.service;

import com.sugarfree.dao.model.TArticle;

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
    List<TArticle> getArticleList(int openId, int menuId);
    /**
     * 通过文章id获得文章详情
     * @param id
     * @return
     */
    TArticle getArticleById(Integer id);
    //点击订阅按钮 订阅文章内容

    //分享给朋友和朋友圈 该文章

    //通过用户id获得订阅文章内容的列表

}
