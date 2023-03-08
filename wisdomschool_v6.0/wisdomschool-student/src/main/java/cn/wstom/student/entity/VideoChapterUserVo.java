package cn.wstom.student.entity;

import lombok.Data;

@Data
public class VideoChapterUserVo extends BaseEntity {
    private VideoChapter videoChapter;

    private VideoChapterUser videoChapterUser;

    @Override
    public String toString() {
        return videoChapter.toString() + videoChapterUser.toString();
    }
}
