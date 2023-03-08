/*
 *	Copyright © 2015 Zhejiang SKT Science Technology Development Co., Ltd. All rights reserved.
 *	浙江斯凯特科技发展有限公司 版权所有
 *	http://www.28844.com
 */

package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.DeckService;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author dws
 */
@Service
public class DeckServiceImpl extends BaseServiceImpl
        <DeckMapper, Deck>
        implements DeckService {

    @Resource
    private DeckMapper deckMapper;

    @Override
    public void deckDelete(String deckId) {
        deckMapper.deckDelete(deckId);
    }
}
