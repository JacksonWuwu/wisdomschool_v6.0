package cn.wstom.face.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaceInfo {
    private int id;
    private String userId;
    private String faceInfo;
}
