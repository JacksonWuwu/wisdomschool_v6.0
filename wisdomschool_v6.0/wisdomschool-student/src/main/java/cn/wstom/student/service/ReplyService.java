package cn.wstom.student.service;


import cn.wstom.student.entity.Reply;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface ReplyService extends BaseService<Reply> {

    public void deleteById(String replyId);
}
