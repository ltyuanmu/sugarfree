package com.sugarfree.controller;

import com.sugarfree.configuration.ShareProperties;
import com.sugarfree.constant.*;
import com.sugarfree.constant.Enum;
import com.sugarfree.dao.model.*;
import com.sugarfree.outvo.MenuOutVo;
import com.sugarfree.service.*;
import com.sugarfree.utils.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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

    @Autowired
    private FormInfoService formInfoService;

    @Autowired
    private WxUserSubscribeService wxUserSubscribeService;

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
        log.info("user openid:{}",openId);
        return wxUserService.getWxUserByOpenId(openId);
    }

    private void setUserSession(TWxUser tWxUser){
        HttpServletRequest request = HttpRequestUtil.getRequest();
        HttpSession session = request.getSession();
        String openId = (String) session.getAttribute("openId");
        if(StringUtils.isEmpty(openId)){
            session.setAttribute("openId",tWxUser.getOpenId());
        }
    }

    /**
     * 获得本次请求的url
     * @return
     */
    private String getRequestUrl(){
        HttpServletRequest request = HttpRequestUtil.getRequest();
        String url  = this.shareProperties.getServerUrl();  // 请求服务器
        url+=request.getServletPath();     // 工程名
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


    /*@RequestMapping(method = RequestMethod.GET,value = "/share/article/success")
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
    }*/
    /**
     * 获取订阅专栏
     * @param menuId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/articleList/{menuId}")
    public ModelAndView getMenuAbstract(@PathVariable int menuId,String state,String isSelf) throws WxErrorException {
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
        TArticle menuAbstract = articleService.getArticleByEnumId(menuId);
        if(menuAbstract==null){
            throw new RuntimeException("文章专栏详情不存在!!");
        }
        ModelAndView modelAndView = new ModelAndView("menuAbstract");
        TSubscriber subscriber = subscriberService.getSubscriberByUserId(wxUser.getId(), menuId);
        if(null == subscriber){
            modelAndView.addObject("subscriber",1);
        }else{
            //目前去掉取消订阅按钮
            //modelAndView.addObject("subscriber",0);
            modelAndView.addObject("subscriber",2);
            //如果state为1 则是本人 则进入列表
            //改为不进入列表
            /*if("1".equals(state)){
                return getArticleList(menuId,state,"1");
            }*/
        }
        //目前如果是分享给别人 则不出现订阅按钮
        //订阅专栏需要统计 true为分享 false为自己
        log.info(isSelf+"=====================================================================");
        if(!"1".equals(state)&&!"1".equals(isSelf)){
            modelAndView.addObject("subscriber",2);
            this.articleService.updateArticleStat(wxUser,menuAbstract,true);
        }else{
            this.articleService.updateArticleStat(wxUser,menuAbstract,false);
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

        modelAndView.addObject("menuAbstract",menuAbstract);
        //获得二维码图片
        //如果没有永久二维码 则获得临时二维码
        if(StringUtils.isNotEmpty(wxUser.getQrTicket())){
            String url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUser.getQrTicket());
            wxUser.setQrUrl(url);
        }else{
            WxMpQrCodeTicket wxUserQRImage = wxUserSubscribeService.getWxUserIMPQRImage(wxUser.getId());
            String url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUserQRImage.getTicket());
            wxUser.setQrUrl(url);
        }
        modelAndView.addObject("user",wxUser);
        //获取订阅扣除积分
        TMenu menu = menuService.getMenuById(menuId);
        modelAndView.addObject("menuPoint",menu.getPoint());
        //添加分享的连接和分享的所需要的参数
        String shareUrl = this.shareProperties.getShareMenuAbstractUrl(menuId,wxUser.getOpenId());
        //签名需要的Url
        String signatureUrl = getRequestUrl();
        log.info("signatureUrl:{}",signatureUrl);
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
    public ModelAndView getArticle(@PathVariable int id,String state,String isSelf) throws WxErrorException {
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
        //如果没有永久二维码 则获得临时二维码
        if(StringUtils.isNotEmpty(wxUser.getQrTicket())){
            //获得二维码图片
            String url = null;
            try {
                url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUser.getQrTicket());
            } catch (WxErrorException e) {
                log.error(e.getMessage(),e);
            }
            wxUser.setQrUrl(url);
        }else{
            WxMpQrCodeTicket wxUserQRImage = wxUserSubscribeService.getWxUserIMPQRImage(wxUser.getId());
            String url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUserQRImage.getTicket());
            wxUser.setQrUrl(url);
        }
        modelAndView.addObject("user", wxUser);
        TArticle article = articleService.getArticleById(id);
        modelAndView.addObject("article", article);
        //订阅按钮判断
        TSubscriber subscriber = subscriberService.getSubscriberByUserId(wxUser.getId(), article.getFkMenuId());
        if(null == subscriber){
            modelAndView.addObject("subscriber",1);
        }else{
            //目前去掉取消订阅按钮
            //modelAndView.addObject("subscriber",0);
            modelAndView.addObject("subscriber",2);
        }
        //目前如果是分享给别人 则不出现订阅按钮
        //isSelf 0代表 别人 1 代表自己
        modelAndView.addObject("isSelf",1);
        if(!"1".equals(state)&&!"1".equals(isSelf)){
            modelAndView.addObject("isSelf",0);
            modelAndView.addObject("subscriber",2);
            this.articleService.updateArticleStat(wxUser,article,true);
        }else if(!"1".equals(state)&&"1".equals(isSelf)){
            setUserSession(wxUser);
            this.articleService.updateArticleStat(wxUser,article,false);
        }else{
            this.articleService.updateArticleStat(wxUser,article,false);
        }

        //获得订阅积分
        TMenu menu = menuService.getMenuById(article.getFkMenuId());
        modelAndView.addObject("point",menu.getPoint());

        //添加分享的连接和分享的所需要的参数
        String shareUrl = this.shareProperties.getShareArticleUrl(id, wxUser.getOpenId());
        //签名需要的Url
        String signatureUrl = getRequestUrl();
        log.info("signatureUrl:{}",signatureUrl);
        WxJsapiSignature signature = this.wxService.createJsapiSignature(signatureUrl);
        modelAndView.addObject("signature",signature);
        modelAndView.addObject("shareUrl",shareUrl);
        //获得文章的活动公告
        modelAndView.addObject("isEntry",0);
        TEntry entry = this.articleService.getEntry();
        if(entry!=null){
            modelAndView.addObject("isEntry",1);
            modelAndView.addObject("entry",entry);
        }
        //获得文章的音乐
        modelAndView.addObject("isMusic",0);
        modelAndView.addObject("music",new TMusic());
        TMusic music = this.articleService.getMusicByArticleId(article.getId());
        if(music!=null){
            modelAndView.addObject("isMusic",1);
            modelAndView.addObject("music",music);
        }
        return modelAndView;
    }


    /*
    * 获得文章列表接口
    * 通过菜单id获得文章的列表
    * 点击可以进入文章详情
    * */
    @RequestMapping(method = RequestMethod.GET,value = "/article/list/{menuId}")
    public ModelAndView getArticleList(@PathVariable int menuId,String state,String isSelf) throws WxErrorException {
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
        //isSelf 0代表 别人 1 代表自己
        if(!"1".equals(state)&&!"1".equals(isSelf)){
            modelAndView.addObject("subscriber",2);
        }else if(!"1".equals(state)&&"1".equals(isSelf)){
            setUserSession(wxUser);
        }
        List<TArticle> articleList = this.articleService.getArticleList(wxUser.getId(), menuId);
        List<TArticle> list = articleList.stream().map(t -> {
            TArticle article = new TArticle();
            article.setId(t.getId());
            article.setClassTime(t.getClassTime());
            article.setTitle(t.getTitle());
            article.setIcon(t.getIcon());
            return article;
        }).collect(Collectors.toList());
        ModelAndView view = new ModelAndView("articleList");
        view.addObject("menu","文章列表");
        view.addObject("list",list);
        return view;

        /*TArticle menuAbstract = articleService.getArticleByEnumId(menuId);
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
        return modelAndView;*/
    }

    /**
    * 获得积分规则介绍
    */
    @RequestMapping(method = RequestMethod.GET,value = "/point")
    public ModelAndView getPointDetail(){
        //目前是个静态界面
        return new ModelAndView("point");
    }

    /**
     * 获取订阅专栏
     * @return 返回页面
     */
    @RequestMapping(method = RequestMethod.GET,value = "/menu/list")
    public ModelAndView getMenuAbstractList() throws WxErrorException {
        //获取用户信息
        TWxUser wxUser = getWxUser();
        if(wxUser==null){
            throw new RuntimeException("调用失败!");
        }
        List<TMenu> subscriberList = this.subscriberService.getSubscriberList(wxUser);
        ModelAndView modelAndView = new ModelAndView("menuList");
        if(CollectionUtils.isEmpty(subscriberList)){
            return new ModelAndView("emptyMenus");
        }
        //获得用户头像
        WxMpUser wxMpUser = this.wxService.getUserService().userInfo(wxUser.getOpenId());
        modelAndView.addObject("icon",wxMpUser.getHeadImgUrl());
        modelAndView.addObject("name",wxMpUser.getNickname());
        modelAndView.addObject("subscriberNum",subscriberList.size());
        //获得用户已读篇数
        Integer readNum = this.articleService.getArticleReadNumByUserId(wxUser.getId());
        modelAndView.addObject("readNum",readNum);
        //添加积分
        int point = this.pointService.getPointByOpenId(wxUser.getOpenId());
        modelAndView.addObject("point",point);
        //TODO

        modelAndView.addObject("menus",subscriberList);
        return modelAndView;
    }

    /**
     * 获得点我提问
     */
    @RequestMapping(method = RequestMethod.GET,value = "/askme")
    public ModelAndView getAskMePicture(){
        //目前是个静态界面
        return new ModelAndView("askme");
    }


    /**
     * 获取form表单的
     * @param menuId 菜单id
     * @return 返回页面
     */
    @RequestMapping(method = RequestMethod.GET,value = "/menu/form/{menuId}")
    public ModelAndView getMenuForm(@PathVariable int menuId,String state,String isSelf) throws WxErrorException {
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
        TArticle menuAbstract = articleService.getArticleByEnumId(menuId);
        if(menuAbstract==null){
            throw new RuntimeException("文章专栏详情不存在!!");
        }
        ModelAndView modelAndView = new ModelAndView("menuForm");
        modelAndView.addObject("formUrl",this.shareProperties.getFormUrl(wxUser.getOpenId()));
        TSubscriber subscriber = subscriberService.getSubscriberByUserId(wxUser.getId(), menuId);
        if(null == subscriber){
            modelAndView.addObject("subscriber",1);
        }else{
            //已订阅成功
            //判断是否提交表单如果没有显示为0如果有显示为3
            boolean isSubmit = this.formInfoService.isSubmitFormByWxUserId(wxUser.getId());
            if(isSubmit){
                modelAndView.addObject("subscriber",3);
            }else{
                modelAndView.addObject("subscriber",0);
            }
            //modelAndView.addObject("subscriber",2);
        }
        //目前如果是分享给别人 则不出现订阅按钮
        if(!"1".equals(state)&&!"1".equals(isSelf)){
            modelAndView.addObject("subscriber",2);
        }
        modelAndView.addObject("menuAbstract",menuAbstract);
        //获得二维码图片
        //如果没有永久二维码 则获得临时二维码
        if(StringUtils.isNotEmpty(wxUser.getQrTicket())){
            String url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUser.getQrTicket());
            wxUser.setQrUrl(url);
        }else{
            WxMpQrCodeTicket wxUserQRImage = wxUserSubscribeService.getWxUserIMPQRImage(wxUser.getId());
            String url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUserQRImage.getTicket());
            wxUser.setQrUrl(url);
        }
        modelAndView.addObject("user",wxUser);
        //获取订阅扣除积分
        TMenu menu = menuService.getMenuById(menuId);
        modelAndView.addObject("menuPoint",menu.getPoint());
        //添加分享的连接和分享的所需要的参数
        String shareUrl = this.shareProperties.getShareMenuAbstractUrl(menuId,wxUser.getOpenId());
        //签名需要的Url
        String signatureUrl = getRequestUrl();
        log.info("signatureUrl:{}",signatureUrl);
        WxJsapiSignature signature = this.wxService.createJsapiSignature(signatureUrl);
        modelAndView.addObject("signature",signature);
        modelAndView.addObject("shareUrl",shareUrl);
        return modelAndView;
    }


    /**
     * 获取订阅专栏
     * @param menuId 菜单id
     * @return 返回页面
     */
    @RequestMapping(method = RequestMethod.GET,value = "/aboutme/{menuId}")
    public ModelAndView getAboutMe(@PathVariable int menuId) throws WxErrorException {
        TArticle article = articleService.getArticleByEnumId(menuId);
        if(article==null){
            throw new RuntimeException("文章专栏详情不存在!!");
        }
        ModelAndView modelAndView = new ModelAndView("aboutme");
        modelAndView.addObject("article",article);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/columns")
    public ModelAndView getColumns(String state) throws WxErrorException {
        TWxUser wxUser;
        if(!"1".equals(state)&&StringUtils.isNotEmpty(state)){
            wxUser = this.wxUserService.getWxUserByOpenId(state);
            setUserSession(wxUser);
        }else{
            wxUser = getWxUser();
        }
        List<MenuOutVo> menuList = this.subscriberService.getMenuList(wxUser.getId());
        MenuOutVo menuOutVo = menuList.get(0);
        ModelAndView modelAndView = new ModelAndView("menus");
        //目前去掉跟着安琪烘焙在MenuList 2017年7月13日16:09:20
        menuList.remove(0);
        //目前去掉拍出好滋味在MenuList 2017年7月26日16:33:27
        modelAndView.addObject("menuList",menuList);
        modelAndView.addObject("hongBei",menuOutVo);
        List<TCarousel> carouselList = this.subscriberService.getCarouselList();
        if(!CollectionUtils.isEmpty(carouselList)){
            modelAndView.addObject("carouselList",carouselList);
        }
        //获得面包师的订阅个数 和更新期数
        Integer miambaoNum = this.subscriberService.getSubNum("miambao_num");
        Integer mianbaoTime =this.subscriberService.getSubNum("miambao_time");
        modelAndView.addObject("miambaoNum",miambaoNum);
        modelAndView.addObject("mianbaoTime",mianbaoTime);
        //拍照订阅格式和更新期数
        Integer paizhaoNum = this.subscriberService.getSubNum("paizhao_num");
        Integer paizhaoTime =this.subscriberService.getSubNum("paizhao_time");
        modelAndView.addObject("paizhaoNum",paizhaoNum);
        modelAndView.addObject("paizhaoTime",paizhaoTime);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/columns/{menuId}")
    public ModelAndView getCloumn(@PathVariable int menuId,String state) throws WxErrorException {
        TWxUser wxUser;
        if(!"1".equals(state)&&StringUtils.isNotEmpty(state)){
            wxUser = this.wxUserService.getWxUserByOpenId(state);
            setUserSession(wxUser);
        }else{
            wxUser = getWxUser();
        }
        TArticle menuAbstract = articleService.getArticleByEnumId(menuId);
        if(menuAbstract==null){
            throw new RuntimeException("文章专栏详情不存在!!");
        }
        ModelAndView modelAndView = new ModelAndView("column");
        //目前如果是分享给别人 则不出现订阅按钮
        //订阅专栏需要统计 true为分享 false为自己
        this.articleService.updateArticleStat(wxUser,menuAbstract,true);
        modelAndView.addObject("menuAbstract",menuAbstract);
        //获得二维码图片
        //如果没有永久二维码 则获得临时二维码
        if(StringUtils.isNotEmpty(wxUser.getQrTicket())){
            String url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUser.getQrTicket());
            wxUser.setQrUrl(url);
        }else{
            WxMpQrCodeTicket wxUserQRImage = wxUserSubscribeService.getWxUserIMPQRImage(wxUser.getId());
            String url = this.wxService.getQrcodeService().qrCodePictureUrl(wxUserQRImage.getTicket());
            wxUser.setQrUrl(url);
        }
        modelAndView.addObject("user",wxUser);
        //获取订阅扣除积分
        TMenu menu = menuService.getMenuById(menuId);
        modelAndView.addObject("menuPoint",menu.getPoint());
        return modelAndView;
    }


    /**
     * 新版获得文章列表
     * @param menuId 菜单id
     * @param state 状态
     * @param isSelf 是否是自己
     * @return 反参
     * @throws WxErrorException 异常
     */
    @RequestMapping(method = RequestMethod.GET,value = "/article/list/new/{menuId}")
    public ModelAndView getNewArticleList(@PathVariable int menuId,String state,String isSelf) throws WxErrorException {
        ModelAndView modelAndView = this.getArticleList(menuId, state, isSelf);
        modelAndView.setViewName("newArticleList");
        return modelAndView;
    }
}
