package cn.wstom.storage.server.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;

/**
 * 文件下载（支持各种文件下载）
 * @author guoyunlong
 *
 */
public class ZipFileDownload {
    // public static final String HTTp_URL =
    // "https://gongyi.bj.bcebos.com/Jellyfish-baidugongyi-auction-9f6da244-e87d-4ab9-bd33-bfb9300be5f7.jpg";

    // public static void main(String[] args) {
    // Dol();
    // }
    /**
     * 文件下载
     *
     * @param urls 需要下载的url
     * @param path 需要下载的路径
     * @param newFileNmae 截取的新文件名
     * @return
     * @throws IOException
     */
    public static void dolFile(String urls, Path path, String newFileNmae) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File file = null;
        try {
            if (!urls.equals("") && urls != null) {
                URL url = new URL(urls);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                // 解决乱码问题
                connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
                connection.connect();
                InputStream is = connection.getInputStream();
                bis = new BufferedInputStream(is);
                file = new File(path + "/" + newFileNmae);
                FileOutputStream fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int b = 0;
                byte[] byArr = new byte[1024 * 1024];
                while ((b = bis.read(byArr)) != -1) {
                    bos.write(byArr, 0, b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}