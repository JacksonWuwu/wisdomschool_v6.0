package cn.wstom.monitor.feign;

import cn.wstom.monitor.entity.UserExam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "wisdomschool-exam-service")
public interface ExamService {


    @RequestMapping("/school/onlineExam/userExam/selectUserExamListBase")
    List<UserExam> selectUserExamListBase(@RequestBody UserExam userExam);


}
