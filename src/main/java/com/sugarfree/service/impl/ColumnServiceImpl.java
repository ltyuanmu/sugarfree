package com.sugarfree.service.impl;

import com.sugarfree.dao.mapper.TColumnThirdMapper;
import com.sugarfree.dao.model.TColumnThird;
import com.sugarfree.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27.
 */
@Service
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    private TColumnThirdMapper tColumnThirdMapper;

    @Override
    public List<TColumnThird> getColumnThirdList() {
        TColumnThird tColumnThird = new TColumnThird();
        tColumnThird.setDeleteState("0");
        List<TColumnThird> list = this.tColumnThirdMapper.select(tColumnThird);
        list.sort((t1,t2)->t1.getSort()-t2.getSort());
        return list;
    }

}
