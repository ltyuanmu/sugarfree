package com.sugarfree.service.impl;

import com.sugarfree.dao.mapper.TWxUserMapper;
import com.sugarfree.dao.model.TWxUser;
import com.sugarfree.service.WxUserSubscribeService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/17
 */
public class WxUserSubscribeServiceImpl implements WxUserSubscribeService{
    
    @Autowired
    private TWxUserMapper tWxUserMapper;
    
    @Override
    public void saveWxUser(WxMpUser userWxInfo) {
        TWxUser wxUser = new TWxUser();

        this.tWxUserMapper.insertSelective(wxUser);
    }
}
