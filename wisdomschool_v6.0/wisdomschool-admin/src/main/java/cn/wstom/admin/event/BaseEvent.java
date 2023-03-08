package cn.wstom.admin.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * 基础事件类型，所有事件都继承此类
 *
 * @author dws
 * @date 2019/02/26
 */
@Getter
@Setter
@ToString
public abstract class BaseEvent extends ApplicationEvent {
    private static final long serialVersionUID = 862757347575002901L;

    /**
     * 事件源用户id
     */
    protected String sourceUserId;

    /**
     * 目标用户id
     */
    protected String targetUserId;

    /**
     * 事件类型
     */
    protected Integer eventType;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    BaseEvent(Object source) {
        super(source);
    }

}
