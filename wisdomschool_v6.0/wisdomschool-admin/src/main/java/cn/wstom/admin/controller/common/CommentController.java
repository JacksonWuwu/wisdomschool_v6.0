package cn.wstom.admin.controller.common;


import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.Comment;
import cn.wstom.admin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

/**
 * 评论控制层，将所有评论功能抽取统一处理，成为公共模块
 *
 * @author dws
 * @date 2019/04/07
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/commentSave")
    @ResponseBody
    Boolean commentSave(@RequestBody Comment comment) throws Exception {
        return commentService.save(comment);
    }

    @RequestMapping("/listByPids")
    @ResponseBody
    List<Comment> listByPids(@RequestBody List<String> parentIds )  {
        return commentService.listByPids(parentIds);
    }

    @RequestMapping("/{CommentId}")
    @ResponseBody
    Comment getCommentById(@PathVariable("CommentId")String CommentId )  {
        return commentService.getById(CommentId);
    }

    @RequestMapping("/removeCommentById/{CommentId}")
    @ResponseBody
    Boolean removeCommentById(@PathVariable("CommentId")String CommentId ) throws Exception {
        return commentService.removeById(CommentId);
    }
}
