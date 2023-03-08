/*
 *	Copyright © 2015 Zhejiang SKT Science Technology Development Co., Ltd. All rights reserved.
 *	浙江斯凯特科技发展有限公司 版权所有
 *	http://www.28844.com
 */

package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.ReplyService;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
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
