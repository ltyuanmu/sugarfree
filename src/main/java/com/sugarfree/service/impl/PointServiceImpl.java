package com.sugarfree.service.impl;

import com.sugarfree.configuration.ShareProperties;
import com.sugarfree.constant.Enum;
import com.sugarfree.dao.mapper.*;
import com.sugarfree.dao.model.*;
import com.sugarfree.service.MenuService;
import com.sugarfree.service.PointService;
import com.sugarfree.service.SubscriberService;
import com.sugarfree.service.WxUserSubscribeService;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/17
 */
@Service
@Transactional
public class PointServiceImpl implements PointService{

    @Autowired
    private TParaMapper tParaMapper;
    @Autowired
    private TPointHistoryMapper tPointHistoryMapper;
    @Autowired
    private TCsolMapper tCsolMapper;
    @Autowired
    private TWxUserMapper tWxUserMapper;
    @Autowired
    private WxUserSubscribeService wxUserSubscribeService;
    @Autowired
    private TGatewayMapper tGatewayMapper;
    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ShareProperties shareProperties;

    @Override
    public int getPointByOpenId(String openId) {
        Optional<TWxUser> optional = Optional.ofNullable(this.wxUserSubscribeService.getWxUserByOpenId(openId));
        return optional.map(TWxUser::getPoint).orElse(0);
    }

    @Override
    public int getPointByEvent(Enum.PointEvent pointEvent) {
        TPara tPara = new TPara();
        tPara.setParaName(pointEvent.getKey());
        tPara.setDeleteState("0");
        TPara tPara1 = tParaMapper.selectOne(tPara);
        Optional<TPara> optional = Optional.ofNullable(tPara1);
        return optional.map(t -> { return Integer.parseInt(t.getParaValue()); }).orElse(0);
    }

    @Override
    public void updatePoint(String openId, Enum.PointEvent pointEvent,String recommendOrInviteOpenId) {
        //获得事件的积分
        int point = this.getPointByOpenId(openId);
        int addPoint = this.getPointByEvent(pointEvent);
        //更新积分
        TWxUser tWxUser = new TWxUser();
        tWxUser.setPoint(point+addPoint);
        //如果为订阅 则更新邀请你的人
        if(Enum.PointEvent.SUBSCRIBE.equals(pointEvent)&&StringUtils.isNotEmpty(recommendOrInviteOpenId)){
            tWxUser.setRecommendOpenId(recommendOrInviteOpenId);
        }
        this.wxUserSubscribeService.updateWxUserByOpenId(openId, tWxUser);
        //添加积分历史记录
        TWxUser wxUser = this.wxUserSubscribeService.getWxUserByOpenId(openId);
        TPointHistory tPointHistory = new TPointHistory();
        tPointHistory.setFkWxUserId(wxUser.getId());
        tPointHistory.setRemarkTime(new Date());
        tPointHistory.setScore(String.valueOf(addPoint));
        tPointHistory.setSource(pointEvent.getContext());
        //如果为推荐 则插入被推荐的人
        if(Enum.PointEvent.RECOMMEND.equals(pointEvent)&&StringUtils.isNotEmpty(recommendOrInviteOpenId)){
            TWxUser invitee = this.wxUserSubscribeService.getWxUserByOpenId(recommendOrInviteOpenId);
            tPointHistory.setRelateWxUserId(invitee.getId());
        }
        tPointHistoryMapper.insertSelective(tPointHistory);
    }

    @Override
    public void deletePoint(String openId, int point,String content) {
        //获得事件的积分
        int myPoint = this.getPointByOpenId(openId);
        //更新积分
        TWxUser tWxUser = new TWxUser();
        tWxUser.setPoint(myPoint-point);
        this.wxUserSubscribeService.updateWxUserByOpenId(openId, tWxUser);
        //添加积分历史记录
        TWxUser wxUser = this.wxUserSubscribeService.getWxUserByOpenId(openId);
        TPointHistory tPointHistory = new TPointHistory();
        tPointHistory.setFkWxUserId(wxUser.getId());
        tPointHistory.setRemarkTime(new Date());
        tPointHistory.setScore(String.valueOf(-point));
        tPointHistory.setSource(content);
        tPointHistoryMapper.insertSelective(tPointHistory);
    }

    @Override
    public String addPointForVoucher(String openId, String voucherCode) {
        //查看是否有积分券
        TCsol tCsol = new TCsol();
        tCsol.setCode(voucherCode);
        TCsol csol = tCsolMapper.selectOne(tCsol);
        if(csol ==null){
            return "积分码不正确，无法兑换积分!";
        }
        //积分为2时则为无限兑换码类型 可以无限兑换
        if("2".equals(csol.getStatus())){
            return this.addPointForOneVoucher(openId,csol);
        }
        //查看积分劵是否已经被使用
        if("1".equals(csol.getStatus())){
            return "积分兑换失败，该积分码已经使用过!";
        }
        //获得用户信息
        TWxUser wxUser = this.wxUserSubscribeService.getWxUserByOpenId(openId);
        //更新积分
        TWxUser modifyWxUser = new TWxUser();
        Integer sumPoint=wxUser.getPoint()+csol.getScore();
        modifyWxUser.setPoint(sumPoint);
        this.wxUserSubscribeService.updateWxUserByOpenId(openId, modifyWxUser);
        //修改积分兑换表
        TCsol modifyCsol = new TCsol();
        modifyCsol.setId(csol.getId());
        modifyCsol.setStatus("1");
        modifyCsol.setFkWxUserId(wxUser.getId());
        modifyCsol.setDeleteState("0");
        modifyCsol.setUpdateTime(new Date());
        modifyCsol.setOpUser(wxUser.getNickname());
        this.tCsolMapper.updateByPrimaryKeySelective(modifyCsol);
        //添加积分历史记录
        TPointHistory tPointHistory = new TPointHistory();
        tPointHistory.setFkWxUserId(wxUser.getId());
        tPointHistory.setRemarkTime(new Date());
        tPointHistory.setScore(String.valueOf(csol.getScore()));
        //获得渠道
        TGateway tGateway = this.tGatewayMapper.selectByPrimaryKey(csol.getFkGatewayId());
        tPointHistory.setSource("兑换的是".concat(tGateway.getName()).concat("的兑换劵"));
        tPointHistoryMapper.insertSelective(tPointHistory);
        return "积分兑换成功，当前积分为：" + sumPoint + "分!";
    }

