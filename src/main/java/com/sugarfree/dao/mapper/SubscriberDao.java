package com.sugarfree.dao.mapper;

import com.sugarfree.dao.model.ArticlePushBean;

import java.util.List;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2017/1/25
 */
public interface SubscriberDao {

    List<ArticlePushBean> getSubscriberArticle();
}
