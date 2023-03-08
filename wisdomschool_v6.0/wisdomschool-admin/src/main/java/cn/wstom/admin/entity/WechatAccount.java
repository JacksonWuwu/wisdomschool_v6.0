package cn.wstom.admin.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author liniec
 * @since 2019年9月27日 15点44分
 */
@Getter
@Setter
public class WechatAccount implements Serializable {

    private Integer id;

    private String openid;

    private String studentId;
}
