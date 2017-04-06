package com.sugarfree.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/22
 */
@Component
@ConfigurationProperties(prefix = "share")
public class ShareProperties {
    /**服务器server*/
    private String serverUrl;
    /**分享文章url*/
    private String shareArticleUrl;
    /**分享菜单详请介绍url*/
    private String shareMenuAbstractUrl;
    /**模板id*/
    private String templetId;
    /**form表单的url*/
    private String formUrl;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getShareArticleUrl() {
        return shareArticleUrl;
    }

    public void setShareArticleUrl(String shareArticleUrl) {
        this.shareArticleUrl = shareArticleUrl;
    }

    public String getShareMenuAbstractUrl() {
        return shareMenuAbstractUrl;
    }

    public void setShareMenuAbstractUrl(String shareMenuAbstractUrl) {
        this.shareMenuAbstractUrl = shareMenuAbstractUrl;
    }

    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId;
    }

    /**
     * 获得分享文章url
     * @param articleId 文章id
     * @return url
     */
    public String getShareArticleUrl(int articleId,String state){
        return getShareArticleUrl(articleId,state,false);
    }

    /**
     * 获得分享文章url
     * @param articleId 文章id
     * @return url
     */
    public String getShareArticleUrl(int articleId,String state,boolean isSelf){
        String url = this.serverUrl.concat(shareArticleUrl);
        url = url.replace("{id}",String.valueOf(articleId)).replace("{state}",state);
        if(isSelf){
            url = url.replace("{isSelf}","1");
        }else{
            url = url.replace("{isSelf}","0");
        }
        return url;
    }

    /**
     * 获得分享文章详细
     * @param menuId 菜单id
     * @return url
     */
    public String getShareMenuAbstractUrl(int menuId,String state){
        String url = this.serverUrl.concat(shareMenuAbstractUrl);
        return url.replace("{id}",String.valueOf(menuId)).replace("{state}",state);
    }

    public String getFormUrl() {
        return formUrl;
    }

    public String getFormUrl(String openId) {
        return formUrl.replace("{openId}",openId);
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }
}
