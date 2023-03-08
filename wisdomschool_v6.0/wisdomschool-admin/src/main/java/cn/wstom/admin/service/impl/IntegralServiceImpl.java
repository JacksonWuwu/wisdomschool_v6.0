package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 积分处理服务
 *
 * @author dws
 */
@Service
public class IntegralServiceImpl extends BaseServiceImpl
        <IntegralMapper, Integral>
        implements IntegralService {

    @Resource
    private IntegralMapper integralMapper;
}
