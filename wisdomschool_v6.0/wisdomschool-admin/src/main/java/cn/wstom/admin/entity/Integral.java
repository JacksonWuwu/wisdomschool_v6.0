package cn.wstom.admin.entity;


import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 积分规则
 * @author dws
 * @date 2019/03/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Integral extends BaseEntity {
    private static final long serialVersionUID = 6469034883881828313L;
    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 顺序
     */
    private Integer orderNum;

    /**
     * 类型，0：奖励，1：惩罚
     */
    private Integer rewardType;
    private String rewardTypeStr;

    /**
     * 周期
     */
    private Integer cycleType;
    private String cycleTypeStr;
    /**
     * 周期：一次性
     */
    public static Integer CYCLE_TYPE_ONCE = 0;
    /**
     * 周期：每天一次
     */
    public static Integer CYCLE_TYPE_EVERYDAY = 1;
    /**
     * 周期：按间隔时间
     */
    public static Integer CYCLE_TYPE_INTERVAL = 2;
    /**
     * 周期：不限制
     */
    public static Integer CYCLE_TYPE_UNLIMIT = 3;


    /**
     * 最多奖励次数
     */
    private Integer cycleTime;

    /**
     * 奖励次数
     */
    private Integer rewardNum;

    /**
     * 积分
     */
    private Integer credit;

}
