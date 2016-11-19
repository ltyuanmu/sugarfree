package com.sugarfree.service.impl;

import com.sugarfree.dao.mapper.TArticleMapper;
import com.sugarfree.dao.model.TArticle;
import com.sugarfree.dao.model.TSubscriber;
import com.sugarfree.service.ArticleService;
import com.sugarfree.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/16
 */
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private TArticleMapper tArticleMapper;

    @Autowired
    private SubscriberService subscriberService;

    @Override
    public TArticle getArticleByEnumId(Integer enumId) {
        TArticle tArticle = new TArticle();
        tArticle.setFkMenuId(enumId);
        tArticle.setDeleteState("0");
        tArticle.setType("0");
        return this.tArticleMapper.selectOne(tArticle);
    }

    @Override
    public List<TArticle> getArticleList(int openId, int menuId) {
        TSubscriber tSubscriber = subscriberService.getSubscriberByOpenId(openId, menuId);
        TArticle tArticle = new TArticle();
        tArticle.setFkMenuId(menuId);
        tArticle.setDeleteState("0");
        tArticle.setType("0");
        Example example = new Example(TArticle.class);
        example.createCriteria().andLessThanOrEqualTo("classTime",tSubscriber.getLastClassTime());
        this.tArticleMapper.selectByExample(example);
        return this.tArticleMapper.select(tArticle);
    }

    @Override
    public TArticle getArticleById(Integer id) {
        return this.tArticleMapper.selectByPrimaryKey(id);
    }
}
