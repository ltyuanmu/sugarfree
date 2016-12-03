package com.sugarfree.controller;

import com.sugarfree.configuration.ShareProperties;
import com.sugarfree.constant.*;
import com.sugarfree.constant.Enum;
import com.sugarfree.dao.model.*;
import com.sugarfree.service.*;
import com.sugarfree.utils.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private PointHistoryService pointHistoryService;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private ShareProperties shareProperties;


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
     * @return
     */
    private TWxUser getWxUser(){
        HttpServletRequest request = HttpRequestUtil.getRequest();
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
     * 获得本次请求的url
     * @return
     */
    private String getRequestUrl(){
        HttpServletRequest request = HttpRequestUtil.getRequest();
        String url  = this.shareProperties.getServerUrl();  // 请求服务器
        url+=request.getRequestURI();     // 工程名
        if(request.getQueryString()!=null) //判断请求参数是否为空
            url+="?"+request.getQueryString();   // 参数
        log.info("getRequestUrl :{}",url);
        return url;
    }


    /**
     * 订阅
     * @param menuId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/subscribe/{menuId}",produces = "text/plain;charset=utf-8")
    public ResponseEntity subscribe(@PathVariable int menuId) throws WxErrorException {
        //获取用户信息
        TWxUser wxUser = getWxUser();
        //获取订阅扣除积分
        TMenu menu = menuService.getMenuById(menuId);
        String state = this.subscriberService.subscriberArticle(wxUser, menu);
        if("1".equals(state)){
            return ResponseEntity.ok().body("ok");
        }else if("2".equals(state)){
            return ResponseEntity.ok().body("ok");
        }/*else if("0".equals(state)){
            return ResponseEntity.ok().body("err");
        }*/else{
            return ResponseEntity.ok().body("err");
        }
    }

    /**
     * 取消订阅
     * @param menuId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/unSubscribe/{menuId}",produces = "text/plain;charset=utf-8")
    public ResponseEntity unSubscribe(@PathVariable int menuId) throws WxErrorException {
        //获取用户信息
        TWxUser wxUser = getWxUser();
        //获取订阅lanmu
        TMenu menu = menuService.getMenuById(menuId);
        //取消订阅
        String state = this.subscriberService.unSubscriberArticle(wxUser, menu);
        if("1".equals(state)){
            return ResponseEntity.ok().body("ok");
        }else{
            return ResponseEntity.ok().body("err");
        }
    }


    @RequestMapping(method = RequestMethod.GET,value = "/share/article/success")
    public void share()
    {
        //获取用户信息
        TWxUser wxUser = getWxUser();
        TPointHistory query = new TPointHistory();
        query.setFkWxUserId(wxUser.getId());
        query.setSource(Enum.PointEvent.SHARE.getContext());
        TPointHistory history = pointHistoryService.getHistory(query);
        if(null == history) {
            //添加积分变更历史记录，扣除积分
            pointService.updatePoint(wxUser.getOpenId(), Enum.PointEvent.SHARE, "");
        }
    }
    /**
     * 获取订阅list
     * @param menuId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/articleList/{menuId}")
    public ModelAndView getArticleList(@PathVariable int menuId,String state) throws WxErrorException {
        //获取用户信息
        TWxUser wxUser;
        if(!"1".equals(state)&&StringUtils.isNotEmpty(state)){
            wxUser = this.wxUserService.getWxUserByOpenId(state);
        }else{
            wxUser = getWxUser();
        }
        if(wxUser==null){
            throw new RuntimeException("调用失败!");
        }
        ModelAndView modelAndView = new ModelAndView("menuAbstract");
        TSubscriber subscriber = subscriberService.getSubscriberByUserId(wxUser.getId(), menuId);
        if(null == subscriber){
            modelAndView.addObject("subscriber",1);
        }else{
            modelAndView.addObject("subscriber",0);
        }
        //目前如果是分享给别人 则不出现订阅按钮
        if(!"1".equals(state)){
            modelAndView.addObject("subscriber",2);
        }
        /*List<TArticle> articleList = this.articleService.getArticleList(wxUser.getId(), menuId);
        List<TArticle> list = articleList.stream().map(t -> {
            TArticle article = new TArticle();
            article.setId(t.getId());
            article.setClassTime(t.getClassTime());
            article.setTitle(t.getTitle());
            return article;
        }).collect(Collectors.toList());
        ModelAndView view = new ModelAndView("articleList");
        view.addObject("menu","文章列表");
        view.addObject("list",list);
        return view;*/

        TArticle menuAbstract = articleService.getArticleByEnumId(menuId);
        if(menuAbstract==null){
            throw new RuntimeException("文章专栏详情不存在!!");
        }
        modelAndView.addObject("menuAbstract",menuAbstract);
        //获得二维码图片
        String url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUser.getQrTicket());
        wxUser.setQrUrl(url);
        modelAndView.addObject("user",wxUser);
        //获取订阅扣除积分
        TMenu menu = menuService.getMenuById(menuId);
        modelAndView.addObject("menuPoint",menu.getPoint());
        //添加分享的连接和分享的所需要的参数
        String shareUrl = this.shareProperties.getShareMenuAbstractUrl(menuId,wxUser.getOpenId());
        //签名需要的Url
        String signatureUrl = getRequestUrl();
        WxJsapiSignature signature = this.wxService.createJsapiSignature(signatureUrl);
        modelAndView.addObject("signature",signature);
        modelAndView.addObject("shareUrl",shareUrl);
        return modelAndView;
    }


    /*获得文章内容
    * 前面为静态内容
    * 后面出现二维吗接口
    *可以进行分享
    * */
    @RequestMapping(method = RequestMethod.GET,value = "/article/{id}")
    public ModelAndView getArticle(@PathVariable int id,String state) throws WxErrorException {
        //获取用户信息
        TWxUser wxUser;
        if(!"1".equals(state)&&StringUtils.isNotEmpty(state)){
            wxUser = this.wxUserService.getWxUserByOpenId(state);
        }else{
            wxUser = getWxUser();
        }
        if(wxUser==null){
            throw new RuntimeException("调用失败!");
        }
        ModelAndView modelAndView = new ModelAndView("article");
        //获得二维码图片
        String url = null;
        try {
            url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUser.getQrTicket());
        } catch (WxErrorException e) {
            log.error(e.getMessage(),e);
        }
        wxUser.setQrUrl(url);
        modelAndView.addObject("user", wxUser);
        TArticle article = articleService.getArticleById(id);
        modelAndView.addObject("article", article);
        //获得订阅积分
        TMenu menu = menuService.getMenuById(article.getFkMenuId());
        modelAndView.addObject("point",menu.getPoint());

        //添加分享的连接和分享的所需要的参数
        String shareUrl = this.shareProperties.getShareArticleUrl(id, wxUser.getOpenId());

        //签名需要的Url
        String signatureUrl = getRequestUrl();
        WxJsapiSignature signature = this.wxService.createJsapiSignature(signatureUrl);
        modelAndView.addObject("signature",signature);
        modelAndView.addObject("shareUrl",shareUrl);
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

    @RequestMapping(method = RequestMethod.GET,value = "/point")
    public ModelAndView getPointDetail(){
        //目前是个静态界面
        return new ModelAndView("point");
    }
}
