package cn.wstom.storage.api;

import cn.wstom.storage.server.service.ResourceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 为在线资源打造的控制器，针对在线播放等功能的适配
 * @author dws
 */
@Controller
@RequestMapping("/storage/resource")
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    /**
     * 以严格的HTTP响应格式返回在线资源流，适用于多数浏览器
     *
     * @param request
     * @param response
     */
    @RequestMapping("/getResource")
    public void getResource(HttpServletRequest request, HttpServletResponse response) {
        resourceService.getResource(request, response);
    }

    @RequestMapping({"/handle/{file}"})
    public void handle(@PathVariable String file, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("fid", file);
        request.setAttribute("review", true);    request.setAttribute("fid", file);
        request.setAttribute("review", true);
        resourceService.getResource(request, response);
    }

}
