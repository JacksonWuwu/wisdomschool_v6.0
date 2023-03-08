package cn.wstom.exam.controller;

import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.entity.TestPaperWorkList;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.TestPaperWorkListService;
import cn.wstom.exam.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/school/onlineExam/testPaperWorkList")
public class TestPpaperWorkListController extends BaseController {
    private String prefix = "school/onlineExam/testPaperWorkList";

    @Autowired
    private TestPaperWorkListService testPaperOneListService;

    @GetMapping("{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/list";
    }
    /**
     * 查询考试列表
     */

    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, TestPaperWorkList testPaper) {

        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("2");
        String sj = "随机";
        String gd = "固定";
        String y = "是";
        String n = "否";
        startPage();
        List<TestPaperWorkList> list = testPaperOneListService.selectList(testPaper);
        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRule().equals("0")) {
                list.get(i).setRule(gd);
            } else {
                list.get(i).setRule(sj);
            }
            if (list.get(i).getState().equals("0")) {
                list.get(i).setState(y);
            } else {
                list.get(i).setState(n);
            }

        }

        //  List<TestPaper> list =testPaperService.findStuPaper(testPaper);
        return wrapTable(list);

    }
    /**
     * 新增试卷
     */
    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/add";
    }
    /**
     * 保存试卷
     */

    @Log(title = "试卷库", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestPaperWorkList testPaper) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>" + testPaper);
        testPaper.setCreateBy(getUser().getLoginName());
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setState("1");
        testPaper.setType("2");
        return toAjax(testPaperOneListService.save(testPaper));
    }
    /**
     * 修改试卷
     */
    @GetMapping("/edit/{id}")

    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestPaperWorkList testPaper = testPaperOneListService.getById(id);
        mmap.put("testPaper", testPaper);
        return prefix + "/edit";
    }
    /**
     * 修改保存试卷
     */

    @Log(title = "试卷", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edithomework(TestPaperWorkList testPaper) throws Exception {
        testPaper.setUpdateId(getUser().getUserAttrId());
        testPaper.setUpdateBy(getUser().getLoginName());
        return toAjax(testPaperOneListService.update(testPaper));
    }
    /**
     * 删除试卷
     */

    @Log(title = "试卷", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            return toAjax(testPaperOneListService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 课程 组卷 页面跳转 人手一份
     */
    @GetMapping("/Coursebuild")

    public String Coursebuild(String id, ModelMap mmap) {
        TestPaperWorkList testPaper = testPaperOneListService.getById(id);

        String tid = getUser().getUserAttrId();
        mmap.put("cid", testPaper.getCoursrId());
        mmap.put("tid", tid);
        mmap.put("testPaper", testPaper);
        return prefix + "/course_build";
    }
    /**
     * 手动 组卷 页面跳转
     */
    @GetMapping("/Humanbuild")

    public String Humanbuild(String id, ModelMap mmap) {
        TestPaperWorkList testPaper = testPaperOneListService.getById(id);
        mmap.put("testPaper", testPaper);
        mmap.put("tid", getUser().getUserAttrId());
        mmap.put("cid",testPaper.getCoursrId());
        mmap.put("id", id);
        mmap.put("rule",testPaper.getRule());
        return prefix + "/human_build";
    }
}
