package com.sugarfree.service.impl;

import com.sugarfree.constant.Enum;
import com.sugarfree.dao.mapper.TParaMapper;
import com.sugarfree.dao.mapper.TPointHistoryMapper;
import com.sugarfree.dao.mapper.TWxUserMapper;
import com.sugarfree.dao.model.TPara;
import com.sugarfree.dao.model.TPointHistory;
import com.sugarfree.dao.model.TWxUser;
import com.sugarfree.service.PointService;
import com.sugarfree.service.WxUserSubscribeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/17
 */
@Service
public class PointServiceImpl implements PointService{

    @Autowired
    private TParaMapper tParaMapper;
    @Autowired
    private TWxUserMapper tWxUserMapper;
    @Autowired
    private TPointHistoryMapper tPointHistoryMapper;

    @Autowired
    private WxUserSubscribeService wxUserSubscribeService;

    @Override
    public int getPointByOpenId(String openId) {
        Optional<TWxUser> optional = Optional.ofNullable(this.wxUserSubscribeService.getWxUserByOpenId(openId));
        return optional.map(TWxUser::getPoint).orElse(0);
    }

    @Override
    public int getPointByEvent(Enum.PointEvent pointEvent) {
        TPara tPara = new TPara();
        tPara.setParaName(pointEvent.getKey());
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
    public void updatePoint(String openId, int point,String content) {
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

}
