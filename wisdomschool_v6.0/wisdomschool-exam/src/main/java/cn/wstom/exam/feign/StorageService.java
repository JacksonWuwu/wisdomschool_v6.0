package cn.wstom.exam.feign;

import cn.wstom.exam.constants.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "wisdomschool-storage-service")
public interface StorageService {
    @RequestMapping("/storage/upload")
    Data upload(@RequestParam("userId") String userId,
                @RequestParam("folder")String folder,
                @RequestParam("originalFileName")String originalFileName,
                @RequestBody MultipartFile file);
    //@RequestMapping("/storage/upload")
    //Data upload(@RequestBody MultiValueMap<String, Object> params);
}
