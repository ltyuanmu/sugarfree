package com.sugarfree.controller;

import com.sugarfree.dao.model.TArticle;
import com.sugarfree.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: 跳转的页面controller
 * @Description: 主要获得动态页面 得到连接
 * @author: LT
 * @date: 2016/11/16
 */
@Controller
public class ViewController {

    @Autowired
    private ArticleService articleService;

    /*菜单文章的详细介绍
    *以上是静态内容
    *出现订阅按钮 提示订阅需要XX积分
    * 是否订阅
    *
    * 下面出现二维码
    * 可以进行分享 分享成功可以进行积分添加
    * */



    /*获得文章内容
    * 前面为静态内容
    * 后面出现二维吗接口
    *可以进行分享
    * */
    @RequestMapping(method = RequestMethod.GET,value = "/artucle/{id}")
    public ModelAndView getArtucle(@PathVariable String id){
        TArticle article = articleService.getArticleById(Integer.valueOf(id));
        return new ModelAndView("article","article",article);
    }


    /*
    * 获得文章列表接口
    * 通过菜单id获得文章的列表
    * 点击可以进入文章详情
    * */


    /*
    * 获得积分规则介绍
    * */
}
