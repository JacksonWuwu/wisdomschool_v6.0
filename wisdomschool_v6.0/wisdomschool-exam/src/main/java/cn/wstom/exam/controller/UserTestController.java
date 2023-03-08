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
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 试卷与学生关联 信息操作处理
 *
 * @author hzh
 * @date 20190223
 */
@Controller
@RequestMapping("/school/onlineExam/userTest")
public class UserTestController extends BaseController {
    private String prefix = "school/onlineExam/userTest";

    @Autowired
    private UserTestService userTestService;
    @Autowired
    private TestPaperService testPaperService;



    @RequestMapping("/selectUserTestListBase")
    @ResponseBody
    List<UserTest> selectUserTestListBase(@RequestBody UserTest userTest){
        return userTestService.selectListBase(userTest);
    }

    @RequestMapping("/userTestList")
    @ResponseBody
    List<UserTest> userTestList(@RequestBody UserTest userTest){
        return userTestService.list(userTest);
    }

    @RequestMapping("/userTestRemoveByIds")
    @ResponseBody
    Boolean userTestRemoveByIds(@RequestBody List<String> ids) throws Exception {
        return userTestService.removeByIds(ids);
    }

    @RequestMapping(value = "/getTcoExamPaper")
    @ResponseBody
    List<UserTest>  getTcoExamPaper(@RequestBody UserTest userTest,
                                    @RequestParam(required = false,value = "pageNum")Integer pageNum,
                                    @RequestParam(required = false,value = "pageSize")Integer pageSize,
                                    @RequestParam(required = false,value = "orderBy")String orderBy){
        PageHelper.startPage(pageNum,pageSize,orderBy);
        return userTestService.getTcoExamPaper(userTest);
    }

    @GetMapping("/{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        System.out.println("cid::::" + cid);
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
        String tid = getUser().getUserAttrId();
        userTest.setCreateId(tid);
        startPage();
        List<UserTest> list = userTestService.list(userTest);
        return wrapTable(list);
    }

    @PostMapping("/getlist")
    @ResponseBody
    public List<TestPaper> getlist(String cid) {
        startPage();
        TestPaper testPaper = new TestPaper();
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("1");
        return testPaperService.list(testPaper);
    }

