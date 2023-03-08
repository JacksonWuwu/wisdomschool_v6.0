package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import lombok.Data;

/**
* 用户观看表 tb_video_chapter_user
*
* @author dws
* @date 20200204
*/
@Data
public class VideoChapterUser extends BaseEntity {
private static final long serialVersionUID = 1L;

    /** 创建Id */
    private Integer createId;
    /** 更新人Id */
    private Integer updateId;
    /** 学校No */
    private Integer userId;
    /**  */
    private Integer videoChapterId;
    /**  */
    private Long progress;
    /**  */
    private Long lastTime;

    private Integer state;
    /*视频名称*/
    private String videoname;

@Override
public String toString() {
return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        .append("createId", getCreateId())
        .append("updateId", getUpdateId())
        .append("userId", getUserId())
        .append("videoChapterId", getVideoChapterId())
        .append("progress", getProgress())
        .append("lastTime", getLastTime())
        .append("videoname", getVideoname())
.toString();
}
}
