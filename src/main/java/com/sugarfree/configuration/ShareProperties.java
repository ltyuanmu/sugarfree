package com.sugarfree.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/22
 */
@ConfigurationProperties(prefix = "share")
public class ShareProperties {
    /**服务器server*/
    private String serverUrl;
    /**分享文章url*/
    private String shareArticleUrl;
    /**分享菜单详请介绍url*/
    private String shareMenuAbstractUrl;

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

    /**
     * 获得分享文章url
     * @param articleId 文章id
     * @param openId 用户openId
     * @return url
     */
    public String getShareArticleUrl(int articleId,String openId){
        String url = this.serverUrl.concat(shareArticleUrl);
        return url.replace("{id}",String.valueOf(articleId)).replace("{openId}",openId);
    }

    /**
     * 获得分享文章详细
     * @param articleId 文章id
     * @param openId 用户openId
     * @return url
     */
    public String getShareMenuAbstractUrl(int articleId,String openId){
        String url = this.serverUrl.concat(shareMenuAbstractUrl);
        return url.replace("{id}",String.valueOf(articleId)).replace("{openId}",openId);
    }
}
