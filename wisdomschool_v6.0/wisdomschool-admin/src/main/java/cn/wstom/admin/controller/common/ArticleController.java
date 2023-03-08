package cn.wstom.admin.controller.common;

import cn.wstom.admin.controller.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.common.web.page.TableDataInfo;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/jiaowu/article")
public class ArticleController extends BaseController {

    private String prefix = "/common/article";

    @Autowired
    private ArticleService articleService;


    @RequiresPermissions("jiaowu:article:view")
    @GetMapping("/list")
    public String toList() {
        return prefix + "/list";
    }

    @RequiresPermissions("jiaowu:article:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Article article) {
        startPage();
        List<Article> list = articleService.list(article);
        return wrapTable(list);
    }

    /**
     * 新增轮播图片
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    @RequiresPermissions("jiaowu:article:add")
    @Log(title = "文章")
    @PostMapping("/add")
    @ResponseBody
    public Data add(Article article) throws Exception {
        return toAjax(articleService.addArticle(article));
    }

    /**
     * 修改轮播图片
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, ModelMap modelMap) {
        modelMap.put("article", articleService.getById(id));
        return prefix + "/edit";
    }

    @RequiresPermissions("jiaowu:article:edit")
    @Log(title = "文章")
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(Article article) throws Exception {
        System.out.println(article.toString());
        return toAjax(articleService.update(article));
    }


    @RequiresPermissions("jiaowu:article:remove")
    @Log(title = "文章")
    @PostMapping( "/remove")
    @ResponseBody
    public Data remove(String id) throws Exception {
        return toAjax(articleService.deleteArticleById(id));
    }
}
