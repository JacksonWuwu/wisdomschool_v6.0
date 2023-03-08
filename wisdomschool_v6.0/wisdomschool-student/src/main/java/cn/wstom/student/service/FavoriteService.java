package cn.wstom.student.service;


import cn.wstom.student.entity.Favorite;
import cn.wstom.student.entity.PageVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 收藏记录
 *
 * @author dws
 */
public interface FavoriteService extends BaseService<Favorite> {
    //@Transactional(rollbackFor = Exception.class)
    boolean addFavorite(String userId, Integer infoType, String infoId);

    boolean checkFavoriteByUser(String userId, Integer infoType, String infoId);

    PageVo<Favorite> getFavoriteListPage(String userId, Integer infoType, String createTime, String orderby, String order, int pageNum, int rows);
}
