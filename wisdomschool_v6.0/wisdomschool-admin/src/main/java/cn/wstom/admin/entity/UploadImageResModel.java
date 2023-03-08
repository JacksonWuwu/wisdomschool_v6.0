package cn.wstom.admin.entity;

import lombok.Data;

@Data
public class UploadImageResModel {
    /**
     * 1成功，0失败
     */
    private Integer uploaded;

    private String fileName;

    private String url;
}
