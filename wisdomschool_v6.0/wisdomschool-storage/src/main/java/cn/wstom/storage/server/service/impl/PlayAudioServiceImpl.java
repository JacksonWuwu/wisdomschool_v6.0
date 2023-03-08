package cn.wstom.storage.server.service.impl;


import cn.wstom.storage.server.mapper.NodeMapper;
import cn.wstom.storage.server.model.Node;
import cn.wstom.storage.server.pojo.AudioInfoList;
import cn.wstom.storage.server.service.PlayAudioService;
import cn.wstom.storage.server.util.AudioInfoUtil;
import cn.wstom.storage.server.util.JSONUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dws
 */
@Service
public class PlayAudioServiceImpl implements PlayAudioService {
    @Resource
    private NodeMapper nodeMapper;

    private AudioInfoList foundAudios(HttpServletRequest request) {
        String fileId = request.getParameter("fileId");
        if (fileId != null && fileId.length() > 0) {
            List<Node> blocks = this.nodeMapper.queryBySomeFolder(fileId);
            return AudioInfoUtil.transformToAudioInfoList(blocks, fileId);
        }
        return null;
    }

    /**
     * <h2>解析播放音频文件</h2>
     * <p>根据音频文件的ID查询音频文件节点，以及同级目录下所有音频文件组成播放列表，并返回节点JSON信息，以便发起播放请求。</p>
     *
     * @param request javax.servlet.http.HttpServletRequest 请求对象
     * @return String 视频节点的JSON字符串
     */
    @Override
    public String getAudioInfoListByJson(HttpServletRequest request) {
        AudioInfoList ail = this.foundAudios(request);
        if (ail != null) {
            return JSONUtils.writeValue(ail);
        }
        return "ERROR";
    }
}
