package com.sugarfree.service.impl;

import com.sugarfree.dao.mapper.TPointHistoryMapper;
import com.sugarfree.dao.model.TPointHistory;
import com.sugarfree.service.PointHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/11/24.
 */
@Service
public class PointHistoryServiceImpl implements PointHistoryService {
    @Autowired
    private TPointHistoryMapper tPointHistoryMapper;

    @Override
    public TPointHistory getHistory(TPointHistory history) {
        return tPointHistoryMapper.selectOne(history);
    }
}
