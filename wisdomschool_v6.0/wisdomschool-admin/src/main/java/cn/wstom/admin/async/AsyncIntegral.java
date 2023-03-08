package cn.wstom.admin.async;



import cn.wstom.admin.entity.SysUser;
import cn.wstom.admin.service.IntegralDetailService;
import cn.wstom.common.utils.SpringUtils;

import java.util.TimerTask;

/**
 * @author dws
 * @date 2019/03/28
 */
public class AsyncIntegral {


    /**
     * 积分记录
     *
     * @param content 积分内容
     * @return 任务task
     */
    public static TimerTask recordIntegral(SysUser user, String code, String content) {
        IntegralDetailService detailService = SpringUtils.getBean(IntegralDetailService.class);
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    detailService.handleCedit(user, code, content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
