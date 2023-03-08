package cn.wstom.admin.controller.school.teacher;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.feign.StudentService;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher/ppt")
public class PPTUserController extends BaseController {

    @Autowired
    private PptChapterUserService pptChapterUserService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private RecourseService recourseService;
    @Autowired
    private ChapterResourceService chapterResourceService;

    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private SysUserService sysUserService;
    /*
     * ppt详细
     * */
    @RequiresPermissions("teacher:chapter:view")
    @GetMapping("/Detail")
    public String topptList(@RequestParam("id") String userId, @RequestParam("tcId") String tcId, ModelMap modelMap) {
        modelMap.put("userId", userId);
        modelMap.put("tcId", tcId);
        System.out.println("tcid");
        return "school/teacher/statis/detailpptPaper";
    }

    /**
     * 查询用户观看PPT列表
     */
//    @RequiresPermissions("teacher:chapter:view")
    @PostMapping("/list/{tcid}/{userId}")
    @ResponseBody
    public TableDataInfo list(@PathVariable("tcid") String tcId, @PathVariable("userId") String userId, VideoChapterUserVo vo) throws Exception{
        SysUser stu = null;
        System.out.println("adsdsadasd");
        try {
            stu = sysUserService.getById(userId);
        } catch (Exception e) {
            throw new Exception("未找到该学生用户");
        }

        ChapterResource chapterResource = new ChapterResource();
        chapterResource.setTcId(tcId);
        System.out.println("tcId====="+tcId);
        List<ChapterResource> crlist = chapterResourceService.list(chapterResource);
        List<String> list=new ArrayList<>();
        crlist.forEach(c->{
            list.add(c.getId());

        });
        PptChapterUser pptChapterUser=new PptChapterUser();
        pptChapterUser.setCrids(list);
        pptChapterUser.setUserId(Integer.valueOf(userId));
        System.out.println("list"+list);
        startPage();
        List<PptChapterUser> vcs = pptChapterUserService.list(pptChapterUser);
        if (!CollectionUtils.isEmpty(vcs)) {
            SysUser finalStu = stu;
            vcs.forEach(v -> {
                ChapterResource cr = chapterResourceService.getById(String.valueOf(v.getResourceChapterId()));
                Chapter chapter = null;
                Recourse recourse = null;
                if (cr != null) {
                    chapter = chapterService.getById(cr.getCid());
                    recourse = recourseService.getById(cr.getRid());
                }

                int cout=v.getCout();

                Map<String, Object> params = new HashMap<>();
                if (cout != 0) {
                    params.put("cout", cout);
                }else {
                    params.put("cout", "未阅读");
                }

                if (chapter != null) {
                    params.put("chapterName", chapter.getName());
                } else {
                    params.put("chapterName", "未找到章节");
                }

                if (recourse != null) {
                    params.put("resourceName", recourse.getName());
                } else {
                    params.put("resourceName", "未找到资源");
                }

                if (finalStu != null) {
                    params.put("studentName", finalStu.getUserName());
                }
                v.setParams(params);
            });
        }

        return wrapTable(vcs);
    }
}
