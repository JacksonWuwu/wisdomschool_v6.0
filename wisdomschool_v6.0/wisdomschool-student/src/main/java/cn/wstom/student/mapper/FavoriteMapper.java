package cn.wstom.student.mapper;



import cn.wstom.student.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏 数据层
 *
 * @author dws
 * @date 20190227
 */
@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {

    /**
     * 添加收藏信息
     *
     * @param favorite
     * @return
     */
    int addFavorite(Favorite favorite);

    /**
     * 按id删除收藏信息
     *
     * @param userId
     * @param infoType
     * @param infoId
     * @return
     */
    int deleteFavoriteById(@Param("userId") String userId, @Param("infoType") Integer infoType, @Param("infoId") String infoId);

    Favorite findFavoriteById(@Param("id") String id);

    /**
     * 查询收藏信息是否存在
     *
     * @param userId   用户id
     * @param infoType 信息类型id
     * @param infoId   收藏信息id
     * @return
     */
    int checkFavoriteByUser(@Param("userId") String userId, @Param("infoType") Integer infoType, @Param("infoId") String infoId);

    /**
     * 查询收藏总数
     *
     * @param userId
     * @param infoType
     * @param createTime
     * @return
     */
    int getFavoriteCount(@Param("userId") String userId,
                         @Param("infoType") Integer infoType,
                         @Param("createTime") String createTime);

    /**
     * 收藏列表
     *
     * @param userId
     * @param infoType
     * @param createTime
     * @param orderby
     * @param order
     * @param offset
     * @param rows
     * @return
     */
    List<Favorite> getFavoriteList(@Param("userId") String userId,
                                   @Param("infoType") Integer infoType,
                                   @Param("createTime") String createTime,
                                   @Param("orderby") String orderby,
                                   @Param("order") String order,
                                   @Param("offset") Integer offset,
                                   @Param("rows") Integer rows);
}
