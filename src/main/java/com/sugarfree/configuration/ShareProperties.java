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
     * @param code 用户code
     * @return url
     */
    public String getShareArticleUrl(int articleId,String code,String state){
        String url = this.serverUrl.concat(shareArticleUrl);
        return url.replace("{id}",String.valueOf(articleId)).replace("{code}",code).replace("{state}",state);
    }

    /**
     * 获得分享文章详细
     * @param menuId 菜单id
     * @return url
     */
    public String getShareMenuAbstractUrl(int menuId,String code,String state){
        String url = this.serverUrl.concat(shareMenuAbstractUrl);
        return url.replace("{id}",String.valueOf(menuId)).replace("{code}",code).replace("{state}",state);
    }
}
