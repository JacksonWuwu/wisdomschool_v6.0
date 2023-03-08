package cn.wstom.student.entity;


import cn.wstom.student.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文章
 *
 * @author dws
 */
@ToString
@Setter
@Getter
public class Article extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 分类id
     */
    private String typeId;
    /**
     * 分类所有归属id
     */
    private String categoryId;
    /**
     * 文章关键词
     */
    private String keywords;
    /**
     * 添加标签
     */
    private String tags;
    /**
     * 文章简介
     */
    private String description;

    private Integer style;

    private String color;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 顶
     */
    private Integer countDig;
    /**
     * 踩
     */
    private Integer countBury;
    /**
     * 评论次数
     */
    private Integer countComment;
    /**
     * 浏览数量
     */
    private Integer countView;
    /**
     * 推荐设置，数字越大越靠前
     */
    private Integer recommend;
    /**
     * 权重
     */
    private Double weight;
    /**
     * 审核状态
     */
    private Integer status;

    private String thumbnail;

    private String[] tagArr;

    public String[] getTagArr() {
        if (tagArr != null && tagArr.length > 0) {
            return tagArr;
        }
        return StringUtil.split(this.getTags(), ",");
    }

    public void setTagArr(String[] tagArr) {
        this.tagArr = tagArr;
    }
}
