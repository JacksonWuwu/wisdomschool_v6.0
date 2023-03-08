package cn.wstom.student.mapper;


import cn.wstom.student.entity.Reply;

/**
 *
 */
public interface ReplyMapper extends BaseMapper<Reply> {

    public void deleteReplyById(String replyId);
}
