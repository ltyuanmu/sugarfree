package com.sugarfree.service.impl;

import com.sugarfree.dao.mapper.TSubscriberMapper;
import com.sugarfree.dao.mapper.TWxUserMapper;
import com.sugarfree.dao.model.TSubscriber;
import com.sugarfree.dao.model.TWxUser;
import com.sugarfree.service.WxUserSubscribeService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/17
 */
@Service
@Slf4j
@Transactional
public class WxUserSubscribeServiceImpl implements WxUserSubscribeService{
    
    @Autowired
    private TWxUserMapper tWxUserMapper;
    @Autowired
    private WxMpService wxService;
    @Autowired
    private TSubscriberMapper tSubscriberMapper;

    @Override
    public TWxUser saveWxUser(WxMpUser userWxInfo,WxMpQrCodeTicket qrCodeTicket) {
        //查看如果该用户已存在则不进行添加
        TWxUser tWxUser = this.getWxUserByOpenId(userWxInfo.getOpenId());
        if(tWxUser!=null){
            return tWxUser;
        }

        //保存用户信息(包括二维码信息)
        TWxUser user = new TWxUser();
        BeanUtils.copyProperties(userWxInfo,user);
        Optional<WxMpQrCodeTicket> optional = Optional.ofNullable(qrCodeTicket);
        if(optional.isPresent()){
            user.setQrTicket(qrCodeTicket.getTicket());
            user.setQrUrl(qrCodeTicket.getUrl());
        }
        user.setPoint(0);
        user.setDeleteState("0");
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        this.tWxUserMapper.insertSelective(user);
        return this.getWxUserByOpenId(userWxInfo.getOpenId());
    }

    @Override
    /**永久获得二维码方法不能用目前使用临时二维码方案*/
    public WxMpQrCodeTicket getWxUserQRImage(String openId) {
        WxMpQrcodeService qrcodeService = this.wxService.getQrcodeService();
        try {
            return qrcodeService.qrCodeCreateLastTicket(openId);
        } catch (WxErrorException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    @Override
    public WxMpQrCodeTicket getWxUserIMPQRImage(Integer wxUserId) {
        WxMpQrcodeService qrcodeService = this.wxService.getQrcodeService();
        try {
            return qrcodeService.qrCodeCreateTmpTicket(wxUserId,604800);
        } catch (WxErrorException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    @Override
    public void unSubscribeWxUser(String openId) {
        TWxUser wxUser = this.getWxUserByOpenId(openId);
        //删除订阅用户 逻辑删除
        TWxUser user = new TWxUser();
        user.setDeleteState("1");
        /*user.setOpenId(openId);
        this.tWxUserMapper.delete(user);*/
        this.updateWxUserByOpenId(openId,user);
        //删除订阅内容
        TSubscriber tSubscriber = new TSubscriber();
        tSubscriber.setFkWxUserId(wxUser.getId());
        this.tSubscriberMapper.delete(tSubscriber);
    }

    @Override
    public void updateWxUserByOpenId(String openId, TWxUser tWxUser) {
        Example example = new Example(TWxUser.class);
        example.createCriteria().andEqualTo("openId",openId).andEqualTo("deleteState","0");
        this.tWxUserMapper.updateByExampleSelective(tWxUser,example);
    }

    @Override
    public TWxUser getWxUserByOpenId(String openId) {
        if(StringUtils.isEmpty(openId)){
            return null;
        }
        TWxUser tWxUser = new TWxUser();
        tWxUser.setOpenId(openId);
        tWxUser.setDeleteState("0");
        Optional<List<TWxUser>> optional = Optional.ofNullable(this.tWxUserMapper.select(tWxUser));
        if(optional.isPresent()){
            return optional.get().stream().findFirst().orElse(null);
        }else{
            return null;
        }
    }

    @Override
    public TWxUser getWxUserByWxUserId(Integer wxUserId) {
        return this.tWxUserMapper.selectByPrimaryKey(wxUserId);
    }
}
