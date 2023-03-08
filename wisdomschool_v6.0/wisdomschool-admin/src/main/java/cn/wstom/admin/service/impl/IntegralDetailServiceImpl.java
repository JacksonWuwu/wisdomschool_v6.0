package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.impl.BaseServiceImpl;
import cn.wstom.common.utils.DateUtils;
import cn.wstom.common.utils.StringUtil;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dws
 * @date 2019/03/25
 */
@Service
public class IntegralDetailServiceImpl extends BaseServiceImpl<IntegralDetailMapper, IntegralDetail>
        implements IntegralDetailService {
    private final IntegralService integralService;
    private final SysUserService userService;

    @Resource
    private IntegralDetailMapper integralDetailMapper;

    @Autowired
    public IntegralDetailServiceImpl(IntegralService integralService, SysUserService userService) {
        this.integralService = integralService;
        this.userService = userService;
    }

    /**
     * 积分处理
     *
     * @param user     操作用户
     * @param ruleCode 积分编码
     * @param content  积分内容
     * @return 积分操作结果
     * @throws Exception 该积分编码不存在则抛出异常
     */
    @Override
    public int handleCedit(SysUser user, String ruleCode, String content) throws Exception {
        if (user.getId() == null || ruleCode == null || "".equals(ruleCode.trim())) {
            return 0;
        }
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("code", ruleCode);
        Integral integral = integralService.getOne(params);

        Assert.notNull(integral, "该积分规则不存在");

        boolean pro = true;

        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setIntegralId(integral.getId());
        integralDetail.setUserId(user.getId());
        integralDetail.setUserName(user.getUserName());

        params.clear();
        params.put("userId", integralDetail.getUserId());
        params.put("integralId", integralDetail.getIntegralId());

        //判断是否需要处理
        if (Integral.CYCLE_TYPE_ONCE.equals(integral.getCycleType())) {
            //一次性
            //判断是否已经处理过
            if (integralDetailMapper.selectCount(params) > 0) {
                pro = false;
            }
        } else if (Integral.CYCLE_TYPE_EVERYDAY.equals(integral.getCycleType())) {
            //每天一次
            //判断是否已经处理过
            params.clear();
            params.put("createTime", integralDetail.getCreateTime());
            if (integralDetailMapper.selectCount(params) > 0) {
                pro = false;
            }

            params.remove("createTime");
        } else if (Integral.CYCLE_TYPE_INTERVAL.equals(integral.getCycleType())) {
            //判断是否已经处理过
            params.clear();
            params.put("orderBy", "create_time");
            params.put("order", "desc");

            IntegralDetail detail = integralDetailMapper.selectOne(params);
            if (detail != null) {
                if (DateUtils.differ(detail.getCreateTime(), new Date()) / (1000 * 60)
                        < integral.getCycleTime()) {
                    pro = false;
                }
            }
        }
        //判断是否有最多处理次数限制
        if (!Integral.CYCLE_TYPE_UNLIMIT.equals(integral.getCycleType())
                && integral.getRewardNum() != null && integral.getRewardNum() > 0
                && integralDetailMapper.selectCount(params) >= integral.getRewardNum()) {
            pro = false;
        }
        if (pro) {
            if (1 == integral.getRewardType()) {
                //奖励
                user.setCredit(StringUtil.nvl(user.getCredit(), 0) + integral.getCredit());
            } else {
                //惩罚
                user.setCredit(StringUtil.nvl(user.getCredit(), 1) - integral.getCredit());
                if (user.getCredit() < 0) {
                    user.setCredit(0);
                }
            }
            //更新用户积分
            userService.update(user);
            //添加积分记录
            integralDetail.setType(integral.getRewardType());
            integralDetail.setCredit(integral.getCredit());
            integralDetail.setContent(content);
            return integralDetailMapper.insert(integralDetail);
        }
        return 0;
    }

    @Override
    public List<IntegralDetail> selectBatchIntegral(List<String> ids) {
        return integralDetailMapper.selectBatchIntegral(ids);
    }

    @Override
    public List<IntegralDetail> selectBatchIntegral() {
        return integralDetailMapper.selectWeekIntegral();
    }

}
