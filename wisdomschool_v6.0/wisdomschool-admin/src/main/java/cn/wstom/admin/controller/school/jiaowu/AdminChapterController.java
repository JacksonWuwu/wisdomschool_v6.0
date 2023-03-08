package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 课程章节 信息操作处理
 *
 * @author hyb
 * @date 20190324
 */
@Controller
@RequestMapping("/jiaowu/chapter")
public class AdminChapterController extends BaseController {
    private String prefix = "/school/jiaowu/chapter";

    @Autowired
    private ChapterService chapterService;

    @RequiresPermissions("module:chapter:view")
    @GetMapping("/get/{cid}")
    public String toList(ModelMap modelMap, @PathVariable String cid) {
        modelMap.put("id", cid);
        return prefix + "/list";
    }

    /**
     * 查询课程章节列表
     */
    @RequiresPermissions("module:chapter:view")
    @RequestMapping("/list/{cid}")
    @ResponseBody
    public List<Chapter> list(@PathVariable("cid") String cid,Chapter chapter) {
        chapter.setCid(cid);
        List<Chapter> list = chapterService.list(chapter);
        return list;
    }

    /**
     * 新增课程章节
     */
    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        Chapter chapter;
        chapter = new Chapter();
        chapter.setPid(Constants.CHAPTER_PARENT_ID);
        chapter.setTitle("主目录");
        chapter.setCid(cid);
        modelMap.put("chapter", chapter);
        return prefix + "/add";
    }
    /**
     * 新增保存课程章节
     */
    @RequiresPermissions("module:chapter:view")
    @Log(title = "课程章节", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(Chapter chapter) throws Exception {
        chapter.setCreateBy(getUser().getLoginName());
        return toAjax(chapterService.save(chapter));
    }



    /**
     * 修改课程章节
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        Chapter chapter = chapterService.getById(id);
        String parentId = chapter.getPid();
        if(parentId.equals(Constants.CHAPTER_PARENT_ID)) {
            mmap.put("title","主目录");
        }
        else {
            Chapter chapterPid = chapterService.getById(parentId);
            String title = chapterPid.getTitle();
            mmap.put("title", title);
        }
        mmap.put("chapter", chapter);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程章节
     */
    @RequiresPermissions("module:chapter:view")
    @Log(title = "课程章节", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(Chapter chapter) throws Exception {
        return toAjax(chapterService.update(chapter));
    }

    /**
     * 删除课程章节
     */
    @RequiresPermissions("module:chapter:view")
    @Log(title = "课程章节", actionType = ActionType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(chapterService.removeById(ids));
    }

    /**
     * 加载课程章节树
     *
     * @param cid 课程id
     */
    @GetMapping("/chapterTreeData/{cid}")
    @ResponseBody
    public List<Map<String, Object>> getChapterTree(@PathVariable String cid) {
        return chapterService.getCourseChapterTree(cid);
    }

    /**
     * 加载课程章节树
     */
    @GetMapping("/chapterTree/{cid}")
    public String chapterTree(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/tree";
    }
}
