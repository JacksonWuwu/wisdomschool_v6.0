package cn.wstom.exam.feign;

import cn.wstom.exam.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "wisdomschool-student-service")
public interface StudentService {

    @RequestMapping(value = "/user/{studentId}")
    Student getStudentById(@PathVariable(value = "studentId")String studentId);

    @RequestMapping(value = "/user/studentList")
    List<Student> studentList(@RequestBody Student student);


}
