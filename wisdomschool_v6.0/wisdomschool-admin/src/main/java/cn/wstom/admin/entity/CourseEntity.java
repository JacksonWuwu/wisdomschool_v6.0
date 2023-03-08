package cn.wstom.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dws
 * @date 2019/03/29
 */
@Setter
@Getter
@ToString
public class CourseEntity implements Comparable<CourseEntity> {
    private String title;
    private String courseId;
    private String introduction;
    private String picUrl;


    public CourseEntity(String title, String courseId) {
        this.title = title;
        this.courseId = courseId;
    }

    public CourseEntity(String title, String courseId, String introduction, String picUrl) {
        this.title = title;
        this.courseId = courseId;
        this.introduction = introduction;
        this.picUrl = picUrl;
    }

    @Override
    public int compareTo(CourseEntity o) {
        return this.courseId.compareTo(o.getCourseId());
    }
}
