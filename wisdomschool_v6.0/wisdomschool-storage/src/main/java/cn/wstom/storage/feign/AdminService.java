package cn.wstom.storage.feign;


import cn.wstom.storage.server.pojo.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "wisdomschool-admin-service")
public interface AdminService {
    @GetMapping(value = "/system/user/{userId}")
    SysUser getUserById(@PathVariable(value = "userId")String userId);

    @PostMapping(value = "/system/user/getUser")
    SysUser getUser(SysUser sysUser);



}
