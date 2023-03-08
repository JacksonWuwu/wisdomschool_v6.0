package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.entity.Individual.MyQuestionVO;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.feign.StudentService;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.Convert;
import cn.wstom.exam.utils.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


/**
 * 存放考试的临时 信息操作处理
 *
 * @author hzh
 * @date 20190308
 */
@Controller
@RequestMapping("/school/onlineExam/websocket")
public class WebsocketController extends BaseController {
    private String prefix = "school/onlineExam/websocket";

    @Autowired
    private WebsocketService websocketService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private TestPaperService testPaperService;
    @Autowired
    private MyTitleTypeService myTitleTypeService;
    @Autowired
    private MyOptionAnswerService myOptionAnswerService;
    @Autowired
    private MyQuestionsService myQuestionsService;

    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 查询存放考试的临时列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Websocket websocket) {
        startPage();
        List<Websocket> list = websocketService.list(websocket);
        return wrapTable(list);
    }

    /**
     * 新增存放考试的临时
     */
    @GetMapping("/add")
    public String toAdd() {
        System.out.println("ADD");
        return prefix + "/add";
    }

    /**
     * 新增保存存放考试的临时
     */
    @Log(title = "存放考试的临时", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(Websocket websocket) throws Exception {
        return toAjax(websocketService.save(websocket));
    }

    /**
     * 修改存放考试的临时
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, ModelMap mmap) {
        Websocket websocket = websocketService.getById(id);
        mmap.put("websocket", websocket);
        return prefix + "/edit";
    }

    /**
     * 修改保存存放考试的临时
     */

    @Log(title = "存放考试的临时", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(Websocket websocket) throws Exception {
        return toAjax(websocketService.update(websocket));
    }

    /**
     * 删除存放考试的临时
     */

    @Log(title = "存放考试的临时", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {

        try {
            return toAjax(websocketService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
        } catch (Exception e) {
            return error(e.getMessage());
        }

    }

    /**
     * 考生进入
     *
     * @param paperId
     */
    @RequestMapping("/sign")
    public void sign(String paperId,String studentId) throws Exception {
        Websocket webSocket = new Websocket();
        SysUser sysUser = new SysUser();
        String uid = getUserId();
        sysUser.setId(uid);
        sysUser = adminService.getUserById(uid);
        TestPaper testPaper = testPaperService.getById(paperId);
        webSocket.setTcId(paperId);
        sysUser = getUser();
        String sid = sysUser.getUserAttrId();
        webSocket.setStuId(studentId);
        webSocket.setCreateBy(sysUser.getUserName());
        webSocket.setStuNum(getUser().getLoginName());
        webSocket.setName(testPaper.getTestName());
        Student student = studentService.getStudentById("1");
        List<Websocket> allSem = new ArrayList<Websocket>();
        allSem = websocketService.list(webSocket);
        if (allSem.size() != 0) {
            ;
        } else {
            webSocket.setCreateBy(sysUser.getLoginName());
            websocketService.save(webSocket);
            String stuID = webSocket.getId();
        }
    }

    /**
     * 退出考试
     *
     * @param paperId
     */
    @RequestMapping("/signout")
    public void signout(String paperId,String studentId) throws Exception {
        Websocket webSocket = new Websocket();
        TestPaper testPaper = testPaperService.getById(paperId);
        webSocket.setTcId(paperId);
        webSocket.setStuId(studentId);
        webSocket.setCreateBy(getUser().getLoginName());
        webSocket.setStuNum(getUser().getLoginName());
        webSocket.setName(testPaper.getName());
        List<Websocket> allSem = new ArrayList<Websocket>();
        allSem = websocketService.list(webSocket);
        if (allSem.size() != 0) {
            for (int i = 0; i < allSem.size(); i++) {
                System.out.println("size:" + allSem.size() + "::" + allSem.get(i).getId());
                String id = allSem.get(i).getId();
                websocketService.removeById(id);
            }

        }
    }


    @Log(title = "题目管理", actionType = ActionType.IMPORT)
    @RequiresPermissions("school:onlineExam:websocket:export")
    @PostMapping("/importData")
    @ResponseBody
    public Data importData(MultipartFile file, boolean updateSupport) throws Exception {
        System.out.println("导入试题");
        StringBuilder errorMsg = new StringBuilder();
            ExcelUtil<MyQuestionVO> util = new ExcelUtil<>(MyQuestionVO.class);
            List<MyQuestionVO> myQuestionVOList = util.importExcel(file.getInputStream());
            List<MyTitleType> myTitleTypeList = myTitleTypeService.list(null);
            Map<String, MyTitleType> myTitleTypeMap = transMyTitleType(myTitleTypeList);
            List<MyQuestions> myQuestionsList = myQuestionsService.list(null);
            Map<String, MyQuestions> myQuestionsMap = transMyQuestions(myQuestionsList);
            List<MyOptionAnswer> myOptionAnswerList = myOptionAnswerService.list(null);
            Map<String, MyOptionAnswer> myOptionAnswerMap = transMyOptionAnswer(myOptionAnswerList);
            List<Course> courseList = adminService.courseList(null);
            Map<String, Course> courseMap = transCourse(courseList);
            List<Chapter> chapterList = adminService.chapterList(null);
            Map<String, Chapter> chapterMap = transChapter(chapterList);
//        try {
//            for (MyQuestionVO q : myQuestionVOList) {
//                MyTitleType myTitleType = myTitleTypeMap.get(q.getMyTitleType().getTitleTypeName());
//                Course course = courseMap.get(q.getCourse().getName());
//                Chapter chapter = chapterMap.get(q.getChapter().getName());
//                MyOptionAnswer myOptionAnswer = myOptionAnswerMap.get(q.getMyOptionAnswer().getStanswer());
//                MyQuestions myQuestions = new MyQuestions();
//                BeanUtils.copyProperties(q.getMyQuestions(), myQuestions);
//                TitleType titleType = titleTypeService.getById(myTitleType.getPublicTitleId());
//                String aid = "";
//                String answString = q.getMyoptionAnswerArr();
//                if (titleType.getTemplateNum().equals("1") || titleType.getTemplateNum().equals("2")) {
//                    String options = q.getMyOptionAnswer().getStoption();
//                    String[] optionArray = options.split(";");
//                    String[] answArray = answString.split(";");
//                    int length = answArray.length;
//                    MyOptionAnswer myOptionAnswer1;
//                    for (int i = 0; i < optionArray.length; i++) {
//                        myOptionAnswer = new MyOptionAnswer();
//                        String[] answArraySplit = optionArray[i].split("\\.");
//                        for (int j = 0; j < answArray.length; j++) {
//                            if (answArray[j].equals(answArraySplit[0])) {
//                                myOptionAnswer.setStoption(answArraySplit[1]);
//                                myOptionAnswer.setStanswer(answArraySplit[0]);
//                                Uuid uuid = new Uuid();
//                                myOptionAnswer.setId(uuid.UId());
//                                myOptionAnswerService.save(myOptionAnswer);
//                                aid += myOptionAnswer.getId() + ";";
//                                break;
//                            } else {
//                                Uuid uuid = new Uuid();
//                                myOptionAnswer.setId(uuid.UId());
//                                myOptionAnswer.setStoption(answArraySplit[1]);
//                                myOptionAnswer.setStanswer("0");
//                                myOptionAnswerService.save(myOptionAnswer);
//                                aid += myOptionAnswer.getId() + ";";
//                                break;
//                            }
//                        }
//
//                    }
//                }
//                if (titleType.getTemplateNum().equals("4")) {
//                    final String yes = "对";
//                    myOptionAnswer = new MyOptionAnswer();
//                    if (yes.equals(answString)) {
//                        myOptionAnswer.setStoption("0");
//                        myOptionAnswer.setStanswer("T");
//                        Uuid uuid = new Uuid();
//                        myOptionAnswer.setId(uuid.UId());
//                        myOptionAnswerService.save(myOptionAnswer);
//                        aid += myOptionAnswer.getId() + ";";
//                    } else {
//                        myOptionAnswer.setStoption("0");
//                        myOptionAnswer.setStanswer("F");
//                        Uuid uuid = new Uuid();
//                        myOptionAnswer.setId(uuid.UId());
//                        myOptionAnswerService.save(myOptionAnswer);
//                        aid += myOptionAnswer.getId() + ";";
//                    }
//                }
//                if (titleType.getTemplateNum().equals("3")) {
//                    String options = q.getMyOptionAnswer().getStoption();
//                    String[] answArray = answString.split(";");
//                    int length = answArray.length;
//                    MyOptionAnswer myOptionAnswer1;
//                    for (int i = 0; i < answArray.length; i++) {
//                        myOptionAnswer = new MyOptionAnswer();
//                        String[] answArraySplit = answArray[i].split("\\.");
//                            String num = NumericToChinese.getChinseNum(i+1);
//                            myOptionAnswer.setStoption(answArraySplit[1]);
//                            myOptionAnswer.setStanswer("第" + num + "空");
//                            Uuid uuid = new Uuid();
//                            myOptionAnswer.setId(uuid.UId());
//                            myOptionAnswerService.save(myOptionAnswer);
//                            aid += myOptionAnswer.getId() + ";";
//
//                    }
//                }
//                if (titleType.getTemplateNum().equals("5")) {
//                    String options = q.getMyOptionAnswer().getStoption();
//                    MyOptionAnswer myOptionAnswer1;
//                    myOptionAnswer = new MyOptionAnswer();
//                    myOptionAnswer.setStoption(q.getMyoptionAnswerArr());
//                    myOptionAnswer.setStanswer("0");
//                    Uuid uuid = new Uuid();
//                    myOptionAnswer.setId(uuid.UId());
//                    myOptionAnswerService.save(myOptionAnswer);
//                    aid += myOptionAnswer.getId() + ";";
//
//                }
//                Calendar date = Calendar.getInstance();
//                String year = String.valueOf(date.get(Calendar.YEAR));
//                myQuestions.setYear(Integer.parseInt(year));
//                myQuestions.setCreateBy(ShiroUtils.getLoginName());
//                SysUser sysUser = sysUserService.getById(ShiroUtils.getUserId());
//                String sid = sysUser.getUserAttrId();
//                myQuestions.setCreateId(sid);
//                myQuestions.setCreateBy(ShiroUtils.getLoginName());
//                myQuestions.setTitleTypeId(myTitleType.getId());
//                myQuestions.setXzsubjectsId(course.getId());
//                myQuestions.setJchapterId(chapter.getId());
//                myQuestions.setDifficulty(q.getDifficulty());
//                myQuestions.setTitle(q.getMyQuestions().getTitle());
//                myQuestions.setParsing(q.getMyQuestions().getParsing());
//                myQuestions.setStstatus(0);
//                myQuestions.setQexposed("0");
//                myQuestions.setQmaxexposure("1000");
//                myQuestions.setMyoptionAnswerArr(aid);
//                myQuestionsService.save(myQuestions);
//            }
//
//        }catch (Exception e){
//            errorMsg.append("格式错误");
//        }
        return Data.success();

    }


    @RequiresPermissions("teacher:course:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public Data importTemplate() {
        try {
            ExcelUtil<MyQuestionVO> util = new ExcelUtil<>(MyQuestionVO.class);
            return util.importTemplateExcel("myQuestions");
        } catch (Exception e) {
            e.printStackTrace();
            return Data.error(e.getMessage());
        }
    }

    private Map<String, MyTitleType> transMyTitleType(List<MyTitleType> myTitleTypeList) {
        Map<String, MyTitleType> myTitleTypeMap = new HashMap<>(myTitleTypeList.size());
        myTitleTypeList.forEach(d -> myTitleTypeMap.put(d.getTitleTypeName(), d));
        return myTitleTypeMap;
    }


    private Map<String, MyQuestions> transMyQuestions(List<MyQuestions> myQuestionsList) {
        Map<String, MyQuestions> myQuestionsMap = new HashMap<>(myQuestionsList.size());
        myQuestionsList.forEach(d -> myQuestionsMap.put(d.getTitle(), d));
        return myQuestionsMap;
    }

    private Map<String, MyOptionAnswer> transMyOptionAnswer(List<MyOptionAnswer> myOptionAnswerList) {
        Map<String, MyOptionAnswer> myOptionAnswerMap = new HashMap<>(myOptionAnswerList.size());
        myOptionAnswerList.forEach(d -> myOptionAnswerMap.put(d.getStoption(), d));
        return myOptionAnswerMap;
    }

    private Map<String, Course> transCourse(List<Course> courseList) {
        Map<String, Course> courseMap = new HashMap<>(courseList.size());
        courseList.forEach(d -> courseMap.put(d.getName(), d));
        return courseMap;
    }

    private Map<String, Chapter> transChapter(List<Chapter> chapterList) {
        Map<String, Chapter> chapterMap = new HashMap<>(chapterList.size());
        chapterList.forEach(d -> chapterMap.put(d.getName(), d));
        return chapterMap;
    }
}
