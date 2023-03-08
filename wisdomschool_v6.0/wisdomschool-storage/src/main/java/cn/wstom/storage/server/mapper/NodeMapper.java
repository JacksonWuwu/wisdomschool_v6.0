package cn.wstom.storage.server.mapper;

import cn.wstom.storage.server.model.Node;

import java.util.List;
import java.util.Map;

public interface NodeMapper {
    List<Node> queryByParentFolderId(String pfid);

    List<Node> queryByMyRoot(String fileCreator);

    int insert(Node f);

    int update(Node f);

    int deleteByParentFolderId(String pfid);

    int deleteById(String fileId);

    Node queryById(String fileId);

    int updateFileNameById(Map<String, String> map);

    List<Node> queryAll();

    Node queryByPath(String path);

    List<Node> queryBySomeFolder(String fileId);

    /**
     * <h2>移动文件节点至指定的文件夹节点</h2>
     * <p>该映射用于实现移动文件（剪切-粘贴）功能，将某一文件节点的父级路径更改为新的父级路径。</p>
     *
     * @param map<String,String> 其中要包括要移动的文件节点id（fileId）以及新的父级文件夹id（locationpath）
     * @return 影响数目
     */
    int moveById(Map<String, String> map);
}
