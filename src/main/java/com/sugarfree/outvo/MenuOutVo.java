package com.sugarfree.outvo;

/**
 * @ClassName: MenuOutVo
 * @Description: 菜单列表出参
 * @author: LT
 * @date: 2017/2/24
 */
public class MenuOutVo {
    /**菜单id*/
    private Integer menuId;
    /**文章id*/
    private Integer articleId;
    /**菜单名*/
    private String menuName;
    /**子标题名*/
    private String subTitle;
    /**菜单图片*/
    private String menuImg;
    /**是否订阅 0未订阅 1已订阅*/
    private Integer isSubscriber;
    /**所需积分*/
    private Integer point;
    /**订阅量*/
    private Integer subscriberNum;
    /**更新期数*/
    private Integer updateNum;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }

    public Integer getIsSubscriber() {
        return isSubscriber;
    }

    public void setIsSubscriber(Integer isSubscriber) {
        this.isSubscriber = isSubscriber;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getSubscriberNum() {
        return subscriberNum;
    }

    public void setSubscriberNum(Integer subscriberNum) {
        this.subscriberNum = subscriberNum;
    }

    public Integer getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(Integer updateNum) {
        this.updateNum = updateNum;
    }
}
