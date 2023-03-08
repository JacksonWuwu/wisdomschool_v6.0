package cn.wstom.admin.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息事件
 *
 * @author dws
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleEvent extends BaseEvent {
    private static final long serialVersionUID = -4261382494171476390L;

    /**
     * 事件源id
     * 如，
     * 0：文章事件 -- 文章id
     * 1：课程事件 -- 课程id
     * 2：用户事件 -- 用户id
     */
    private String eventSourceId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public ArticleEvent(Object source) {
        super(source);
    }
}
