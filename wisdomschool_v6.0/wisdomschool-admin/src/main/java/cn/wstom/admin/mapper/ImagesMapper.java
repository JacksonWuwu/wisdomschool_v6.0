package cn.wstom.admin.mapper;

import cn.wstom.admin.entity.Images;
import cn.wstom.admin.entity.ImagesInfoMerge;
import cn.wstom.admin.entity.Question;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
public interface ImagesMapper extends BaseMapper<Images> {
    // ///////////////////////////////
    // /////       增加       ////////
    // ///////////////////////////////

    /**
     * @param images
     * @return
     */
    int addImages(Images images);

    /**
     * 添加图片和信息关联记录
     *
     * @param imagesInfoMerge
     * @return
     */
    int addImagesInfoMerge(ImagesInfoMerge imagesInfoMerge);
    // ///////////////////////////////
    // /////        刪除      ////////
    // ///////////////////////////////

    /**
     * 按图片id删除图片信息
     *
     * @param id
     * @return
     */
    int deleteImagesById(@Param("id") String id);


    /**
     * 按信息分类和内容id删除图片信息
     *
     * @param channelId
     * @param tid
     * @return
     */
    int deleteImagesByTid(@Param("channelId") String channelId, @Param("tid") String tid);

    /**
     * 按图片路径删除数据
     *
     * @param tid    信息id
     * @param imgurl 图片地址
     * @return
     */
    int deleteImagesByTidAndImgurl(@Param("tid") String tid, @Param("imgurl") String imgurl);

    // ///////////////////////////////
    // /////        修改      ////////
    // ///////////////////////////////

    int updateImagesById(Images images);

    //更新图片被使用次数
    int updateImagesCount(String id);

    // ///////////////////////////////
    // ///// 查詢 ////////
    // ///////////////////////////////
    Images getImagesById(@Param("id") String id);

    /**
     * 按信息类别和信息ID查询所有相关图片信息
     *
     * @param tid
     * @return
     */
    List<Images> getImagesListByTid(@Param("tid") String tid);

    /**
     * 按信息类型id和信息id查询第一个文章图片
     *
     * @param imgUrl
     * @return
     */
    Images findImagesByImgurl(@Param("imgUrl") String imgUrl);


    /**
     * 用信息id和图片地址查询该图片是否存在
     *
     * @param tid    信息id
     * @param imgUrl 图片地址
     * @return
     */
    int checkImagesByTidAndImgurl(@Param("tid") String tid, @Param("imgUrl") String imgUrl);

    /**
     * 查询图片路径是否存在
     *
     * @param imgUrl 图片地址
     * @return
     */
    int checkImagesByImgurl(@Param("imgUrl") String imgUrl);


    /**
     * 按信息id查询所有关联图片的数量
     *
     * @param channelId  信息id
     * @param img_width  图片宽度
     * @param img_height 图片高度
     * @return
     */
    int getImagesByTidListCount(
            @Param("channelId") String channelId,
            @Param("img_width") Integer img_width,
            @Param("img_height") Integer img_height);

    /**
     * 按信息类别id查询所有关联的图片
     *
     * @param channelId 信息类别id
     * @param imgWidth  图片宽度
     * @param imgHeight 图片高度
     * @param offset    翻页起始数
     * @param rows      查询记录条数
     * @return
     */
    List<Question> getImagesByTidList(
            @Param("channelId") String channelId,
            @Param("imgWidth") Integer imgWidth,
            @Param("imgHeight") Integer imgHeight,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);
}
