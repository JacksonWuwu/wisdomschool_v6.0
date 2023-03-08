package cn.wstom.student.controller.front;


import cn.wstom.student.controller.BaseController;
import cn.wstom.student.utils.StringHelperUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @version 1.0 <br/>
 */
@Controller
public class SearchController extends BaseController {
  //  protected final static Logger logger = Logger.getLogger(SearchController.class);

    private static final String PREFIX = "/front/search";

    /**
     * 搜索列表
     *
     * @param q        搜索标题
     * @param modelMap
     * @return
     */
    @GetMapping(value = {"/search"})
    public String companyList(@RequestParam(value = "q", required = false) String q,
                              @RequestParam(value = "type", required = false) String type,
                              @RequestParam(value = "ct", required = false) String ct,
                              @RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap) {
        if (!StringUtils.isBlank(type)) {
            if (!StringHelperUtils.checkInteger(type)) {
                type = null;
            }
        }
        if (!StringUtils.isBlank(ct)) {
            if (!StringHelperUtils.checkInteger(ct)) {
                ct = null;
            }
        }
        if (getUser() != null) {
            modelMap.addAttribute("user", getUser());
        }
        modelMap.addAttribute("title", q);
        modelMap.addAttribute("type", type);
        modelMap.addAttribute("ct", ct);
        modelMap.addAttribute("p", p);
        return PREFIX + "/detail";
    }
}
