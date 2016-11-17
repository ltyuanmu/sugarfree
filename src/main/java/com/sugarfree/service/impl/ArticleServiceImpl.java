package com.sugarfree.service.impl;

import com.sugarfree.dao.mapper.TArticleMapper;
import com.sugarfree.dao.model.TArticle;
import com.sugarfree.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public TArticle getArticleByEnumId(Integer enumId) {
        TArticle tArticle = new TArticle();
        tArticle.setFkMenuId(enumId);
        tArticle.setDeleteState("0");
        tArticle.setType("0");
        return this.tArticleMapper.selectOne(tArticle);
    }

    @Override
    public TArticle getArticleById(Integer id) {
        return this.tArticleMapper.selectByPrimaryKey(id);
    }
}
