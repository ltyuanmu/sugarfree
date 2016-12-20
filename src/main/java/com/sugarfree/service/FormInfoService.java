package com.sugarfree.service;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/12/21
 */
public interface FormInfoService {
    /**
     * 保存表单信息
     * @param content
     */
    void saveFormInfo(String content);

    /**
     * 是否提交过表单
     * @param wxUserId
     * @return true 已提交表单信息 false 没有提交表单信息
     */
    boolean isSubmitFormByWxUserId(Integer wxUserId);
}
