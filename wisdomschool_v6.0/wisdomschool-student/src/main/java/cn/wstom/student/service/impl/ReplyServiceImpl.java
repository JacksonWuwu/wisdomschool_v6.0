

package cn.wstom.student.service.impl;


import cn.wstom.student.entity.Reply;
import cn.wstom.student.mapper.ReplyMapper;
import cn.wstom.student.service.ReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author dws
 */
@Service
public class ReplyServiceImpl extends BaseServiceImpl
        <ReplyMapper, Reply>
        implements ReplyService {

    @Resource
    private ReplyMapper replyMapper;


    @Override
    public void deleteById(String replyId){
        replyMapper.deleteReplyById(replyId);
    }

}
