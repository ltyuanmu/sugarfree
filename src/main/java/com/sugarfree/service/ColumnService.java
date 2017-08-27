package com.sugarfree.service;

import com.sugarfree.dao.model.TColumnThird;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27.
 */
public interface ColumnService {
    /**获得第三方栏目*/
    List<TColumnThird> getColumnThirdList();
}
