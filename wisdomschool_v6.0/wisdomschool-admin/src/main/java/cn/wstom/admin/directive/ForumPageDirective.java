package cn.wstom.admin.directive;

import cn.wstom.admin.entity.Info;
import cn.wstom.admin.entity.PageVo;
import cn.wstom.admin.service.SolrService;

import cn.wstom.admin.utils.JwtUtils;
import cn.wstom.admin.utils.ServletUtils;
import cn.wstom.common.constant.JwtConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/07
 */
@Component
public class ForumPageDirective extends BaseTemplateDirective {

    private final SolrService solrService;
    private static final String RESULT_KEY = "forumpage";

    @Autowired
    public ForumPageDirective(SolrService solrService) {
        this.solrService = solrService;
    }

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public String getName() {
        return "forumPage";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        // 获取页面的参数
        //问题id
        String title = handler.getString("title");
        //信息类型，0是全部，1问答，2文章，3分享
        Integer infoType = handler.getInteger("infoType");
        //按信息分类id查询
        String categoryId = handler.getString("categoryId");
        //需要排除id
        String notId = handler.getString("notId");
        //排序规则,recommend按推荐值排序，weight按权重值排序
        String orderBy = handler.getString("orderBy");
        String token = ServletUtils.getRequest().getHeader(JwtConstant.tokenHeader);
        String userId = jwtUtils.getUserIdFromToken(token);
        //当前页数
        int p = handler.getInteger("p", 0);
        //每页记录数
        int rows = handler.getInteger("rows", 10);

        // 获取文件的分页
        try {
            PageVo<Info> pageVo = solrService.searchInfo(title, userId, infoType, null, categoryId, notId, orderBy, p, rows);
            handler.put(RESULT_KEY, pageVo).render();
        } catch (Exception e) {
            handler.put(MSG, e.getMessage()).render();
        }
    }
}
