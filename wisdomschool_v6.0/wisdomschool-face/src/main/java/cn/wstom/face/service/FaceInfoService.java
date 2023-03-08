package cn.wstom.face.service;

import cn.wstom.face.entity.FaceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface FaceInfoService {

    List<FaceInfo> getFaceInfoList(FaceInfo faceInfo);

    int insertFaceInfo(FaceInfo faceInfo);

    int delectFaceInfo(int id);


}
