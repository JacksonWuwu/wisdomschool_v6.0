package cn.wstom.exam.service.impl;


import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.mapper.StuOptionanswerMapper;
import cn.wstom.exam.mapper.TestPaperMapper;
import cn.wstom.exam.mapper.UserTestMapper;
import cn.wstom.exam.service.StuOptionanswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 学生考试答案 服务层实现
 *
 * @author hzh
 * @date 20190304
 */
@Service
public class StuOptionanswerServiceImpl extends BaseServiceImpl<StuOptionanswerMapper, StuOptionanswer>
        implements StuOptionanswerService {

    @Autowired
    private StuOptionanswerMapper stuOptionanswerMapper;
    @Autowired
    private UserTestMapper userTestMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TestPaperMapper testPaperMapper;

    @Override
    public int updateByIdAns(StuOptionanswer stuOptionanswer) {
        return stuOptionanswerMapper.updateByIdAns(stuOptionanswer);
    }

    @Transactional
    @Override
    public int saveOptionAnswers(List<OptionSubmitVo> options, String studentId) {
        List<StuOptionanswer> optionanswers = new ArrayList<>();
        Map<String, StuOptionanswer> updateMap = new HashMap<>();

        SysUser sysUser = new SysUser();
        sysUser.setUserAttrId(studentId);
        SysUser studentUser = adminService.getUser(sysUser);
//        if (options.isEmpty()) {
//            throw new Exception();
//        }

        //  获取已记录的做题数据
        if (options.get(0) != null) {
            StuOptionanswer updateEntity = new StuOptionanswer();
            System.out.println(studentUser.toString());
            updateEntity.setCreateId(Integer.valueOf(studentUser.getId()));
            updateEntity.setPaperId(Integer.valueOf(options.get(0).getTestPaperId()));
            List<StuOptionanswer> updateList = stuOptionanswerMapper.selectList(updateEntity);
            updateList.forEach(ue -> {
                updateMap.put(ue.getTestpaperOptionanswer(), ue);
            });
        }


        options.forEach(option -> {
            //  动态计算可判断的题目分数
            StuOptionanswer optionanswer = new StuOptionanswer();
            //  若不存在则插入数据
            if (updateMap.get(option.getTestPaperQuestionId()) == null) {
                optionanswer.setId(option.getuUid());
                optionanswer.setCreateId(Integer.valueOf(studentUser.getId()));
                optionanswer.setPaperId(Integer.valueOf(option.getTestPaperId()));
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

        TestPaper testPaper = testPaperMapper.selectById(options.get(0).getTestPaperId());
        //  更新提交状态
        UserTest userTest = new UserTest();
        userTest.setTestPaperId(options.get(0).getTestPaperId());
        userTest.setUserId(studentUser.getId());
        List<UserTest> index = userTestMapper.selectListBase(userTest);
        if (index.isEmpty()) {
            userTest.setCreateId(studentUser.getId());
            userTest.setcId(testPaper.getCoursrId());
            userTestMapper.insert(userTest);
        }else {
            userTest.setId(index.get(0).getId());
        }
        userTest.setSumbitState(Constants.EXAM_SUBMIT_SCORE_DONE);

        if (!optionanswers.isEmpty()) {
            stuOptionanswerMapper.insertBatch(optionanswers);
        }

        return userTestMapper.update(userTest);
    }

    @Transactional
    @Override
    public int saveOptionAnswersByUserId(List<OptionSubmitVo> options, String userId) {
        List<StuOptionanswer> optionanswers = new ArrayList<>();
        Map<String, StuOptionanswer> updateMap = new HashMap<>();

        //  获取已记录的做题数据
        if (options.get(0) != null) {
            StuOptionanswer updateEntity = new StuOptionanswer();
            updateEntity.setCreateId(Integer.valueOf(userId));
            updateEntity.setPaperId(Integer.valueOf(options.get(0).getTestPaperId()));
            List<StuOptionanswer> updateList = stuOptionanswerMapper.selectList(updateEntity);
            updateList.forEach(ue -> {
                updateMap.put(ue.getTestpaperOptionanswer(), ue);
            });
        }


        options.forEach(option -> {
            //  动态计算可判断的题目分数
            StuOptionanswer optionanswer = new StuOptionanswer();
            //  若不存在则插入数据
            if (updateMap.get(option.getTestPaperQuestionId()) == null) {
                optionanswer.setId(option.getuUid());
                optionanswer.setCreateId(Integer.valueOf(userId));
                optionanswer.setPaperId(Integer.valueOf(option.getTestPaperId()));
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

        TestPaper testPaper = testPaperMapper.selectById(options.get(0).getTestPaperId());
        //  更新提交状态
        UserTest userTest = new UserTest();
        userTest.setTestPaperId(options.get(0).getTestPaperId());
        userTest.setUserId(userId);
        List<UserTest> index = userTestMapper.selectListBase(userTest);
        if (index.isEmpty()) {
            userTest.setCreateId(userId);
            userTest.setcId(testPaper.getCoursrId());
            userTestMapper.insert(userTest);
        } else {
            userTest.setId(index.get(0).getId());
        }
        userTest.setSumbitState(Constants.EXAM_SUBMIT_SCORE_DONE);

        if (!optionanswers.isEmpty()) {
            stuOptionanswerMapper.insertBatch(optionanswers);
        }

        return userTestMapper.update(userTest);
    }


    @Transactional
    @Override
    public int updateListAndTotalScore(List<StuOptionanswer> optionanswers, String testPaperId, String userId) {

        AtomicInteger totalScore = new AtomicInteger();
        try {
            //  更新题目得分
            optionanswers.forEach(o -> {
                stuOptionanswerMapper.update(o);
                totalScore.addAndGet(o.getQuestionScore());
            });
            UserTest userTest = new UserTest();

            //  更新总分
            userTest.setTestPaperId(testPaperId);
            userTest.setUserId(userId);
            UserTest index = userTestMapper.selectListBase(userTest).get(0);

            userTest.setStuScore(String.valueOf(totalScore));
            userTest.setMadeScore(Constants.EXAM_MADE_SCORE_DONE);
            userTest.setId(index.getId());
            userTestMapper.update(userTest);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
