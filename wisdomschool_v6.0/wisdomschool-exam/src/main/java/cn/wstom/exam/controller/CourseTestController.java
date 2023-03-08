
package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.entity.TestPaper;
import cn.wstom.exam.service.TestPaperService;
import cn.wstom.exam.utils.Convert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 试卷 信息操作处理stuToTest
 *
 * @author hzh
 * @date 20190223
 */

@Controller
@RequestMapping("/school/onlineExam/courseTest")
public class CourseTestController extends BaseController {
    private String prefix = "school/onlineExam/courseTest";



    @Autowired
    private TestPaperService testPaperService;



    @GetMapping("{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("cid", cid);
        return prefix + "/list";
    }


    /**
     * 查询试卷列表
     */

    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, TestPaper testPaper) {

        testPaper.setTsId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("1");
        startPage();
        List<TestPaper> list =testPaperService.findStuPaper(testPaper);
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
     * 新增保存试卷
     */

    @RequiresPermissions("teacher:course:view")
    @Log(title = "试卷", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestPaper testPaper) throws Exception {
        testPaper.setCreateBy(getUser().getLoginName());
        testPaper.setState("1");
        return toAjax(testPaperService.save(testPaper));
    }


    /**
     * 修改试卷
     */

    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestPaper testPaper = testPaperService.getById(id);
        mmap.put("testPaper", testPaper);
        return prefix + "/edit";
    }

    /**
     * 修改保存试卷
     */

    @Log(title = "试卷", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(TestPaper testPaper) throws Exception {
        return toAjax(testPaperService.update(testPaper));
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
            return toAjax(testPaperService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

}




