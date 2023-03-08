package cn.wstom.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dws
 * @date 2019/03/28
 */
@Getter
@Setter
@ToString
public class TweetEntity implements Comparable<TweetEntity> {
    /**
     * id
     */
    private String tweetId;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面图片路径
     */
    private String pictureLocation;
    /**
     * 点击量
     */
    private Long hits;
    /**
     * 内容
     */
    private String content;
    /**
     * 时间
     */
    private String buildDate;
    /**
     * 作者
     */
    private String author;
    /**
     * 是否收藏了文章
     */
    private boolean isCollect;

    @Override
    public int compareTo(TweetEntity o) {
        return 0;
    }
}
