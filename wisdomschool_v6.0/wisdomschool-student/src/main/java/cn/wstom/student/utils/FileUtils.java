package cn.wstom.student.utils;


import cn.wstom.student.config.Global;
import cn.wstom.student.constants.Constants;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Date;

import static com.google.common.io.ByteStreams.toByteArray;

/**
 * 文件处理工具类
 *
 * @author dws
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 允许通过的格式
     */
    public static String[] allowImage = {"gif", "png", "jpg", "jpeg", "bmp"};
    public static String[] allowVideo = {"mov", "mp4", "webm", "ogg", "flv"};
    public static String[] allowFile = {"doc", "docx", "xlsx", "xls", "text", "pdf", "rar", "zip", "ppt"};
    public static String[] allowExt = {"mp3"};
    public static String[] adjunctFile = {"doc", "docx", "xlsx", "xls", "text", "pdf", "zip", "ppt"};
    /**
     * 默认的文件名最大长度
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 200;

    /**
     * storage_media 为 local时生效
     */
    private static String LOCAL_BASE_DIR;

    /**
     * 存储媒介
     */
    public static String STORAGE_MEDIA;

    /**
     * 文件名格式
     */
    private static String YYYYMM = "yyyy/MMdd/";
    private static String DDHHMMSS = "ddHHmmss/";
    private static String YYYYMMDDHHMMSS = YYYYMM + DDHHMMSS;
    private static RandomStringGenerator randomString = new RandomStringGenerator.Builder().withinRange('a', 'x').build();

    static {
        STORAGE_MEDIA = Global.getConfig("wstom.file.storage-media");
        allowExt = Global.getConfig("wstom.file.allow-ext").split(",");
    }

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        boolean result = false;
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            LOGGER.info("删除文件【{}】", filePath);
            result = file.delete();
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && parentFile.exists() &&
                parentFile.isDirectory() && parentFile.listFiles().length <= 0) {
            result = parentFile.delete();
        }
        return result;
    }

    /**
     * 校验路径
     *
     * @param path
     * @throws IOException
     */
    public static void validatePath(String path) throws IOException {
        File file = new File(path);
        validatePath(file);
    }

    /**
     * 校验路径
     *
     * @param file
     * @throws IOException
     */
    public static void validatePath(File file) throws IOException {
        if (file.getParentFile() != null && !file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new IOException("target '" + file.getPath() + "' directory cannot be created");
        } else if (file.exists() && !file.canWrite()) {
            throw new IOException("target '" + file.getPath() + "' is read-only");
        }
    }


    public static File getFileByUrl(String url) throws IOException {
        File tmpFile = File.createTempFile("temp", ".tmp");//创建临时文件
        toBDFile(url, tmpFile.getCanonicalPath());
        return tmpFile;
    }

    public static long getDuration(File file) throws EncoderException {
        MultimediaInfo m = new Encoder().getInfo(file);
        return m.getDuration();
    }

    public static void toBDFile(String urlStr, String bdUrl) throws IOException, UnknownHostException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        DataInputStream in = new DataInputStream(conn.getInputStream());
        byte[] data= toByteArray(in);
        in.close();
        FileOutputStream out=new FileOutputStream(bdUrl);
        out.write(data);
        out.close();
    }
    /**
     * 获取远程文件
     *
     * @param imageUrl
     * @param targetPath
     * @throws Exception
     */
    public static void downLoadFile(String imageUrl, String targetPath) throws Exception {
        URL url = new URL(imageUrl);
        //打开链接
        URLConnection connection = url.openConnection();
        int connectTimeout = 5 * 1000;
        connection.setConnectTimeout(connectTimeout);
        InputStream is = connection.getInputStream();

        byte[] buff = new byte[1024];
        int len;
        File file = new File(targetPath);
        if (file.getParentFile() != null && !file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new IOException("target directory 【" + targetPath + "】 cannot be created");
        }

        OutputStream os = new FileOutputStream(targetPath);
        while ((len = is.read(buff)) != -1) {
            os.write(buff, 0, len);
        }
        os.close();
        is.close();
    }

    /**
     * 生成当前年月日格式的文件路径
     *
     * @return
     */
    public static String getPathName() {
        return DateFormatUtils.format(new Date(), YYYYMM);
    }

    /**
     * 生成文件名
     *
     * @return
     */
    public static String getFileName() {
        return DateFormatUtils.format(new Date(), DDHHMMSS) + randomString.generate(4);
    }

    /**
     * 生成文件名
     *
     * @param ext 文件名后缀，不带‘.’
     * @return
     */
    public static String getFileName(String ext) {
        return getFileName() + Constants.SEPARATOR_DOT + ext;
    }

    /**
     * 生成路径和文件名
     *
     * @param ext 文件后缀，不带‘.’
     * @return
     */
    public static String getPathAndFileName(String ext) {
        return DateFormatUtils.format(new Date(), YYYYMMDDHHMMSS) + randomString.generate(4) + Constants.SEPARATOR_DOT + ext;
    }

    /**
     * 文件类型判断
     *
     * @param extName
     * @return
     */
    public static boolean checkFileType(String extName, String[] allowFiles) {
        for (String s : allowFiles) {
            if (extName.toLowerCase().endsWith(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExt(String fileName) {
        int pos = fileName.lastIndexOf(Constants.SEPARATOR_DOT);
        return fileName.substring(pos + 1);
    }

    /**
     * 获取文件绝对路径
     *
     * @param dir
     * @param filename
     * @return
     * @throws IOException
     */
    public static File getAbsoluteFile(String dir, String filename) throws IOException {
        String baseDir = getBaseDir();
        File file;
        if (dir != null && "".equals(dir.trim())) {
            file = new File(baseDir + File.separator + dir + File.separator + filename);
        } else {
            file = new File(baseDir + File.separator + filename);
        }

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 获取存储基路径
     *
     * @return
     */
    public static String getBaseDir() {
        if (StringUtil.isEmpty(LOCAL_BASE_DIR)) {
            LOCAL_BASE_DIR = Global.getConfig("wstom.file.storage-path");
            if (!LOCAL_BASE_DIR.endsWith("\\") && !LOCAL_BASE_DIR.endsWith("/")) {
                LOCAL_BASE_DIR = LOCAL_BASE_DIR + File.separator;
            }
        }
        return LOCAL_BASE_DIR;
    }

    /**
     * 获取临时文件路径
     *
     * @return
     */
    public static String getTmpDir() {
        return getBaseDir() + "tmp" + File.separator;
    }

    /**
     * 编码文件名
     */
    public static String encodingFilename(String filename, String extension) {
        filename = filename.replace("_", " ");
        filename = Md5Utils.hash(filename + System.nanoTime()) + extension;
        return filename;
    }


    /**
     * 复制整个文件夹的内容
     *
     * @param oldFolderPath
     *            准备拷贝的目录
     * @param newFolderPath
     *            指定绝对路径的新目录
     * @return
     */
    public void copyFolder(String oldFolderPath, String newFolderPath) throws IOException {
        // 如果文件夹不存在 则建立新文件夹

        File newFolder = new File(newFolderPath);
        if (!newFolder.getParentFile().canWrite() || !newFolder.getParentFile().canWrite()) {
            throw new IOException("路径【" + newFolderPath + "】没有写权限, 请检查");
        }
        if ((!newFolder.exists() || !newFolder.isDirectory())) {
            newFolder.mkdirs();
        }
        File oldFolder = new File(oldFolderPath);
        String[] oldFilePaths = oldFolder.list();
        if (oldFilePaths == null || oldFilePaths.length <= 0) {
            return;
        }
        File temp = null;
        for (String oldFilePath : oldFilePaths) {
            if (oldFolderPath.endsWith(File.separator)) {
                temp = new File(oldFolderPath + oldFilePath);
            } else {
                temp = new File(oldFolderPath + File.separator + oldFilePath);
            }
            if (temp.isFile()) {
                FileInputStream input = new FileInputStream(temp);
                FileOutputStream output = new FileOutputStream(newFolderPath
                        + "/" + temp.getName());
                byte[] b = new byte[1024 * 5];
                int len;
                while ((len = input.read(b)) != -1) {
                    output.write(b, 0, len);
                }
                output.flush();
                output.close();
                input.close();
            }
            // 如果是子文件夹
            if (temp.isDirectory()) {
                copyFolder(oldFolderPath + "/" + oldFilePath, newFolderPath + "/" + oldFilePath);
            }
        }
    }
}
