package cn.wstom.monitor.feign;


import cn.wstom.monitor.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "wisdomschool-admin-service")
public interface AdminService {

    //sys
    @RequestMapping(value = "/system/user/getUserList")
    List<SysUser> getUserList(@RequestBody SysUser sysUser);

    @RequestMapping(value = "/system/user/{userId}")
    SysUser getUserById(@PathVariable(value = "userId")String userId);

    @RequestMapping(value = "/system/user/getUser")
    SysUser getUser(@RequestBody SysUser sysUser);


}
