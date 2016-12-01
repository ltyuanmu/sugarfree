package com.sugarfree.service.impl;

import com.google.common.collect.Lists;
import com.sugarfree.dao.mapper.TArticleMapper;
import com.sugarfree.dao.model.TArticle;
import com.sugarfree.dao.model.TSubscriber;
import com.sugarfree.service.ArticleService;
import com.sugarfree.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/16
 */
@Service
@Transactional
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
    public List<TArticle> getArticleList(int userId, int menuId) {
        TSubscriber tSubscriber = subscriberService.getSubscriberByUserId(userId, menuId);
        Example example = new Example(TArticle.class);
        example.createCriteria()
                .andEqualTo("status","1")
                .andEqualTo("type","1")
                .andEqualTo("fkMenuId",menuId);
               // .andLessThanOrEqualTo("classTime",tSubscriber.getLastClassTime());
        example.orderBy("classTime").asc();
        return this.tArticleMapper.selectByExample(example);
    }

    @Override
    public TArticle getArticleById(Integer id) {
        return this.tArticleMapper.selectByPrimaryKey(id);
    }

    @Override
    public TArticle getArticleByEnumIdClassTime(Integer enumId, Integer classTime) {
        TArticle article = new TArticle();
        article.setStatus("1");
        article.setDeleteState("0");
        article.setClassTime(classTime);
        article.setFkMenuId(enumId);
        List<TArticle> list = Optional.ofNullable(this.tArticleMapper.select(article)).orElse(Lists.newArrayList());
        return list.stream().findFirst().orElse(null);
    }
}
