package cn.wstom.student.service.impl;


import cn.wstom.student.entity.VideoChapter;
import cn.wstom.student.mapper.VideoChapterMapper;
import cn.wstom.student.service.VideoChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 章节视频 服务层实现
*
* @author dws
* @date 20200204
*/
@Service
public class VideoChapterServiceImpl extends BaseServiceImpl<VideoChapterMapper, VideoChapter>
        implements VideoChapterService {

    @Autowired
    private VideoChapterMapper videoChapterMapper;

    @Override
    public VideoChapter selectByRcId(Integer rcId) {
        return videoChapterMapper.selectByRcId(rcId);
    }

    @Override
    public List<Integer> selecttobeState(String tcid) {
        return videoChapterMapper.selecttobeState(tcid);
    }

    @Override
    public boolean updatebytcid(VideoChapter videoChapter) {
        return videoChapterMapper.updatebytcid(videoChapter);
    }

}
