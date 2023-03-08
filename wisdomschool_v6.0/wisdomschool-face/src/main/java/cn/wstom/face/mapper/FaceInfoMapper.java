package cn.wstom.face.mapper;

import cn.wstom.face.entity.FaceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaceInfoMapper {

    List<FaceInfo> getFaceInfoList(FaceInfo faceInfo);

    int insertFaceInfo(FaceInfo faceInfo);

    int delectFaceInfo(int id);


}
