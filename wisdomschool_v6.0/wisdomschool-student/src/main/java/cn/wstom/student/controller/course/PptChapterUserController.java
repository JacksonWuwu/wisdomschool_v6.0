package cn.wstom.student.controller.course;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.PptChapterUser;
import cn.wstom.student.service.PptChapterUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/chapter/ppt")
public class PptChapterUserController extends BaseController {

    @Autowired
    private PptChapterUserService pptChapterUserService;

    @ApiOperation("提交观看记录")
    @PostMapping(value = "/push")
    public Data push(@RequestBody String chapterResourceId) throws Exception {
        PptChapterUser pptChapterUser=new PptChapterUser();
        pptChapterUser.setResourceChapterId(chapterResourceId);
        pptChapterUser.setUserId(Integer.parseInt(getUserId()));
        System.out.println("pptChapterUser"+pptChapterUser);
        PptChapterUser index = pptChapterUserService.getBySidRid(pptChapterUser);
        if (index==null){
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            pptChapterUser.setId(uuid);
            pptChapterUser.setCout(1);
         return toAjax(pptChapterUserService.save(pptChapterUser));
        }
        int cout=index.getCout();
        index.setCout(++cout);
        System.out.println("videoUser3"+index);
        return toAjax(pptChapterUserService.update(index));
    }

}
