package cn.wstom.admin.directive;

import cn.wstom.admin.entity.Comment;
import cn.wstom.admin.entity.PageVo;
import cn.wstom.admin.service.ChapterService;
import cn.wstom.admin.utils.JwtUtils;
import cn.wstom.admin.utils.ServletUtils;
import cn.wstom.common.constant.JwtConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/12
 */
@Component
public class CourseCommentPageDirective extends BaseTemplateDirective {
    private final ChapterService chapterService;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    public CourseCommentPageDirective(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @Override
    public String getName() {
        return "courseCommentPage";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        // 获取页面的参数
        //教师课程id
        String courseId = handler.getString("courseId");
        //用户id
        String token = ServletUtils.getRequest().getHeader(JwtConstant.tokenHeader);
        String userId = jwtUtils.getUserIdFromToken(token);
        //添加时间
        String createTime = handler.getString("createTime");

        String orderBy = handler.getString("orderBy");

        String order = handler.getString("order");
        Integer userType = handler.getInteger("userType");

        //章节id
        String chapterId = handler.getString("chapterId");
        //当前页数
        int p = handler.getInteger("p", 1);
        //每页记录数
        int rows = handler.getInteger("rows", 10);
        // 获取文件的分页
        try {
            PageVo<Comment> commentPageVo = chapterService.getCourseCommentListPage(courseId, userId,
                    createTime, orderBy, order, chapterId, p, rows, userType);
            System.out.println(commentPageVo);
            handler.put("coursecommentpage", commentPageVo).render();
        } catch (Exception e) {
            e.printStackTrace();
            handler.put(MSG, e.getMessage()).render();
        }
    }
}
