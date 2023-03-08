package cn.wstom.face.service.impl;

import cn.wstom.face.entity.FaceInfo;
import cn.wstom.face.mapper.FaceInfoMapper;
import cn.wstom.face.service.FaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FaceInfoServiceImpl implements FaceInfoService {
    @Autowired
    FaceInfoMapper faceInfoMapper;

    @Override
    public List<FaceInfo> getFaceInfoList(FaceInfo faceInfo) {
        return faceInfoMapper.getFaceInfoList(faceInfo);
    }

    @Override
    public int insertFaceInfo(FaceInfo faceInfo) {
        return faceInfoMapper.insertFaceInfo(faceInfo);
    }

    @Override
    public int delectFaceInfo(int id) {
        return faceInfoMapper.delectFaceInfo(id);
    }
}
