package cn.wstom.student.controller.front;


import cn.wstom.student.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author dws
 */
@Controller
public class PeopleController extends BaseController {

    private static final String PREFIX = "/front/user";

    private static Logger logger = LoggerFactory.getLogger(PeopleController.class);



    /**
     * 用户首页页面
     *
     * @param p
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/people/{id}")
    public String people(@RequestParam(value = "p", defaultValue = "1") int p, @PathVariable(value = "id") String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");

        modelMap.addAttribute("user", getUser());
        modelMap.addAttribute("p", p);
        return PREFIX + "/index";
    }

    /**
     * 用户问题列表页面
     *
     * @param p
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/people/{id}/question")
    public String peopleQuestion(@RequestParam(value = "p", defaultValue = "1") int p, @PathVariable(value = "id") String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");
        modelMap.addAttribute("user", getUser());
        modelMap.addAttribute("p", p);
        return PREFIX + "/question";
    }

    /**
     * 用户问题列表页面
     *
     * @param p
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/people/{id}/answers")
    public String peopleAnswers(@RequestParam(value = "p", defaultValue = "1") int p, @PathVariable(value = "id") String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");

        modelMap.addAttribute("user", getUser());
        modelMap.addAttribute("p", p);
        return PREFIX + "/answers";
    }

    /**
     * 用户问题列表页面
     *
     * @param p
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/people/{id}/article")
    public String peopleArticle(@RequestParam(value = "p", defaultValue = "1") int p, @PathVariable(value = "id") String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");

        modelMap.addAttribute("user", getUser());
        modelMap.addAttribute("p", p);
        return PREFIX + "/article";
    }

    /**
     * 用户问题列表页面
     *
     * @param p
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/people/{id}/share")
    public String peopleShare(@RequestParam(value = "p", defaultValue = "1") int p, @PathVariable(value = "id") String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");

        modelMap.addAttribute("user", getUser());
        modelMap.addAttribute("p", p);
        return PREFIX + "/share";
    }

    /**
     * 用户关注列表页面
     *
     * @param p
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/people/{id}/follow")
    public String peopleFollow(@RequestParam(value = "p", defaultValue = "1") int p, @PathVariable(value = "id", required = false) String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");

        modelMap.addAttribute("user", getUser());
        modelMap.addAttribute("p", p);
        return PREFIX + "/follow";
    }

    /**
     * 用户问题列表页面
     */
    @GetMapping(value = "/people/{id}/fans")
    public String peopleFans(@RequestParam(value = "p", defaultValue = "1") int p, @PathVariable(value = "id", required = false) String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");

        modelMap.addAttribute("user", getUser());
        modelMap.addAttribute("p", p);
        return PREFIX + "/fans";
    }
}
