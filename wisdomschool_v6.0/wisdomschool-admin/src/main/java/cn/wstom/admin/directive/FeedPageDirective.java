package cn.wstom.admin.directive;


import cn.wstom.admin.entity.Feed;
import cn.wstom.admin.entity.PageVo;
import cn.wstom.admin.service.FeedService;
import cn.wstom.admin.utils.JwtUtils;
import cn.wstom.admin.utils.ServletUtils;
import cn.wstom.common.constant.JwtConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/09
 */
@Component
public class FeedPageDirective extends BaseTemplateDirective {
    private final FeedService feedService;
    private static final String RESULT_KEY = "feed";

    @Autowired
    public FeedPageDirective(FeedService feedService) {
        this.feedService = feedService;
    }

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public String getName() {
        return "feedPage";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        // 获取页面的参数
        //所属主信息类型，0是所有，1是文章，2是小组话题

        String token = ServletUtils.getRequest().getHeader(JwtConstant.tokenHeader);
        String userId = jwtUtils.getUserIdFromToken(token);
        Integer infoType = handler.getInteger("infoType", 0);
        //翻页页数
        Integer p = handler.getInteger("p", 1);
        //每页记录条数
        Integer rows = handler.getInteger("rows", 10);
        // 获取文件的分页
        try {
            PageVo<Feed> pageVo = feedService.getUserListFeedPage(userId, infoType, p, rows);
            System.out.println(pageVo.getList());
            handler.put(RESULT_KEY, pageVo).render();
        } catch (Exception e) {
            e.printStackTrace();
            handler.put(MSG, e.getMessage()).render();
        }
    }
}
