
package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.entity.TestPaper;
import cn.wstom.exam.entity.UserTest;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.TestPaperService;
import cn.wstom.exam.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 试卷分布 列表
 *
 * @author hzh
 * @date 20190223
 */

@Controller
@RequestMapping("/school/onlineExam/exam")
public class examController extends BaseController {
    private String prefix = "school/onlineExam/exam";

    @Autowired
    private UserTestService userTestService;

    @Autowired
    private TestPaperService testPaperService;



    @GetMapping("/{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("cid", cid);
        return prefix + "/list";
    }

    /**
     * 查询测试关联列表
     */

    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, UserTest userTest) {
        userTest.setcId(cid);
        userTest.setCreateId(getUser().getUserAttrId());
        userTest.setType("1");
        startPage();
        List<UserTest> list = userTestService.list(userTest);
        final String yes = "是";
        final String no = "否";
        final String y = "1";
        for (UserTest test : list) {
            if (test.getSumbitState().equals(y)) {
                test.setSumbitState(yes);
            } else {
                test.setSumbitState(no);
            }
        }

        return wrapTable(list);
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, ModelMap mmap) {
        UserTest userTest = userTestService.getById(id);
        TestPaper testPaper = testPaperService.getById(userTest.getTestPaperId());
        mmap.put("userTest",userTest);
        mmap.put("testPaper",testPaper);
        return prefix + "/edit";
    }

    /**
     * 修改保存
     */
    @Log(title = "我的测试", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(UserTest UserTest) throws Exception {
        String tid1 = getUser().getUserAttrId();
        UserTest.setUpdateId(tid1);
        UserTest.setUpdateBy(getLoginName());
        try {
            return toAjax(userTestService.update(UserTest));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return error(e.getMessage());
        }
    }



    /**
     * 删除测试关联
     */
    @Log(title = "测试关联", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        String[] userId = ids.split(",");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < userId.length; i++) {
            list.add(userId[i]);
        }
        try {
            return toAjax(userTestService.removeByIds(list));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return error(e.getMessage());
        }
    }
}




