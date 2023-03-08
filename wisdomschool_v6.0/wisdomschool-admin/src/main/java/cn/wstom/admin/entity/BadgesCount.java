package cn.wstom.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dws
 */
@Data
public class BadgesCount implements Serializable {
    private static final long serialVersionUID = 8276459939240769498L;

    /**
     * 消息数量
     */
    private int messages;
}
