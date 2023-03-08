package cn.wstom.admin.entity;


import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;

@Data

public class PreviewChapter extends BaseEntity {
    /**
     * 章节
     */
    private Integer chapterid;


    /**
     * 课件
     */
    private Integer pid;


    public PreviewChapter(Integer pid, Integer chapterid) {
        this.chapterid = chapterid;
        this.pid = pid;
    }
    public PreviewChapter() {

    }
}
