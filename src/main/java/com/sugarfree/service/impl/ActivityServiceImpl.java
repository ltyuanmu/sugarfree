package com.sugarfree.service.impl;

import com.sugarfree.dao.mapper.TActivityHelpMapper;
import com.sugarfree.dao.mapper.TActivityJoinMapper;
import com.sugarfree.dao.mapper.TActivityMapper;
import com.sugarfree.dao.model.TActivity;
import com.sugarfree.dao.model.TActivityHelp;
import com.sugarfree.dao.model.TActivityJoin;
import com.sugarfree.dao.model.TWxUser;
import com.sugarfree.service.ActivityService;
import com.sugarfree.service.WxUserSubscribeService;
import com.sugarfree.utils.UrlImageUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ActivityServiceImpl implements ActivityService{

    @Autowired
    private WxMpService wxService;
    @Autowired
    private TActivityMapper tActivityMapper;
    @Autowired
    private TActivityHelpMapper tActivityHelpMapper;
    @Autowired
    private TActivityJoinMapper tActivityJoinMapper;
    @Autowired
    private WxUserSubscribeService wxUserSubscribeService;
    @Autowired
    private UrlImageUtil urlImageUtil;

    @Override
    public String getActivityImg(Integer userId) throws WxErrorException {
        //获得当前活动 没有则返回null
        Date now = Calendar.getInstance().getTime();
        Example example = new Example(TActivity.class);
        example.createCriteria()
                .andLessThan("activity_end",now)
                .andGreaterThanOrEqualTo("activity_start",now)
                .andGreaterThanOrEqualTo("enable_flag",false);
        example.orderBy("id").desc();
        List<TActivity> list = this.tActivityMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        TActivity tActivity = list.get(0);
        TWxUser wxUser = this.wxUserSubscribeService.getWxUserByWxUserId(userId);
        WxMpUser wxMpUser = this.wxService.getUserService().userInfo(wxUser.getOpenId());
        TActivityJoin tActivityJoin = new TActivityJoin();
        tActivityJoin.setActivityId(tActivity.getId());
        tActivityJoin.setJoinDate(now);
        tActivityJoin.setJoinNickname(wxMpUser.getNickname());
        tActivityJoin.setJoinOpenId(wxMpUser.getOpenId());
        this.tActivityJoinMapper.insertSelective(tActivityJoin);
        Integer tActivityJoinId = tActivityJoin.getId();
        //生成活动对应二维码
        WxMpQrcodeService qrcodeService = this.wxService.getQrcodeService();
        WxMpQrCodeTicket wxUserIMPQRImage =  qrcodeService.qrCodeCreateTmpTicket(-tActivityJoinId,604800);
        File file = wxService.getQrcodeService().qrCodePicture(wxUserIMPQRImage);
        //添加低图拼接
        if(StringUtils.isNotEmpty(tActivity.getImgUrl())){
            try {
                File baseMap = this.urlImageUtil.getImageFromNetByUrl(tActivity.getImgUrl());
                file = urlImageUtil.joinImage(file, baseMap);
                log.info("file size:{}",file.length());
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
        WxMediaUploadResult uploadResult = wxService.getMaterialService().mediaUpload("image", file);
        return uploadResult.getMediaId();
    }

    @Override
    public void activityHelp(Integer activityJoinId, String helpOpenId) throws WxErrorException {
        TActivityJoin tActivityJoin = this.tActivityJoinMapper.selectByPrimaryKey(activityJoinId);
        WxMpUser wxMpUser = this.wxService.getUserService().userInfo(helpOpenId);
        TActivityHelp tActivityHelp = new TActivityHelp();
        tActivityHelp.setActivityId(tActivityJoin.getActivityId());
        tActivityHelp.setActivityJoinId(tActivityJoin.getId());
        tActivityHelp.setHelpDate(new Date());
        tActivityHelp.setHelpNickname(wxMpUser.getNickname());
        tActivityHelp.setHelpOpenId(wxMpUser.getOpenId());
        this.tActivityHelpMapper.insertSelective(tActivityHelp);
    }

    @Override
    public String getActivityOpenId(String activityJoinId) {
        TActivityJoin tActivityJoin = this.tActivityJoinMapper.selectByPrimaryKey(activityJoinId);
        return tActivityJoin.getJoinOpenId();
    }
}
