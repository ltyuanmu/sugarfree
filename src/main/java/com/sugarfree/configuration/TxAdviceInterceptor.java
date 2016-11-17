package com.sugarfree.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Collections;

/**
 *  事物管理拦截配置
 *  参考 http://qiita.com/ksby/items/c063f6ba80a004d34171
 * Created by haijiang.mo@newtouch.cn on 2016/10/21.
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class TxAdviceInterceptor {

    /**
     * 拦截的方法名
     */
    private static final String TX_METHOD_NAME = "*";

    //事物超时时间
    private static final int TX_METHOD_TIMEOUT = 3;

    @Autowired
    DataSource dataSource;

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean
    public TransactionInterceptor transactionInterceptor() {
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setName(TX_METHOD_NAME);
        transactionAttribute.setRollbackRules(
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        //暂时不设置
//        transactionAttribute.setTimeout(TX_METHOD_TIMEOUT);
        source.setTransactionAttribute(transactionAttribute);
        TransactionInterceptor txAdvice = new TransactionInterceptor(txManager(), source);
        return txAdvice;
    }
}
