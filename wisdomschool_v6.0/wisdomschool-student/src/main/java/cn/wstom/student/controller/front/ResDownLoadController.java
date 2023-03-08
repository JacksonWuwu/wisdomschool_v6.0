package cn.wstom.student.controller.front;

import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.AdjunctStudent;
import cn.wstom.student.entity.Recourse;
import cn.wstom.student.utils.AtomicIntegerUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 *  资源下载重定向
 * @author liniec
 * @version 2019/09/30 13:18
 */
@Controller
@RequestMapping("/resource")
@Slf4j
public class ResDownLoadController extends BaseController {

    @Value("${wstom.storageContextPath}")
    private String STORAGE_URL;

    @Override
    @RequestMapping("/download/{resourceId}")
    public String redirect(@PathVariable String resourceId) {
        //  下载次数的更新
        Recourse recourse = adminService.getRecourseByStuId(resourceId).get(0);
        if (!AtomicIntegerUtil.isEmplyInMap(recourse.getClass(), recourse.getId())) {
            AtomicIntegerUtil.getInstance(recourse.getClass(), recourse.getId(), Long.valueOf(recourse.getCount()));
        }
        recourse.setCount(AtomicIntegerUtil.getInstance(recourse.getClass(), recourse.getId()).getAndIncrement());
        return "redirect:" + STORAGE_URL + "/downloadFile?fileId=" + resourceId;
    }

    @RequestMapping("/downloadadjunct/{resourceId}/{uid}/{fileName}")
    public String redirectAdjunct(@PathVariable String resourceId, @PathVariable String uid,@PathVariable String fileName) {
        String url=STORAGE_URL + "/downloadadjunct?fileId=" + resourceId+"&fileName="+fileName+"&uid="+uid;
        RestTemplate client = new RestTemplate();
        String body = client.getForEntity(url, String.class).getBody();
        return body;
    }

    @RequestMapping("/deleteadjunct")
    @ResponseBody
    public String deleteAdjunct(String fileId,String uid,String fileName){
        String url=STORAGE_URL + "/deleteadjunct?fileId=" + fileId+"&fileName="+fileName+"&uid="+uid;
        RestTemplate client = new RestTemplate();
        String body = client.getForEntity(url, String.class).getBody();
        AdjunctStudent adjunctStudent=new AdjunctStudent();
        adjunctStudent.setFilename(null);
        adjunctStudent.setAdjid(fileId);
        adjunctStudent.setStuid(uid);
        adjunctStudent.setStates(0);
        adminService.updateByAidAndSid(adjunctStudent);
        System.out.println(body);
        Map<String,String> map=new HashMap<String,String>();
        map.put("sucess","true");

        return JSON.toJSONString(map);
    }




    @RequestMapping("/downloadtwo/{resourceId}")
    public String redirecttwo(@PathVariable String resourceId) {
        //  下载次数的更新
        Recourse recourse = adminService.getRecourseByStuId(resourceId).get(0);
        if (!AtomicIntegerUtil.isEmplyInMap(recourse.getClass(), recourse.getId())) {
            AtomicIntegerUtil.getInstance(recourse.getClass(), recourse.getId(), Long.valueOf(recourse.getCount()));
        }
        recourse.setCount(AtomicIntegerUtil.getInstance(recourse.getClass(), recourse.getId()).getAndIncrement());
        return "redirect:" + STORAGE_URL + "/downloadFileTwo?fileId=" + resourceId;
    }
}
