package cn.wstom.admin.service.impl;

import cn.wstom.admin.entity.Discuss;
import cn.wstom.admin.mapper.DiscussMapper;
import cn.wstom.admin.service.DiscussService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/31
 */
@Service
public class DiscussServiceImpl extends BaseServiceImpl<DiscussMapper, Discuss>
        implements DiscussService {

    @Autowired
    private DiscussMapper discussMapper;

    @Override
    public List<Discuss> list(Discuss discuss) {
        Integer sort = (Integer) discuss.getParams().get("sort");
        StringBuilder sb = new StringBuilder();
        switch (sort) {
            case 2:
                sb.append("(CASE type ")
                        .append("WHEN 2 THEN 2 ")
                        .append("ELSE 0 END) DESC, t.create_tate DESC");
                break;
            case 3:
                sb.append("(CASE type")
                        .append("WHEN 2 THEN 2")
                        .append("ELSE 0 END) DESC, view DESC");
                break;
            case 4:
                sb.append("(CASE type")
                        .append("WHEN 2 THEN 2")
                        .append("ELSE 0 END) DESC, update_time DESC");
                break;
            default:
                sb.append("(CASE type")
                        .append("WHEN 2 THEN 2")
                        .append("ELSE 0 END) DESC, update_time DESC");
                break;
        }

        discuss.getParams().put("sort", sb.toString());
        return discussMapper.selectList(discuss);
    }
}
