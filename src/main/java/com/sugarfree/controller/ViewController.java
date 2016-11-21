package com.sugarfree.controller;

import com.sugarfree.constant.Enum;
import com.sugarfree.dao.model.TArticle;
import com.sugarfree.dao.model.TMenu;
import com.sugarfree.dao.model.TSubscriber;
import com.sugarfree.dao.model.TWxUser;
import com.sugarfree.service.*;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: 跳转的页面controller
 * @Description: 主要获得动态页面 得到连接
 * @author: LT
 * @date: 2016/11/16
 */
@Slf4j
@Controller
public class ViewController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private WxUserSubscribeService wxUserService;

    @Autowired
    private PointService pointService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private WxMpService wxService;

    @Autowired
    private SubscriberService subscriberService;


    /*菜单文章的详细介绍
    *以上是静态内容
    *出现订阅按钮 提示订阅需要XX积分
    * 是否订阅
    *
    * 下面出现二维码
    * 可以进行分享 分享成功可以进行积分添加
    * */

    /**
     * 获取openId
     * @param request
     * @return
     */
    private TWxUser getWxUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        String openId = (String) session.getAttribute("openId");
        if(StringUtils.isEmpty(openId)){
            try {
                String code = request.getParameter("code");
                WxMpOAuth2AccessToken accessToken = wxService.oauth2getAccessToken(code);
                openId = accessToken.getOpenId();
            } catch (WxErrorException e) {
                log.error(e.getMessage(),e);
            }
            if(StringUtils.isEmpty(openId)){
                throw new RuntimeException("openId is null");
            }
            session.setAttribute("openId",openId);
        }
        return wxUserService.getWxUserByOpenId(openId);
    }

    /**
     * 订阅
     * @param menuId
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/subscribe/{menuId}")
    public ModelAndView subscribe(@PathVariable int menuId, HttpServletRequest request){
        //获取用户信息
        TWxUser wxUser = getWxUser(request);
        //获取订阅扣除积分
        TMenu menu = menuService.getMenuById(menuId);
        TSubscriber tSubscriber = subscriberService.getSubscriberByUserId(wxUser.getId(), menuId);
        if(null != tSubscriber){
            return new ModelAndView("subscriberReturn","ret","2");
        }
        if (menu.getPoint() > wxUser.getPoint())
        {
            //需要积分大于用户已有积分提示积分不够
            return new ModelAndView("subscriberReturn","ret","0");
        }else{
            //添加积分变更历史记录，扣除积分
            pointService.updatePoint(wxUser.getOpenId(),menu.getPoint(), "订阅"+menu.getName()+"扣除积分");
            //添加订阅记录
            TSubscriber subscriber = new TSubscriber();
            subscriber.setCreateTime(new Date());
            subscriber.setFkMenuId(menuId);
            subscriber.setFkWxUserId(wxUser.getId());
            subscriber.setStatus("0");
            subscriber.setLastClassTime(0);
            subscriberService.insert(subscriber);
        }
        ModelAndView modelAndView =new ModelAndView("subscriberReturn");
        modelAndView.addObject("ret","1");
        modelAndView.addObject("menuId",menuId);
        return modelAndView;
    }

    /**
     * 获取订阅list
     * @param menuId
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/articleList/{menuId}")
    public ModelAndView getArticleList(@PathVariable int menuId,HttpServletRequest request) throws WxErrorException {
        //获取用户信息
        TWxUser wxUser = getWxUser(request);
        ModelAndView modelAndView = new ModelAndView("menuAbstract");
        TSubscriber subscriber = subscriberService.getSubscriberByUserId(wxUser.getId(), menuId);
        if(null == subscriber){
            modelAndView.addObject("subscriber",1);
        }
        TArticle menuAbstract = articleService.getArticleByEnumId(menuId);
        modelAndView.addObject("menuAbstract",menuAbstract);
        //获得二维码图片
        String url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUser.getQrTicket());
        wxUser.setQrUrl(url);
        modelAndView.addObject("user",wxUser);
        //获取订阅扣除积分
        TMenu menu = menuService.getMenuById(menuId);
        modelAndView.addObject("menuPoint",menu.getPoint());
        return modelAndView;
    }


    /*获得文章内容
    * 前面为静态内容
    * 后面出现二维吗接口
    *可以进行分享
    * */
    @RequestMapping(method = RequestMethod.GET,value = "/article/{id}")
    public ModelAndView getArticle(@PathVariable int id,HttpServletRequest request){
        //获取用户信息
        TWxUser wxUser = getWxUser(request);
        ModelAndView modelAndView = new ModelAndView("article");
        //获得二维码图片
        String url = null;
        try {
            url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUser.getQrTicket());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        wxUser.setQrUrl(url);
        modelAndView.addObject("user",wxUser);
        TArticle article = articleService.getArticleById(id);
        modelAndView.addObject("article", article);
        return modelAndView;
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
