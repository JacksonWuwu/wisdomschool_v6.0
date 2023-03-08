package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 */
@Setter
@Getter
public class Images extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 类型id
     * 0.话题，1.评论，2.相册
     */
    public Integer imageType;
    /**
     * 图片的对应内容id
     */
    public String infoId;
    /**
     * 用户id
     */
    public String userId;
    /**
     * 相册id
     */
    public String picId;
    /**
     * 图片地址
     */
    public String imgUrl;
    /**
     * 图片名称
     */
    public String imgName;
    /**
     * 图片说明
     */
    public String description;
    /**
     * 图片体积大小
     */
    public String fileSize;
    /**
     * 图片宽度
     */
    public String imgWidth;
    /**
     * 图片高度
     */
    public String imgHeight;
    /**
     * 图片指纹
     */
    public String signature;
    /**
     * 添加时间
     */
    public Date createTime;
    /**
     * 删除设置
     */
    public String imgDelete;
    /**
     * 排序
     */
    public Integer sort;
    private Integer infoCount;
}
