package cn.wstom.student.controller.front;


import cn.wstom.student.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dws
 */
@Controller
@RequestMapping("/help")
public class HelpController extends BaseController {

    private static final String PREFIX = "";

    /**
     * 帮助列表
     *
     * @return
     */
    @GetMapping(value = "/list")
    public String helpList() {
        return PREFIX + "help/list";
    }
}
