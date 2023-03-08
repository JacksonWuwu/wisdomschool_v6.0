package cn.wstom.admin.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dws
 * @date 2019/03/27
 */
@Getter
@Setter
@ToString
public class NotifyEvent extends BaseEvent {

    private static final long serialVersionUID = -5919282549212701855L;
    /**
     * 事件类型
     * 0：文章通知事件
     * 1：回答通知事件
     * 2：课程通知事件
     * 3：系统通知事件
     */
    private Integer eventType;

    /**
     * 事件目标id
     */
    private String targetId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public NotifyEvent(Object source) {
        super(source);
    }
}
