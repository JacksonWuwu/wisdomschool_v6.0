package cn.wstom.face.feign;

import cn.wstom.face.config.FeignHttpsConfig;
import cn.wstom.face.entity.UserExam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "https://wisdomschool-exam-service",configuration = {FeignHttpsConfig.class})
public interface ExamService {


    @RequestMapping("/school/onlineExam/userExam/selectUserExamListBase")
    List<UserExam> selectUserExamListBase(@RequestBody UserExam userExam);

    @RequestMapping("/school/onlineExam/userExam/userExamList")
    List<Map<String, Object>>userExamList(@RequestBody UserExam userExam);


}
