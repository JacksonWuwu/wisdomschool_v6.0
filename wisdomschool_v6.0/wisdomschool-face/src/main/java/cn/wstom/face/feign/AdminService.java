package cn.wstom.face.feign;


import cn.wstom.face.constants.Data;
import cn.wstom.face.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@FeignClient(value = "wisdomschool-admin-service")
public interface AdminService {

    //sys
    @RequestMapping(value = "/admin/sysLogin/login")
    Data login(@RequestBody SysUser sysUser);

    @RequestMapping(value = "/system/user/{userId}")
    SysUser getUserById(@PathVariable(value = "userId")String userId);

    @RequestMapping(value = "/system/user/getUser")
    SysUser getUser(@RequestBody SysUser sysUser);

    @RequestMapping(value = "/system/user/getUserList")
    List<SysUser> getUserList(@RequestBody SysUser sysUser);



}
