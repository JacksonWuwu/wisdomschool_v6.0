package cn.wstom.storage.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ShowPictureService {
    String getPreviewPictureJson(HttpServletRequest request);

    /**
     * <h2>获取压缩版图片</h2>
     * <p>该方法用于获取指定图片的压缩流，以便于提高预览速度。</p>
     *
     * @param request HttpServletRequest 请求对象，应包含文件块路径fp，该方法会根据其大小自动判定压缩率。
     */
    void getCondensedPicture(HttpServletRequest request, HttpServletResponse response);
}
