package cn.wstom.storage.server.mapper;

import cn.wstom.storage.server.model.Folder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface FolderMapper {
    Folder queryById(String fid);

    List<Folder> queryByParentId(String pid);

    Folder queryByParentIdAndFolderName(Map<String, String> map);

    int insertNewFolder(Folder f);

    int deleteById(String folderId);

    int updateFolderNameById(Map<String, Object> map);

    int updateFolderConstraintById(Map<String, Object> map);

    int moveById(Map<String, Object> map);
}
