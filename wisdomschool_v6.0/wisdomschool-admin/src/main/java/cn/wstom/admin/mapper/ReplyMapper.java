package cn.wstom.admin.mapper;

import cn.wstom.admin.entity.Reply;


/**
 *
 */
public interface ReplyMapper extends BaseMapper<Reply> {

    public void deleteReplyById(String replyId);
}
