package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.FavoriteService;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 收藏 服务层实现
 *
 * @author dws
 * @date 20190227
 */
@Service
public class FavoriteServiceImpl extends BaseServiceImpl<FavoriteMapper, Favorite>
        implements FavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;
//    private final QuestionService questionService;
//    private final ArticleService articleService;

    /*@Autowired
    public FavoriteServiceImpl(QuestionService questionService, ArticleService articleService) {
        this.questionService = questionService;
        this.articleService = articleService;
    }*/

    /**
     * 用户添加信息收藏
     *
     * @param userId
     * @param infoType
     * @param infoId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addFavorite(String userId, Integer infoType, String infoId) {
        /*if (infoType == ForumConstants.INFO_TYPE_QUESTION) {
            Question question = questionService.getById(infoId);
            Assert.notNull(question, "您收藏的信息不存在！");
        } else if (infoType == ForumConstants.INFO_TYPE_ARTICLE) {
            Article article = articleService.findArticleById(infoId);
            Assert.notNull(article, "您收藏的信息不存在！");
        } else {
            return false;
        }
        if (this.checkFavoriteByUser(userId, infoType, infoId)) {
            return true;
        } else {
            Favorite favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setInfoType(infoType);
            favorite.setInfoId(infoId);
            favorite.setCreateTime(new Date());
            return favoriteMapper.addFavorite(favorite) > 0;
        }*/
        return false;
    }

    /**
     * 查询收藏信息是否存在
     *
     * @param userId   用户id
     * @param infoType 信息类型id
     * @param infoId   收藏信息id
     * @return
     */
    @Override
    public boolean checkFavoriteByUser(String userId, Integer infoType, String infoId) {
        return favoriteMapper.checkFavoriteByUser(userId, infoType, infoId) > 0;
    }

    /**
     * 收藏翻页查询
     *
     * @param pageNum
     * @param rows
     * @return
     * @throws Exception
     */
    @Override
    public PageVo<Favorite> getFavoriteListPage(String userId, Integer infoType, String createTime, String orderby, String order, int pageNum, int rows) {
        PageVo<Favorite> pageVo = new PageVo<>(pageNum);
        pageVo.setRows(rows);
        if (orderby == null) {
            orderby = "a.score";
        }
        if (order == null) {
            order = "desc";
        }
        pageVo.setList(favoriteMapper.getFavoriteList(userId, infoType, createTime, orderby, order, pageVo.getOffset(), pageVo.getRows()));
        pageVo.setCount(favoriteMapper.getFavoriteCount(userId, infoType, createTime));
        return pageVo;
    }
}
