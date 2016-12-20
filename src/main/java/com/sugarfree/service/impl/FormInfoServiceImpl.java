package com.sugarfree.service.impl;

import com.google.gson.Gson;
import com.sugarfree.bean.FromBean;
import com.sugarfree.dao.mapper.TFormInfoMapper;
import com.sugarfree.dao.model.TFormInfo;
import com.sugarfree.dao.model.TWxUser;
import com.sugarfree.service.FormInfoService;
import com.sugarfree.service.WxUserSubscribeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/12/21
 */
@Service
public class FormInfoServiceImpl implements FormInfoService{
    @Autowired
    private WxUserSubscribeService wxUserSubscribeService;

    @Autowired
    private TFormInfoMapper tFormInfoMapper;


    @Override
    public void saveFormInfo(String content) {
        FromBean fromBean = new Gson().fromJson(content,FromBean.class);
        if(fromBean.getEntry()!=null&& StringUtils.isNotEmpty(fromBean.getEntry().getX_field_1())){
            String openId = fromBean.getEntry().getX_field_1();
            TWxUser wxUser = this.wxUserSubscribeService.getWxUserByOpenId(openId);
            if(wxUser==null) return;
            TFormInfo formInfo = new TFormInfo();
            formInfo.setFkWxUserId(wxUser.getId());
            formInfo.setContent(content);
            this.tFormInfoMapper.insertSelective(formInfo);
        }
    }

    @Override
    public boolean isSubmitFormByWxUserId(Integer wxUserId) {
        TFormInfo formInfo = new TFormInfo();
        formInfo.setFkWxUserId(wxUserId);
        int count = this.tFormInfoMapper.selectCount(formInfo);
        return count>0;
    }
}
