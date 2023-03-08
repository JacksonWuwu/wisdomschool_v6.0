package cn.wstom.storage.server.service.impl;


import cn.wstom.storage.server.enumeration.Constants;
import cn.wstom.storage.server.mapper.NodeMapper;
import cn.wstom.storage.server.model.Node;
import cn.wstom.storage.server.pojo.PictureViewList;
import cn.wstom.storage.server.service.ShowPictureService;
import cn.wstom.storage.server.util.ConfigureReader;
import cn.wstom.storage.server.util.JSONUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowPictureServiceImpl implements ShowPictureService {
    @Resource
    private NodeMapper nodeMapper;

    /**
     * <h2>获取所有同级目录下的图片并封装为PictureViewList对象</h2>
     * <p>
     * 该方法用于根据请求获取预览图片列表并进行封装，对于过大图片会进行压缩。
     * </p>
     *
     * @param request HttpServletRequest 请求对象，需包含fileId字段（需预览的图片ID）。
     * @return PictureViewList 预览列表封装对象，详见其注释。
     * @see PictureViewList
     */
    private PictureViewList foundPictures(HttpServletRequest request) {
        String fileId = request.getParameter("fileId");
        if (fileId != null && fileId.length() > 0) {
            String account = (String) request.getSession().getAttribute("ACCOUNT");
            List<Node> nodes = this.nodeMapper.queryBySomeFolder(fileId);
            List<Node> pictureViewList = new ArrayList<>();
            int index = 0;
            for (Node n : nodes) {
                String fileName = n.getFileName();
                String suffix = fileName.substring(fileName.lastIndexOf(Constants.SEPARATOR_DOT) + 1).toLowerCase();
                if ("jpg".equals(suffix) || "jpeg".equals(suffix) || "gif".equals(suffix) || "bmp".equals(suffix)
                        || "png".equals(suffix)) {
                    int pSize = Integer.parseInt(n.getFileSize());
                    if (pSize > 1) {
                        n.setFilePath("homeController/showCondensedPicture?fileId=" + n.getFileId());
                    }
                    pictureViewList.add(n);
                    if (!n.getFileId().equals(fileId)) {
                        continue;
                    }
                    index = pictureViewList.size() - 1;
                }
            }
            PictureViewList pvl = new PictureViewList();
            pvl.setIndex(index);
            pvl.setPictureViewList(pictureViewList);
            return pvl;
        }
        return null;
    }

    @Override
    public String getPreviewPictureJson(HttpServletRequest request) {
        PictureViewList pvl = this.foundPictures(request);
        if (pvl != null) {
            return JSONUtils.writeValue(pvl);
        }
        return "ERROR";
    }

    @Override
    public void getCondensedPicture(HttpServletRequest request, HttpServletResponse response) {
        String fileId = request.getParameter("fileId");
        if (fileId != null) {
            Node node = nodeMapper.queryById(fileId);
            if (node != null) {
                File pBlock = new File(ConfigureReader.instance().getFileBlockPath(), node.getFilePath());
                if (pBlock.exists()) {
                    try {
                        int pSize = Integer.parseInt(node.getFileSize());
                        if (pSize < 3) {
                            Thumbnails.of(pBlock).scale(0.5).outputFormat("JPG").toOutputStream(response.getOutputStream());
                        } else if (pSize < 5) {
                            Thumbnails.of(pBlock).scale(0.3).outputFormat("JPG").toOutputStream(response.getOutputStream());
                        } else {
                            Thumbnails.of(pBlock).size(600, 600).outputFormat("JPG").toOutputStream(response.getOutputStream());
                        }
                    } catch (IOException e) {
                        //压缩失败时，尝试以源文件进行预览
                        try {
                            Files.copy(pBlock.toPath(), response.getOutputStream());
                        } catch (IOException e1) {
                        }
                    }
                }
            }
        }
    }
}
