package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 积分明细
 *
 * @author dws
 * @date 2019/03/25
 */
@Getter
@Setter
@ToString
public class IntegralDetail extends BaseEntity {


    private static final long serialVersionUID = -4793796084200015462L;

    /**
     * 用户id
     */
    private String userId;

    private String userName;

    /**
     * 积分规则id
     */
    private String integralId;

    /**
     * 积分类型
     */
    private Integer type;

    /**
     * 积分值
     */
    private Integer credit;

    /**
     * 类型名
     */
    private String typeName;

    /**
     * 积分生成具体内容
     */
    private String content;

    /**
     * 总积分
     */
    private String sum;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
