package cn.wstom.admin.directive;


import cn.wstom.admin.entity.ChapterResource;
import cn.wstom.admin.entity.ChapterResourceVo;
import cn.wstom.admin.entity.Recourse;
import cn.wstom.admin.service.ChapterResourceService;
import cn.wstom.admin.service.RecourseService;
import cn.wstom.admin.utils.JwtUtils;
import cn.wstom.admin.utils.ServletUtils;
import cn.wstom.common.constant.JwtConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author dws
 * @date 2019/03/21
 */
@Component
public class ChapterResourceDirective extends BaseTemplateDirective {
    private final ChapterResourceService chapterResourceService;
    private final RecourseService recourseService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    public ChapterResourceDirective(ChapterResourceService chapterResourceService, RecourseService recourseService) {
        this.chapterResourceService = chapterResourceService;
        this.recourseService = recourseService;
    }

    @Override
    public String getName() {
        return "chapterResource";
    }


    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String courseId = handler.getString("courseId");
        String chapterId = handler.getString("chapterId");
        String token = ServletUtils.getRequest().getHeader(JwtConstant.tokenHeader);
        String userId = jwtUtils.getUserIdFromToken(token);
        List<ChapterResource> chapterResources = chapterResourceService.selectByCidAndSid(userId, courseId, chapterId);
        List<String> rid = new LinkedList<>();
        chapterResources.forEach(c -> rid.add(c.getRid()));
        Map<String, Recourse> recourseMap = recourseService.mapByIds(rid);
        List<ChapterResourceVo> cr = new LinkedList<>();
        chapterResources.forEach(c -> {
            ChapterResourceVo crvo = new ChapterResourceVo();
            BeanUtils.copyProperties(c, crvo);
            crvo.setRecourse(recourseMap.get(c.getRid()));
            cr.add(crvo);
        });
        handler.put("chapterresources", cr).render();
    }
}
