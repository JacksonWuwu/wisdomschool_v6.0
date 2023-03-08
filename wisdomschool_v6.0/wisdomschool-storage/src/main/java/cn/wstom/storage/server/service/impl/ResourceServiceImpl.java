package cn.wstom.storage.server.service.impl;


import cn.wstom.storage.server.enumeration.Constants;
import cn.wstom.storage.server.mapper.NodeMapper;
import cn.wstom.storage.server.model.Node;
import cn.wstom.storage.server.service.ResourceService;
import cn.wstom.storage.server.util.ConfigureReader;
import cn.wstom.storage.server.util.FileBlockUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * 资源服务类，所有处理非下载流请求的工作均在此完成
 *
 * @author dws
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private NodeMapper nodeMapper;

    /**
     * 提供资源的输出流，原理与下载相同，但是个别细节有区别
     *
     * @param request
     * @param response
     */
    @Override
    public void getResource(HttpServletRequest request, HttpServletResponse response) {
        String fid = request.getParameter("fid");
        if (fid == null) {
            fid = (String) request.getAttribute("fid");
        }
        System.out.println("fid : " + fid);
        if (fid != null) {
            Node n = nodeMapper.queryById(fid);
            if (n != null) {
                File file = FileBlockUtil.getFileFromBlocks(n);
                String suffix = n.getFileName().substring(n.getFileName().lastIndexOf(Constants.SEPARATOR_DOT)).trim().toLowerCase();
                String contentType = "application/octet-stream";
                switch (suffix) {
                    case ".mov":
                    case ".mp4":
                        contentType = "video/mp4";
                        break;
                    case ".webm":
                        contentType = "video/webm";
                        break;
                    case ".mp3":
                        contentType = "audio/mpeg";
                        break;
                    case ".ogg":
                        contentType = "audio/ogg";
                        break;
                    default:
                        break;
                }
                sendResource(file, n.getFileName(), contentType, request, response);
                if (request.getHeader("Range") == null) {
                    // TODO: 2019/2/23 记录日志
                }
                return;
            }
        }
        try {
            //  处理无法下载的资源
            response.sendError(404);
        } catch (IOException e) {
        }
    }

    /**
     * 使用各个浏览器（主要是Safari）兼容的通用格式发送资源至请求来源，类似于断点续传下载功能
     *
     * @param resource
     * @param fname
     * @param contentType
     * @param request
     * @param response
     */
    private void sendResource(File resource, String fname, String contentType, HttpServletRequest request,
                              HttpServletResponse response) {
        try (RandomAccessFile randomFile = new RandomAccessFile(resource, "r")) {
            long contentLength = randomFile.length();
            String range = request.getHeader("Range");
            long start = 0, end = 0;
            if (range != null && range.startsWith("bytes=")) {
                String[] values = range.split("=")[1].split("-");
                start = Long.parseLong(values[0]);
                if (values.length > 1) {
                    end = Long.parseLong(values[1]);
                }
            }
            long requestSize = 0;
            if (end != 0 && end > start) {
                requestSize = end - start + 1;
            } else {
                requestSize = Long.MAX_VALUE;
            }
            byte[] buffer = new byte[ConfigureReader.instance().getBuffSize()];
            response.setContentType(contentType);
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("ETag", fname);
            response.setHeader("Last-Modified", new Date().toString());
            // 第一次请求只返回content length来让客户端请求多次实际数据
            if (range == null) {
                response.setHeader("Content-length", contentLength + "");
            } else {
                // 以后的多次以断点续传的方式来返回视频数据
                // 206
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                long requestStart = 0, requestEnd = 0;
                String[] ranges = range.split("=");
                if (ranges.length > 1) {
                    String[] rangeDatas = ranges[1].split("-");
                    requestStart = Long.parseLong(rangeDatas[0]);
                    if (rangeDatas.length > 1) {
                        requestEnd = Long.parseLong(rangeDatas[1]);
                    }
                }
                long length = 0;
                if (requestEnd > 0) {
                    length = requestEnd - requestStart + 1;
                    response.setHeader("Content-length", "" + length);
                    response.setHeader("Content-Range",
                            "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
                } else {
                    length = contentLength - requestStart;
                    response.setHeader("Content-length", "" + length);
                    response.setHeader("Content-Range",
                            "bytes " + requestStart + "-" + (contentLength - 1) + "/" + contentLength);
                }
            }
            ServletOutputStream out = response.getOutputStream();
            long needSize = requestSize;
            randomFile.seek(start);
            while (needSize > 0) {
                int len = randomFile.read(buffer);
                if (needSize < buffer.length) {
                    out.write(buffer, 0, (int) needSize);
                } else {
                    out.write(buffer, 0, len);
                    if (len < buffer.length) {
                        break;
                    }
                }
                needSize -= buffer.length;
            }
            out.close();
        } catch (Exception e) {

        }
    }

}
