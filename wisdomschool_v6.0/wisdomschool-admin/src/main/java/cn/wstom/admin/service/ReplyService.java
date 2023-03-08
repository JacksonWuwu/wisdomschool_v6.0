package cn.wstom.admin.service;


import cn.wstom.admin.entity.Reply;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface ReplyService extends BaseService<Reply> {

    public void deleteById(String replyId);
}