    @Override
    public boolean isSubscriberAddPoint(String openId, String recommendId) {
        //查看用户的id
        TWxUser wxUser = this.wxUserSubscribeService.getWxUserByOpenId(openId);
        if(wxUser==null){
            return false;
        }
        TWxUser tWxUser = new TWxUser();
        tWxUser.setOpenId(recommendId);
        List<TWxUser> list = this.tWxUserMapper.select(tWxUser);
        //查看以前是否有该被邀请的用户id
        if(CollectionUtils.isEmpty(list)){
            return true;
        }
        //获得被邀请的用户id
        List<Integer> userIds = list.stream().map(TWxUser::getId).collect(Collectors.toList());
        //如果有的话 查看该用户是否被此用户邀请过
        Example example = new Example(TPointHistory.class);
        example.createCriteria()
                .andEqualTo("fkWxUserId",wxUser.getId())
                .andIn("relateWxUserId",userIds);
        List<TPointHistory> historys = this.tPointHistoryMapper.selectByExample(example);
        //如果邀请 则不在加积分
        return CollectionUtils.isEmpty(historys);
    }

    @Override
    public String addPointForOneVoucher(String openId, TCsol csol) {
        //获得用户信息
        TWxUser wxUser = this.wxUserSubscribeService.getWxUserByOpenId(openId);
        //查看该用户是否兑换过
        TPointHistory history = new TPointHistory();
        history.setFkWxUserId(wxUser.getId());
        history.setStatus(csol.getCode());
        int i = this.tPointHistoryMapper.selectCount(history);
        if(i>0){
            return "您已兑换过! ";
        }
        //更新积分
        TWxUser modifyWxUser = new TWxUser();
        Integer sumPoint=wxUser.getPoint()+csol.getScore();
        modifyWxUser.setPoint(sumPoint);
        this.wxUserSubscribeService.updateWxUserByOpenId(openId, modifyWxUser);
        //添加积分历史记录
        TPointHistory tPointHistory = new TPointHistory();
        tPointHistory.setFkWxUserId(wxUser.getId());
        tPointHistory.setRemarkTime(new Date());
        tPointHistory.setScore(String.valueOf(csol.getScore()));
        tPointHistory.setStatus(csol.getCode());
        //获得渠道
        tPointHistory.setSource("兑换的是".concat(csol.getCode()));
        tPointHistoryMapper.insertSelective(tPointHistory);
        return "积分兑换成功，当前积分为：" + sumPoint + "分!";
    }

    @Override
    public String getPointMag(String openId) throws WxErrorException {
        //获得该用户的积分,获得该用户未订阅的列表
        int point = this.getPointByOpenId(openId);
        StringBuilder sb = new StringBuilder("您的积分为:");
        sb.append(point);
        //获得该用户的订阅列表
        TWxUser wxUser = this.wxUserSubscribeService.getWxUserByOpenId(openId);
        List<TMenu> list = subscriberService.getSubscriberList(wxUser);
        StringBuilder menuText = new StringBuilder();
        if(0==list.stream().filter(tMenu -> 1001==tMenu.getId()).count()){
            TMenu tMenu = this.menuService.getMenuById(1001);
            if(point>=tMenu.getPoint()){
                menuText.append("\n")
                    .append("/:sun跟着安琪学烘焙：教你从0开始系统入门烘焙").append("\n")
                    .append(shareProperties.getServerUrl()).append("/link/1001");
            }
        }
        if(0==list.stream().filter(tMenu -> 1002==tMenu.getId()).count()){
            TMenu tMenu = this.menuService.getMenuById(1002);
            if(point>=tMenu.getPoint()){
                menuText.append("\n")
                        .append("/:sun拍出好滋味：教你用手机拍出烘焙美食大片").append("\n")
                        .append(shareProperties.getServerUrl()).append("/link/1002");
            }
        }
        if(0==list.stream().filter(tMenu -> 1004==tMenu.getId()).count()){
            TMenu tMenu = this.menuService.getMenuById(1004);
            if(point>=tMenu.getPoint()){
                menuText.append("\n")
                        .append("/:sun烘焙地图：跟着黄油一起逛世界").append("\n")
                        .append(shareProperties.getServerUrl()).append("/link/1004");
            }
        }
        if(StringUtils.isNotEmpty(menuText)){
            sb.append("\n").append("\n")
                    .append("你目前可订阅").append("\n")
                    .append(menuText);
        }
        return sb.toString();
    }

}
