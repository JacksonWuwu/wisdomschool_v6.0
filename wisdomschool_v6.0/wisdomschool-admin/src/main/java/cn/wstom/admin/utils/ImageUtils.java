package cn.wstom.admin.utils;

import cn.wstom.admin.exception.ApplicationException;
import cn.wstom.common.utils.FileUtils;

import com.sun.imageio.plugins.common.ImageUtil;
import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 图片工具类
 *
 * @author dws
 * @date 2018/9/1
 */
public class ImageUtils {

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static void scale(InputStream is, String targetPath, int width, int height) throws IOException {
        FileUtils.validatePath(targetPath);
        logger.debug("scaled with/height : " + width + "/" + height);
        Thumbnails.of(is).size(width, height).toFile(targetPath);
    }

    public static void scale(File file, String targetPath, int width, int height) throws IOException {
        FileUtils.validatePath(targetPath);
        logger.debug("scaled with/height : " + width + "/" + height);
        Thumbnails.of(file).size(width, height).toFile(targetPath);
    }

    /**
     * 图片压缩,各个边按比例压缩
     *
     * @param is         原图位置
     * @param targetPath 目标位置
     * @param maxSize    指定压缩后最大边长
     * @return boolean
     * @throws IOException
     */
    public static void scale(InputStream is, String targetPath, int maxSize) throws IOException {
        // 读入文件
        BufferedImage src = ImageIO.read(is);
        Map<String, Integer> optimalSize = getOptimalSize(src, maxSize);
        scale(is, targetPath, optimalSize.get("w"), optimalSize.get("h"));
    }

    public static void scale(File file, String targetPath, int maxSize) throws IOException {
        // 读入文件
        BufferedImage img = ImageIO.read(file);
        Map<String, Integer> optimalSize = getOptimalSize(img, maxSize);
        scale(file, targetPath, optimalSize.get("w"), optimalSize.get("h"));
    }

    /**
     * 获得最佳大小
     *
     * @param img
     * @param maxSize
     * @return
     */
    private static Map<String, Integer> getOptimalSize(BufferedImage img, int maxSize) throws IOException {
        Map<String, Integer> optimalSize = new HashMap<>(2);

        int w = img.getWidth();
        int h = img.getHeight();

        logger.debug("origin with/height " + w + "/" + h);

        int size = Math.max(w, h);
        int tow = w;
        int toh = h;

        if (size > maxSize) {
            if (w > maxSize) {
                tow = maxSize;
                toh = h * maxSize / w;
            } else {
                tow = w * maxSize / h;
                toh = maxSize;
            }
        }
        logger.debug("scaled with/height : " + tow + "/" + toh);
        optimalSize.put("w", tow);
        optimalSize.put("h", toh);
        return optimalSize;
    }

    /**
     * 裁剪图片
     *
     * @param file       源图片路径
     * @param targetPath 处理后图片路径
     * @param x          起始X坐标
     * @param y          起始Y坐标
     * @param width      裁剪宽度
     * @param height     裁剪高度
     * @return boolean
     * @throws IOException          io异常
     * @throws InterruptedException 中断异常
     */
    public static void cutImage(File file, String targetPath, int x, int y, int width, int height) throws IOException {
        FileUtils.validatePath(targetPath);
        Thumbnails.of(file).sourceRegion(x, y, width, height).size(width, height).keepAspectRatio(false).toFile(targetPath);
    }

    /**
     * 裁剪图片
     *
     * @param file       源图片路径
     * @param targetPath 处理后图片路径
     * @param x          起始X坐标
     * @param y          起始Y坐标
     * @param size       裁剪大小
     * @return boolean
     * @throws IOException          io异常
     * @throws InterruptedException 中断异常
     */
    public static void cutImage(File file, String targetPath, int x, int y, int size) throws IOException {
        cutImage(file, targetPath, x, y, size, size);
    }

    public static String storeImage(MultipartFile file, String dir) throws IOException, ApplicationException {
        ImageUtils.validateImage(file);

        String path = dir + FileUtils.getPathAndFileName(FileUtils.getExt(Objects.requireNonNull(file.getOriginalFilename())));
        File temp = new File(FileUtils.getBaseDir() + path);
        FileUtils.validatePath(temp);
        file.transferTo(temp);
        return path;
    }

    public static String storeThumb(MultipartFile file, String basePath, int maxWidth) throws IOException, ApplicationException {
        ImageUtils.validateImage(file);

        String path = basePath + FileUtils.getPathAndFileName(FileUtils.getExt(Objects.requireNonNull(file.getOriginalFilename())));
        ImageUtils.scale(file.getInputStream(), path, maxWidth);
        return path;
    }

    public static String storeThumb(MultipartFile file, String basePath, int width, int height) throws IOException, ApplicationException {
        ImageUtils.validateImage(file);

        String path = basePath + FileUtils.getPathAndFileName(FileUtils.getExt(Objects.requireNonNull(file.getOriginalFilename())));
        ImageUtils.scale(file.getInputStream(), path, width, height);
        return path;
    }

    public Map<String, Integer> imageSize(String storePath) {
        File file = new File(storePath);
        try {
            BufferedImage image = ImageIO.read(file);
            int width = image.getWidth();
            int height = image.getHeight();

            HashMap<String, Integer> result = new HashMap<>(2);
            result.put("w", width);
            result.put("h", height);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void validateImage(MultipartFile file) throws IOException, ApplicationException, cn.wstom.admin.exception.ApplicationException {
        FileUploadUtils.validateFile(file, FileUtils.allowImage);
    }
}