    @PostMapping("/getlistExam")
    @ResponseBody
    public List<TestPaper> getlistExam(String cid) {
        startPage();
        TestPaper testPaper = new TestPaper();
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("1");
        return testPaperService.list(testPaper);
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
     * 新增测试关联
     */

    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {

        String tid = getUser().getUserAttrId();
        modelMap.put("cid", cid);
        modelMap.put("tid", tid);
        return prefix + "/add";
    }

    /**
     * 新增保存测试关联
     */

    @Log(title = "测试关联", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(UserTest userTest) {
        String[] userId = userTest.getUserId().split(",");
        List<UserTest> list = new ArrayList<UserTest>();
        try {
            for (String s : userId) {
                UserTest userTest2 = new UserTest();
                userTest2.setTestPaperId(userTest.getTestPaperId());
                userTest2.setUserId(s);
                List<UserTest> userTestList = new ArrayList<UserTest>();
                userTestList = userTestService.list(userTest2);
                if (userTestList.size() == 0) {
                    UserTest userTest1 = new UserTest();
                    userTest1.setTestPaperId(userTest.getTestPaperId());
                    userTest1.setUserId(s);
                    userTest1.setStuStartTime(userTest.getStuStartTime());
                    userTest1.setStuEndTime(userTest.getStuEndTime());
                    userTest1.setCreateBy(getUser().getLoginName());
                    userTest1.setSumbitState("0"); //0表示未提交,1为提交
                    userTest1.setcId(userTest.getcId());
                    String tid1 = getUser().getUserAttrId();
                    userTest1.setCreateId(tid1);
                    list.add(userTest1);
                }
            }
            return toAjax(userTestService.saveBatch(list));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return error("记录已存在");
        }
    }


    @GetMapping("/fabu")

    public String fabu(String id,String cid,ModelMap modelMap){
        modelMap.put("id",id);
        TestPaper testPaper = testPaperService.getById(id);
        modelMap.put("cid",testPaper.getCoursrId());
        modelMap.put("tid", getUser().getUserAttrId());
         modelMap.put("rule",testPaper.getRule());
        return prefix + "/fabu";
    }



    @Log(title = "测试关联", actionType = ActionType.INSERT)
    @PostMapping("/addfabu")
    @ResponseBody
    public Data addfabu(UserTest userTest) {
        String[] userId = userTest.getUserId().split(",");
        List<UserTest> list = new ArrayList<UserTest>();
        /**
         *  -  发布后 添加学生试题
         *  -  因为可以重复更新题型，此处进行的操作为若不存在该题型的试卷则insert，存在则更新
         */
        try {
            for (String s : userId) {
                UserTest userTest2 = new UserTest();
                userTest2.setTestPaperId(userTest.getTestPaperId());
                userTest2.setUserId(s);
                List<UserTest> userTestList = new ArrayList<UserTest>();
                userTestList = userTestService.list(userTest2);
                if (userTestList.size() == 0) {
                    UserTest userTest1 = new UserTest();
                    userTest1.setTestPaperId(userTest.getTestPaperId());
                    userTest1.setUserId(s);
                    userTest1.setCreateBy(getUser().getLoginName());
                    userTest1.setSumbitState("0"); //0表示未提交,1为提交
                    userTest1.setcId(userTest.getcId());
                    String tid1 = getUser().getUserAttrId();
                    userTest1.setCreateId(tid1);
                    list.add(userTest1);
                }
            }
            return toAjax(userTestService.saveBatch(list));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return error("记录已存在");
        }
    }


    /**
     * 新增保存测试关联  期末考
     */

    @Log(title = "测试关联", actionType = ActionType.INSERT)
    @PostMapping("/addQ")
    @ResponseBody
    public Data addQ(UserTest userTest) {
        System.out.println("userTest:" + userTest);
        String[] userId = userTest.getUserId().split(",");
        List<UserTest> list = new ArrayList<UserTest>();
        try {
            for (int i = 0; i < userId.length; i++) {
                UserTest userTest2 = new UserTest();
                userTest2.setTestPaperId(userTest.getTestPaperId());
                userTest2.setUserId(userId[i]);
                List<UserTest> userTestList = new ArrayList<UserTest>();
                userTestList = userTestService.list(userTest2);
                if (userTestList.size() == 0) {
                    System.out.println("新纪录");
                    UserTest userTest1 = new UserTest();
                    userTest1.setTestPaperId(userTest.getTestPaperId());
                    userTest1.setUserId(userId[i]);
                    userTest1.setStuStartTime(userTest.getStuStartTime());
                    userTest1.setCreateBy(getUser().getLoginName());
                    userTest1.setStuEndTime(userTest.getStuEndTime());
                    userTest1.setSumbitState("0"); //0表示未提交,1为提交
                    userTest1.setcId(userTest.getcId());
                    String tid1 = getUser().getUserAttrId();
                    userTest1.setCreateId(tid1);
                    list.add(userTest1);
                }
            }
            return toAjax(userTestService.saveBatch(list));
        } catch (Exception e) {
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


    /**
     * 获取年级
     */
    @PostMapping("/getStudents")
    @ResponseBody
    public List<UserTest> getStudents(String cId, String tid)
    /*UserTest userTest 以后要获取老师和老师绑定的课程*/ {
        //获取老师和老师绑定的课程来获取年级，此处是做测试使用
        UserTest userTest = new UserTest();
        String crid = getUser().getUserAttrId();
        userTest.setCreateId(crid);
        userTest.setTcoId(cId);
        return userTestService.getStudentInfo(crid, cId);

    }

    /**
     * 获取班级
     */
    @PostMapping("/getTcoName")
    @ResponseBody
    public List<UserTest> getTcoName(String cId, String tid) {
        UserTest userTest = new UserTest();
        String crid = getUser().getUserAttrId();
        userTest.setCreateId(crid);
        userTest.setTcoId(cId);
        List<UserTest> list = userTestService.getTcoName(crid, cId);
        System.out.println(list);
        return userTestService.getTcoName(crid, cId);

    }

    /**
     * 获取学生
     */
    @PostMapping("/getTcoStu")
    @ResponseBody
    public List<UserTest> getTcoStu(String cId, String tid) {
        UserTest userTest = new UserTest();
        String crid = getUser().getUserAttrId();
        userTest.setCreateId(crid);
        userTest.setTcoId(cId);
        return userTestService.getTcoStu(crid, cId);

    }

    /**
     * 查询期末试卷列表
     */

    @PostMapping("/testPaperlist/{cid}")
    @ResponseBody
    public TableDataInfo testPaperlist(@PathVariable String cid, TestPaper testPaper) {
        startPage();
        testPaper.setCoursrId(cid);
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setType("1");
        List<TestPaper> list = testPaperService.list(testPaper);
        return wrapTable(list);
    }

    /**
     * 查询期末试卷列表
     */

    @PostMapping("/testCoursePaperlist/{cid}")
    @ResponseBody
    public TableDataInfo testCoursePaperlist(@PathVariable String cid, TestPaper testPaper) {
        startPage();
        testPaper.setCoursrId(cid);
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setType("0");
        List<TestPaper> list = testPaperService.list(testPaper);
        return wrapTable(list);
    }
    /**
     * 设置提交的状态
     */
    @RequestMapping("/setState")
    @ResponseBody
    public Data setState(@RequestParam String paperId,
                         @RequestParam String studentId, @RequestParam String tutId)throws Exception {
        System.out.println("====================设置提交状态paperId:" +paperId+"--studentId--"+studentId+"--tutId--"+tutId);
            UserTest userTest1 = new UserTest();
            List<UserTest> userTestList;
            userTestList = userTestService.list(userTest1);
            userTest1.setId(tutId);
            userTest1.setSumbitState("1");
            userTest1.setUpdateBy(getUser().getLoginName());
            userTest1.setUserId(getUser().getUserAttrId());
            userTest1.setUpdateBy(getUser().getLoginName());
            return toAjax( userTestService.update(userTest1));
    }


}
