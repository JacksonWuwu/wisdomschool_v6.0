package cn.wstom.student.controller.course;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.*;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/preview")
public class PreviewController extends BaseController {


    /**
     *
     * 课程列表获取
     * @return
     */
    @ApiOperation("学生课程列表")
    @GetMapping(value = "/list")
    public Data list(@RequestParam(required = false, defaultValue = "0", value = "tcid") String tcid) {
        String studentId = getUser().getUserAttrId();
        System.out.println("====preview--List====");
        PreviewStudent previewStudent=new PreviewStudent();
        previewStudent.setTcid(Integer.valueOf(tcid));
        previewStudent.setSid(Integer.valueOf(studentId));
        List<String> list2= adminService.listBySidAndTcid(previewStudent);
        List<Preview> previewList =new ArrayList<>();
        list2.forEach(t->{
            Preview preview=adminService.getPreviewById(t);
            previewList.add(preview);
        });
        Map<String, Object> map = new HashMap<>();
        System.out.println("previewList"+previewList);

        return Data.success(previewList);
    }


    /**
     * 课程内容获取
     * @param pid
     * @return
     */
    @ApiOperation("课程内容")
    @GetMapping("/detail/{pid}")
    public Data detail(@PathVariable String pid) {
        System.out.println("++++chapter+++++");
        //  课程信息
        Preview preview=adminService.getPreviewById(pid);
        TeacherCourse teachCourse = adminService.getTeacherCourseById(preview.getTcid());
        Course course = adminService.getCourseById(teachCourse.getCid());
        Chapter chapter = new Chapter();
        chapter.setPreviewid(pid);
        chapter.setCid(course.getId());
        List<Chapter> chapters = adminService.chapterList(chapter);
        return Data.success(chapters);
    }

    private List<ChapterVo> transTree(List<Chapter> chapters) {
        Map<String, ChapterVo> chapterMap = new LinkedHashMap<>();
        chapters.forEach(c -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(c, chapterVo);
            chapterMap.put(c.getId(), chapterVo);
        });
        Map<String, ChapterVo> tree = new LinkedHashMap<>();
        chapters.forEach(c -> {
            if ("0".equals(c.getPid())) {
                tree.put(c.getId(), chapterMap.get(c.getPid()));
            } else {
                ChapterVo chapterVo = chapterMap.get(c.getPid());
                tree.putIfAbsent(chapterVo.getId(), chapterVo);
                tree.get(chapterVo.getId()).getChildren().add(c);
            }
        });
        return Lists.newArrayList(tree.values());
    }
}
