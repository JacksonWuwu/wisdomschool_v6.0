package cn.wstom.exam.service.impl;



import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.mapper.*;
import cn.wstom.exam.service.StuOptionExamanswerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 学生考试答案 服务层实现
 *
 * @author hzh
 * @date 20190304
 */
@Service
public class StuOptionExamanswerServiceImpl extends BaseServiceImpl<StuOptionExamanswerMapper, StuOptionExamanswer>
        implements StuOptionExamanswerService {

    @Autowired
    private StuOptionExamanswerMapper stuOptionanswerMapper;
    @Autowired
    private UserExamMapper userExamMapper;
    @Autowired
    private UserPaperWorkMapper userPaperWorkMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TestPaperOneMapper testPaperOneMapper;
    @Autowired
    private TestPaperWorkMapper testPaperWorkMapper;
    @Autowired
    private TestpaperOptinanswerMapper testpaperOptinanswerMapper;
    @Autowired
    private TestpaperQuestionsMapper testpaperQuestionsMapper;

    @Override
    public int updateByIdAns(StuOptionExamanswer stuOptionanswer) {
        return stuOptionanswerMapper.updateByIdAns(stuOptionanswer);
    }

    @Transactional
    @Override
    public int saveOptionAnswers(List<OptionExamSubmitVo> options, String studentId) {
        List<StuOptionExamanswer> optionanswers = new ArrayList<>();
        Map<String, StuOptionExamanswer> updateMap = new HashMap<>();
         SysUser sysUser=new SysUser();
         sysUser.setUserAttrId(studentId);
        SysUser studentUser = adminService.getUser(sysUser);

//        if (options.isEmpty()) {
//            throw new Exception();
//        }

        //  获取已记录的做题数据
        if (options.get(0) != null) {
            StuOptionExamanswer updateEntity = new StuOptionExamanswer();
            System.out.println(studentUser.toString());
            updateEntity.setCreateId(Integer.valueOf(studentUser.getId()));
            updateEntity.setPaperOneId(Integer.valueOf(options.get(0).getTestPaperOneId()));
            List<StuOptionExamanswer> updateList = stuOptionanswerMapper.selectList(updateEntity);
            updateList.forEach(ue -> {
                updateMap.put(ue.getTestpaperOptionanswer(), ue);
            });
        }


        options.forEach(option -> {
            //  动态计算可判断的题目分数
            StuOptionExamanswer optionanswer = new StuOptionExamanswer();
            //  若不存在则插入数据
            if (updateMap.get(option.getTestPaperQuestionId()) == null) {
                optionanswer.setId(option.getuUid());
                optionanswer.setCreateId(Integer.valueOf(studentUser.getId()));
                optionanswer.setPaperOneId(Integer.valueOf(option.getTestPaperOneId()));
                optionanswer.setStuAnswer(option.getStuAnswer());
                optionanswer.setTestpaperOptionanswer(option.getTestPaperQuestionId());//   题目Id
                optionanswer.setStoption(option.getStuOptionAnswer());
                optionanswer.setQuestionScore(option.getScore());
                optionanswers.add(optionanswer);
            } else {
                // 若存在则更新数据
                optionanswer = updateMap.get(option.getTestPaperQuestionId());
                optionanswer.setStuAnswer(option.getStuAnswer());
                optionanswer.setStoption(option.getStuOptionAnswer());
                optionanswer.setQuestionScore(option.getScore());
                stuOptionanswerMapper.update(optionanswer);
            }
        });

        TestPaperOne testPaper = testPaperOneMapper.selectById(options.get(0).getTestPaperOneId());
        //  更新提交状态
        UserExam userExam = new UserExam();
        userExam.setTestPaperOneId(options.get(0).getTestPaperOneId());
        userExam.setUserId(studentUser.getId());
        List<UserExam> index = userExamMapper.selectListBase(userExam);
        if (index.isEmpty()) {
            userExam.setCreateId(studentUser.getId());
            userExam.setcId(testPaper.getCoursrId());
            userExamMapper.insert(userExam);
        }else {
            userExam.setId(index.get(0).getId());
        }
        userExam.setSumbitState(Constants.EXAM_SUBMIT_SCORE_DONE);

        if (!optionanswers.isEmpty()) {
            stuOptionanswerMapper.insertBatch(optionanswers);
        }

        return userExamMapper.update(userExam);
    }
    @Transactional
    @Override
    public int saveOptionAnswersByUserId(List<OptionExamSubmitVo> options, String userId) {
        List<StuOptionExamanswer> optionanswers = new ArrayList<>();
        Map<String, StuOptionExamanswer> updateMap = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        //  获取已记录的做题数据
        if (options.get(0) != null&&options.size()>0) {
            StuOptionExamanswer updateEntity = new StuOptionExamanswer();
            updateEntity.setCreateId(Integer.valueOf(userId));
            updateEntity.setPaperOneId(Integer.valueOf(options.get(0).getTestPaperOneId()));
            //筛选出学生记录的做题答案

            List<StuOptionExamanswer> updateList = stuOptionanswerMapper.selectList(updateEntity);
            updateList.forEach(ue -> {
                updateMap.put(ue.getTestpaperOptionanswer(), ue);
            });
        }
        options.forEach(option -> {
            //  动态计算可判断的题目分数
            StuOptionExamanswer optionanswer = new StuOptionExamanswer();
            //  若不存在则插入数据
            if (updateMap.get(option.getTestPaperQuestionId()) == null) {
                optionanswer.setId(option.getuUid());
                optionanswer.setCreateId(Integer.valueOf(userId));
                optionanswer.setPaperOneId(Integer.valueOf(option.getTestPaperOneId()));
                optionanswer.setStuAnswer(option.getStuAnswer());
                optionanswer.setTestpaperOptionanswer(option.getTestPaperQuestionId());//   题目Id
                optionanswer.setStoption(option.getStuOptionAnswer());
                String stuAnswer = option.getStuAnswer(); //
                String[] stuAnswerList = stuAnswer.split(";");
                String sanswer = "0";//学生答案
                String answer = "0";//试卷答案
                //筛选出学生选择的选项
                for (int i = 0; i < stuAnswerList.length; i++) {
                    if (!stuAnswerList[i].equals("0")){
                        sanswer = stuAnswerList[i];
                    }
                }
                TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                testpaperOptinanswer.setQuestionId(option.getTestPaperQuestionId());
                List<TestpaperOptinanswer> testpaperOptinanswerList = testpaperOptinanswerMapper.selectList(testpaperOptinanswer);
                for (int i = 0; i < testpaperOptinanswerList.size(); i++) {
                    if (!testpaperOptinanswerList.get(i).getStanswer().equals("0")){
                        answer = testpaperOptinanswerList.get(i).getStanswer();
                    }
                }

                TestpaperQuestions testpaperQuestions = testpaperQuestionsMapper.selectById(option.getTestPaperQuestionId());
                if (sanswer.equals(answer)){
                    optionanswer.setQuestionScore(testpaperQuestions.getQuestionScore());
                    list.add(testpaperQuestions.getQuestionScore());
                }else{
                    optionanswer.setQuestionScore(0);
                    list.add(0);
                }

                optionanswers.add(optionanswer);
            } else {
                // 若存在则更新数据
                optionanswer = updateMap.get(option.getTestPaperQuestionId());
                String stuAnswer = option.getStuAnswer();
                String[] stuAnswerList = stuAnswer.split(";");
                String sanswer = "0";//学生答案
                String answer = "0";//试卷答案
                for (int i = 0; i < stuAnswerList.length; i++) {
                    if (!stuAnswerList[i].equals("0")){
                        sanswer = stuAnswerList[i];
                    }
                }
                TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                testpaperOptinanswer.setQuestionId(option.getTestPaperQuestionId());
                List<TestpaperOptinanswer> testpaperOptinanswerList = testpaperOptinanswerMapper.selectList(testpaperOptinanswer);
                for (int i = 0; i < testpaperOptinanswerList.size(); i++) {
                    if (!testpaperOptinanswerList.get(i).getStanswer().equals("0")){
                        answer = testpaperOptinanswerList.get(i).getStanswer();
                    }
                }
                TestpaperQuestions testpaperQuestions = testpaperQuestionsMapper.selectById(option.getTestPaperQuestionId());
                if (sanswer.equals(answer)){
                    optionanswer.setQuestionScore(testpaperQuestions.getQuestionScore());
                    list.add(testpaperQuestions.getQuestionScore());
                }else{
                    optionanswer.setQuestionScore(0);
                    list.add(0);
                }
                optionanswer.setStuAnswer(option.getStuAnswer());
                optionanswer.setStoption(option.getStuOptionAnswer());
                stuOptionanswerMapper.update(optionanswer);
            }
        });

        int scoreAll = 0;
        for (int i = 0; i < list.size(); i++) {
            scoreAll = scoreAll + list.get(i);
        }
        TestPaperOne testPaper = testPaperOneMapper.selectById(options.get(0).getTestPaperOneId());
        //  更新提交状态
        UserExam userExam = new UserExam();
        userExam.setTestPaperOneId(options.get(0).getTestPaperOneId());
        userExam.setUserId(userId);
        List<UserExam> index = userExamMapper.selectListBase(userExam);
        if (index.isEmpty()) {
            userExam.setCreateId(userId);
            userExam.setcId(testPaper.getCoursrId());
            userExamMapper.insert(userExam);
        } else {
            userExam.setId(index.get(0).getId());
        }
        userExam.setStuScore(String.valueOf(scoreAll));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date time = new Date();
//        String endTime = simpleDateFormat.format(time);
//        userExam.setStuEndTime(endTime);
        if (!optionanswers.isEmpty()) {
            stuOptionanswerMapper.insertBatch(optionanswers);
        }

        return userExamMapper.update(userExam);
    }
    @Transactional
    @Override
    public int submitOptionAnswersByUserId(List<OptionExamSubmitVo> options, String userId) {
        List<StuOptionExamanswer> optionanswers = new ArrayList<>();
        Map<String, StuOptionExamanswer> updateMap = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        int scoreAll = 0;

        //  获取已记录的做题数据
        if (options.get(0) != null) {
            StuOptionExamanswer updateEntity = new StuOptionExamanswer();
            updateEntity.setCreateId(Integer.valueOf(userId));
            updateEntity.setPaperOneId(Integer.valueOf(options.get(0).getTestPaperOneId()));
            List<StuOptionExamanswer> updateList = stuOptionanswerMapper.selectList(updateEntity);
            updateList.forEach(ue -> {
                updateMap.put(ue.getTestpaperOptionanswer(), ue);
            });
        }
        try{
            options.forEach(option -> {
                //  动态计算可判断的题目分数
                StuOptionExamanswer optionanswer = new StuOptionExamanswer();
                //  若不存在则插入数据
                if (updateMap.get(option.getTestPaperQuestionId()) == null) {
                    optionanswer.setId(option.getuUid());
                    optionanswer.setCreateId(Integer.valueOf(userId));
                    optionanswer.setPaperOneId(Integer.valueOf(option.getTestPaperOneId()));
                    optionanswer.setStuAnswer(option.getStuAnswer());
                    optionanswer.setTestpaperOptionanswer(option.getTestPaperQuestionId());//   题目Id
                    optionanswer.setStoption(option.getStuOptionAnswer());
                    String stuAnswer = option.getStuAnswer();
                    String[] stuAnswerList = stuAnswer.split(";");
                    String sanswer = "0";//学生答案
                    String answer = "0";//试卷答案
                    //找出学生的选项
                    for (int i = 0; i < stuAnswerList.length; i++) {
                        if (!stuAnswerList[i].equals("0")){
                            sanswer = stuAnswerList[i];
                        }
                    }
                    TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                    testpaperOptinanswer.setQuestionId(option.getTestPaperQuestionId());
                    List<TestpaperOptinanswer> testpaperOptinanswerList = testpaperOptinanswerMapper.selectList(testpaperOptinanswer);
                    for (int i = 0; i < testpaperOptinanswerList.size(); i++) {
                        if (!testpaperOptinanswerList.get(i).getStanswer().equals("0")){
                            answer = testpaperOptinanswerList.get(i).getStanswer();
                        }
                    }
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsMapper.selectById(option.getTestPaperQuestionId());
                    if (sanswer.equals(answer)){
                        optionanswer.setQuestionScore(testpaperQuestions.getQuestionScore());
                        list.add(testpaperQuestions.getQuestionScore());
                    }else{
                        optionanswer.setQuestionScore(0);
                        list.add(0);
                    }

                    optionanswers.add(optionanswer);
                } else {
                    // 若存在则更新数据
                    optionanswer = updateMap.get(option.getTestPaperQuestionId());
                    String stuAnswer = option.getStuAnswer();
                    String[] stuAnswerList = stuAnswer.split(";");
                    String sanswer = "0";//学生答案
                    String answer = "0";//试卷答案
                    for (int i = 0; i < stuAnswerList.length; i++) {
                        if (!stuAnswerList[i].equals("0")){
                            sanswer = stuAnswerList[i];
                        }
                    }
                    TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                    testpaperOptinanswer.setQuestionId(option.getTestPaperQuestionId());
                    List<TestpaperOptinanswer> testpaperOptinanswerList = testpaperOptinanswerMapper.selectList(testpaperOptinanswer);
                    for (int i = 0; i < testpaperOptinanswerList.size(); i++) {
                        if (!testpaperOptinanswerList.get(i).getStanswer().equals("0")){
                            answer = testpaperOptinanswerList.get(i).getStanswer();
                        }
                    }
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsMapper.selectById(option.getTestPaperQuestionId());
                    if (sanswer.equals(answer)){
                        optionanswer.setQuestionScore(testpaperQuestions.getQuestionScore());
                        list.add(testpaperQuestions.getQuestionScore());
                    }else{
                        optionanswer.setQuestionScore(0);
                        list.add(0);
                    }
                    optionanswer.setStuAnswer(option.getStuAnswer());
                    optionanswer.setStoption(option.getStuOptionAnswer());
                    stuOptionanswerMapper.update(optionanswer);
                }
            });
            for (int i = 0; i < list.size(); i++) {
                scoreAll = scoreAll + list.get(i);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        TestPaperOne testPaper = testPaperOneMapper.selectById(options.get(0).getTestPaperOneId());
        //  更新提交状态
        UserExam userExam = new UserExam();
        userExam.setTestPaperOneId(options.get(0).getTestPaperOneId());
        userExam.setUserId(userId);
        List<UserExam> index = userExamMapper.selectListBase(userExam);
        if (index.isEmpty()) {
            userExam.setCreateId(userId);
            userExam.setcId(testPaper.getCoursrId());
            userExamMapper.insert(userExam);
        } else {
            userExam.setId(index.get(0).getId());
        }
        userExam.setSumbitState("1");
        userExam.setStuScore(String.valueOf(scoreAll));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String endTime = simpleDateFormat.format(time);
        userExam.setStuEndTime(endTime);
        if (!optionanswers.isEmpty()) {
            stuOptionanswerMapper.insertBatch(optionanswers);
        }

        return userExamMapper.update(userExam);
    }


    /**
     * 自动提交
     * @param options
     * @param userId
     * @return
     */
    @Override
    public int autoSubmitOptionAnswersByUserId(List<OptionExamSubmitVo> options, String userId) {
        List<StuOptionExamanswer> optionanswers = new ArrayList<>();
        Map<String, StuOptionExamanswer> updateMap = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        int scoreAll = 0;
        //  获取已记录的做题数据
        if (options.get(0) != null) {
            StuOptionExamanswer updateEntity = new StuOptionExamanswer();
            updateEntity.setCreateId(Integer.valueOf(userId));
            updateEntity.setPaperOneId(Integer.valueOf(options.get(0).getTestPaperOneId()));
            List<StuOptionExamanswer> updateList = stuOptionanswerMapper.selectList(updateEntity);
            updateList.forEach(ue -> {
                updateMap.put(ue.getTestpaperOptionanswer(), ue);
            });
        }
        try{
            options.forEach(option -> {
                //  动态计算可判断的题目分数
                StuOptionExamanswer optionanswer = new StuOptionExamanswer();
                //  若不存在则插入数据
                if (updateMap.get(option.getTestPaperQuestionId()) == null) {
                    optionanswer.setId(option.getuUid());
                    optionanswer.setCreateId(Integer.valueOf(userId));
                    optionanswer.setPaperOneId(Integer.valueOf(option.getTestPaperOneId()));
                    optionanswer.setStuAnswer(option.getStuAnswer());
                    optionanswer.setTestpaperOptionanswer(option.getTestPaperQuestionId());//   题目Id
                    optionanswer.setStoption(option.getStuOptionAnswer());
                    String stuAnswer = option.getStuAnswer();
                    String[] stuAnswerList = stuAnswer.split(";");
                    String sanswer = "0";//学生答案
                    String answer = "0";//试卷答案
                    for (int i = 0; i < stuAnswerList.length; i++) {
                        if (!stuAnswerList[i].equals("0")){
                            sanswer = stuAnswerList[i];
                        }
                    }
                    TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                    testpaperOptinanswer.setQuestionId(option.getTestPaperQuestionId());
                    List<TestpaperOptinanswer> testpaperOptinanswerList = testpaperOptinanswerMapper.selectList(testpaperOptinanswer);
                    for (int i = 0; i < testpaperOptinanswerList.size(); i++) {
                        if (!testpaperOptinanswerList.get(i).getStanswer().equals("0")){
                            answer = testpaperOptinanswerList.get(i).getStanswer();
                        }
                    }
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsMapper.selectById(option.getTestPaperQuestionId());
                    if (sanswer.equals(answer)){
                        optionanswer.setQuestionScore(testpaperQuestions.getQuestionScore());
                        list.add(testpaperQuestions.getQuestionScore());
                    }else{
                        optionanswer.setQuestionScore(0);
                        list.add(0);
                    }

                    optionanswers.add(optionanswer);
                } else {
                    // 若存在则更新数据
                    optionanswer = updateMap.get(option.getTestPaperQuestionId());
                    String stuAnswer = option.getStuAnswer();
                    String[] stuAnswerList = stuAnswer.split(";");
                    String sanswer = "0";//学生答案
                    String answer = "0";//试卷答案
                    for (int i = 0; i < stuAnswerList.length; i++) {
                        if (!stuAnswerList[i].equals("0")){
                            sanswer = stuAnswerList[i];
                        }
                    }
                    TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                    testpaperOptinanswer.setQuestionId(option.getTestPaperQuestionId());
                    List<TestpaperOptinanswer> testpaperOptinanswerList = testpaperOptinanswerMapper.selectList(testpaperOptinanswer);
                    for (int i = 0; i < testpaperOptinanswerList.size(); i++) {
                        if (!testpaperOptinanswerList.get(i).getStanswer().equals("0")){
                            answer = testpaperOptinanswerList.get(i).getStanswer();
                        }
                    }
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsMapper.selectById(option.getTestPaperQuestionId());
                    if (sanswer.equals(answer)){
                        optionanswer.setQuestionScore(testpaperQuestions.getQuestionScore());
                        list.add(testpaperQuestions.getQuestionScore());
                    }else{
                        optionanswer.setQuestionScore(0);
                        list.add(0);
                    }
                    optionanswer.setStuAnswer(option.getStuAnswer());
                    optionanswer.setStoption(option.getStuOptionAnswer());
                    stuOptionanswerMapper.update(optionanswer);
                }
            });
            for (int i = 0; i < list.size(); i++) {
                scoreAll = scoreAll + list.get(i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(scoreAll);
        TestPaperOne testPaper = testPaperOneMapper.selectById(options.get(0).getTestPaperOneId());
        //  更新提交状态
        UserExam userExam = new UserExam();
        userExam.setTestPaperOneId(options.get(0).getTestPaperOneId());
        userExam.setUserId(userId);
        List<UserExam> index = userExamMapper.selectListBase(userExam);
        if (index.isEmpty()) {
            userExam.setCreateId(userId);
            userExam.setcId(testPaper.getCoursrId());
            userExamMapper.insert(userExam);
        } else {
            userExam.setId(index.get(0).getId());
        }
        userExam.setSumbitState("2");

        userExam.setStuScore(String.valueOf(scoreAll));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String endTime = simpleDateFormat.format(time);
        userExam.setStuEndTime(endTime);
        if (!optionanswers.isEmpty()) {
            stuOptionanswerMapper.insertBatch(optionanswers);
        }

        return userExamMapper.update(userExam);
    }

    @Transactional
    @Override
    public int submitPaperWorkOptionAnswersByUserId(List<OptionExamSubmitVo> options, String userId) {
        List<StuOptionExamanswer> optionanswers = new ArrayList<>();
        Map<String, StuOptionExamanswer> updateMap = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        //  获取已记录的做题数据
        if (options.get(0) != null) {
            StuOptionExamanswer updateEntity = new StuOptionExamanswer();
            updateEntity.setCreateId(Integer.valueOf(userId));
            updateEntity.setPaperOneId(Integer.valueOf(options.get(0).getTestPaperOneId()));
            List<StuOptionExamanswer> updateList = stuOptionanswerMapper.selectList(updateEntity);
            updateList.forEach(ue -> {
                updateMap.put(ue.getTestpaperOptionanswer(), ue);
            });
        }
        options.forEach(option -> {
            //  动态计算可判断的题目分数
            StuOptionExamanswer optionanswer = new StuOptionExamanswer();
            //  若不存在则插入数据
            if (updateMap.get(option.getTestPaperQuestionId()) == null) {
                optionanswer.setId(option.getuUid());
                optionanswer.setCreateId(Integer.valueOf(userId));
                optionanswer.setPaperOneId(Integer.valueOf(option.getTestPaperOneId()));
                optionanswer.setStuAnswer(option.getStuAnswer());
                optionanswer.setTestpaperOptionanswer(option.getTestPaperQuestionId());//   题目Id
                optionanswer.setStoption(option.getStuOptionAnswer());
                String stuAnswer = option.getStuAnswer();
                String[] stuAnswerList = stuAnswer.split(";");
                String sanswer = "0";//学生答案
                String answer = "0";//试卷答案
                for (int i = 0; i < stuAnswerList.length; i++) {
                    if (!stuAnswerList[i].equals("0")){
                        sanswer = stuAnswerList[i];
                    }
                }
                TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                testpaperOptinanswer.setQuestionId(option.getTestPaperQuestionId());
                List<TestpaperOptinanswer> testpaperOptinanswerList = testpaperOptinanswerMapper.selectList(testpaperOptinanswer);
                for (int i = 0; i < testpaperOptinanswerList.size(); i++) {
                    if (!testpaperOptinanswerList.get(i).getStanswer().equals("0")){
                        answer = testpaperOptinanswerList.get(i).getStanswer();
                    }
                }
                TestpaperQuestions testpaperQuestions = testpaperQuestionsMapper.selectById(option.getTestPaperQuestionId());
                if (sanswer.equals(answer)){
                    optionanswer.setQuestionScore(testpaperQuestions.getQuestionScore());
                    list.add(testpaperQuestions.getQuestionScore());
                }else{
                    optionanswer.setQuestionScore(0);
                    list.add(0);
                }

                optionanswers.add(optionanswer);
            } else {
                // 若存在则更新数据
                optionanswer = updateMap.get(option.getTestPaperQuestionId());
                String stuAnswer = option.getStuAnswer();
                String[] stuAnswerList = stuAnswer.split(";");
                String sanswer = "0";//学生答案
                String answer = "0";//试卷答案
                for (int i = 0; i < stuAnswerList.length; i++) {
                    if (!stuAnswerList[i].equals("0")){
                        sanswer = stuAnswerList[i];
                    }
                }
                TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                testpaperOptinanswer.setQuestionId(option.getTestPaperQuestionId());
                List<TestpaperOptinanswer> testpaperOptinanswerList = testpaperOptinanswerMapper.selectList(testpaperOptinanswer);
                for (int i = 0; i < testpaperOptinanswerList.size(); i++) {
                    if (!testpaperOptinanswerList.get(i).getStanswer().equals("0")){
                        answer = testpaperOptinanswerList.get(i).getStanswer();
                    }
                }
                TestpaperQuestions testpaperQuestions = testpaperQuestionsMapper.selectById(option.getTestPaperQuestionId());
                if (sanswer.equals(answer)){
                    optionanswer.setQuestionScore(testpaperQuestions.getQuestionScore());
                    list.add(testpaperQuestions.getQuestionScore());
                }else{
                    optionanswer.setQuestionScore(0);
                    list.add(0);
                }
                optionanswer.setStuAnswer(option.getStuAnswer());
                optionanswer.setStoption(option.getStuOptionAnswer());
                stuOptionanswerMapper.update(optionanswer);
            }
        });
        int scoreAll = 0;
        for (int i = 0; i < list.size(); i++) {
            scoreAll = scoreAll + list.get(i);
        }
        System.out.println(scoreAll);

        TestPaperWork testPaper = testPaperWorkMapper.selectById(options.get(0).getTestPaperOneId());
        //  更新提交状态
        UserPaperWork userExam = new UserPaperWork();
        userExam.setTestPaperWorkId(options.get(0).getTestPaperOneId());
        userExam.setUserId(userId);
        List<UserPaperWork> index = userPaperWorkMapper.selectListBase(userExam);
        if (index.isEmpty()) {
            userExam.setCreateId(userId);
            userExam.setcId(testPaper.getCoursrId());
            userPaperWorkMapper.insert(userExam);
        } else {
            userExam.setId(index.get(0).getId());
        }

        userExam.setSumbitState("1");
        userExam.setStuScore(String.valueOf(scoreAll));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String endTime = simpleDateFormat.format(time);
        userExam.setStuEndTime(endTime);
        if (!optionanswers.isEmpty()) {

            stuOptionanswerMapper.insertBatch(optionanswers);
        }


        return userPaperWorkMapper.update(userExam);
    }

    @Override
    public int updateStuAnswer(StuOptionExamanswer stuOptionanswer) {
        return stuOptionanswerMapper.updateStuAnswer(stuOptionanswer);
    }

    @Override
    public int updateAnswerType(StuOptionExamanswer stuOptionanswer) {
        return stuOptionanswerMapper.updateAnswerType(stuOptionanswer);
    }

    @Override
    public StuOptionExamanswer selectByCreatId(@Param("paperOneId")String paperOneId,@Param("createId") String createId, @Param("stoption")String stoption) {
        return stuOptionanswerMapper.selectByCreatId(paperOneId, createId, stoption);
    }

    @Override
    public List<StuOptionExamanswer> selectByStudentAnswer(@Param("paperOneId")String paperOneId,@Param("createId") String createId) {
        return stuOptionanswerMapper.selectByStudentAnswer(paperOneId,createId);
    }


    @Transactional
    @Override
    public int updateListAndTotalScore(List<StuOptionExamanswer> optionanswers, String testPaperOneId, String userId) {

        AtomicInteger totalScore = new AtomicInteger();
        try {
            //  更新题目得分
            optionanswers.forEach(o -> {
                System.out.println(o);
                stuOptionanswerMapper.update(o);
                System.out.println("stuOptionanswerMapper.update(o)"+stuOptionanswerMapper.update(o));
                totalScore.addAndGet(o.getQuestionScore());
            });
            UserExam userExam = new UserExam();

            //  更新总分

            userExam.setTestPaperOneId(testPaperOneId);
            userExam.setUserId(userId);
            if (userExamMapper.selectListBase(userExam)!=null&&userExamMapper.selectListBase(userExam).size()>0){
            UserExam index = userExamMapper.selectListBase(userExam).get(0);
            userExam.setStuScore(String.valueOf(totalScore));
            userExam.setMadeScore(Constants.EXAM_MADE_SCORE_DONE);
            userExam.setId(index.getId());
            userExamMapper.update(userExam);}
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
