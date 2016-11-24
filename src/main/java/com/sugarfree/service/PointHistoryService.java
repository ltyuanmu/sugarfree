package com.sugarfree.service;

import com.sugarfree.dao.mapper.TPointHistoryMapper;
import com.sugarfree.dao.model.TPointHistory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/11/24.
 */
public interface PointHistoryService {

    /**
     * 根据条件查询积分修改历史
     * @param history
     * @return
     */
    TPointHistory getHistory(TPointHistory history);

}
