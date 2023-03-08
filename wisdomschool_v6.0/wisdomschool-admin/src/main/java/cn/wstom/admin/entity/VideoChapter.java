package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import lombok.Data;

/**
* 章节视频表 tb_video_chapter
*
* @author dws
* @date 20200204
*/
@Data
public class VideoChapter extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 创建Id */
    private Integer createId;
    /** 更新人Id */
    private Integer updateId;
    /** 学校No */
    private Integer courseTeacherId;
    /**  */
    private Integer chapterId;
    /**  */
    private Double time;
    /** 是否可快进 */
    private Integer state;

    private Integer resourceChapterId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("createId", getCreateId())
                .append("updateId", getUpdateId())
                .append("courseClbumId", getCourseTeacherId())
                .append("chapterId", getChapterId())
                .append("time", getTime())
                .append("state", getState())
                .append("resourceChapterId", getResourceChapterId())
                .toString();
    }
}
