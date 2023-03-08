package cn.wstom.student.controller.front;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.Favorite;
import cn.wstom.student.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dws
 */
@Controller
public class FavoriteController extends BaseController {
    @Autowired
    private FavoriteService favoriteService;



    /**
     * 处理关注信息
     *
     * @param id
     * @param type
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/ucenter/favorite/add")
    public Data addFavorite(@RequestParam(value = "id") String id, @RequestParam(value = "type") Integer type) throws Exception {
        Assert.notNull(id, "非法参数");
        Assert.notNull(type, "非法参数");
        Favorite favorite = new Favorite();
        favorite.setUserId(getUserId());
        favorite.setInfoId(id);
        favorite.setInfoType(type);
        return toAjax(favoriteService.save(favorite));
    }

}
