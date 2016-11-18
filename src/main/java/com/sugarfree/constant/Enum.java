package com.sugarfree.constant;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/17
 */
public class Enum {

    //t_article
    //类型 0:订阅详请,1:订阅文章内容
    //状态:0:未提交,1:提交

    //积分设置 point_conf
    public enum PointEvent{
        SUBSCRIBE("subscribe"),
        SHARE("share"),
        ARTICLE("article"),
        RECOMMEND("recommend");
        private String context;

        private PointEvent(String context){
            this.context = context;
        }

        public String getContext(){
            return this.context;
        }

    }
}
