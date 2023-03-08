package cn.wstom.storage.server.service.impl;


import cn.wstom.storage.server.enumeration.Constants;
import cn.wstom.storage.server.mapper.NodeMapper;
import cn.wstom.storage.server.model.Node;
import cn.wstom.storage.server.service.PlayVideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 视频播放service
 *
 * @author dws
 */
@Service
public class PlayVideoServiceImpl implements PlayVideoService {
    @Resource
    private NodeMapper nodeMapper;

    private Node foundVideo(String fileId) {
        if (fileId != null && fileId.length() > 0) {
            Node f = this.nodeMapper.queryById(fileId);
            if (f != null) {
                String fileName = f.getFileName();
                String suffix = fileName.substring(fileName.lastIndexOf(Constants.SEPARATOR_DOT) + 1).toLowerCase();
                if ("mp4".equals(suffix) || "webm".equals(suffix) || "mov".equals(suffix)) {
                    return f;
                }
            }
        }
        return null;
    }

    /**
     * <h2>解析播放视频文件</h2>
     * <p>
     * 根据视频文件的ID查询视频文件节点并返回节点JSON信息，以便发起播放请求。
     * </p>
     *
     * @param fileId 文件id
     * @return String 视频节点的JSON字符串
     */
    @Override
    public Node getPlayVideoJson(String fileId) {
        return foundVideo(fileId);
    }
}
