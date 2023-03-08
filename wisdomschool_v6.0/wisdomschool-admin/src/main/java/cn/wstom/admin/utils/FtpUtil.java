package cn.wstom.admin.utils;

import cn.wstom.admin.exception.ApplicationException;
import cn.wstom.common.config.Global;
import cn.wstom.common.support.CharsetKit;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * ftp操作工具类
 *
 * @author dws
 * @date 2019/02/05
 */
public class FtpUtil {

    private static String ftpDir;

    public static FTPClient connectFtp() {
        FTPClient ftpClient = new FTPClient();

        String servername = Global.getConfig("upload.ftp.ip");
        String username = Global.getConfig("upload.ftp.username");
        String passwd = Global.getConfig("upload.ftp.password");
        String port = Global.getConfig("upload.ftp.port", "21");
        ftpDir = Global.getConfig("upload.ftp.dir");

        try {
            ftpClient.connect(servername, Integer.parseInt(port));
            ftpClient.setDataTimeout(18000);
            ftpClient.login(username, passwd);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new Exception("连接ftp服务器失败");
            }

            changeDirectory(ftpClient, ftpDir);
            ftpClient.changeWorkingDirectory("/" + ftpDir);
            return ftpClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void uploadFile(FTPClient ftpClient, String webname, String localFilePath, String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(localFilePath);
        uploadFile(ftpClient, webname, fis, fileName);
    }

    public static void uploadFile(FTPClient ftpClient, String webname, File file, String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        uploadFile(ftpClient, webname, fis, fileName);
    }

    public static String uploadFile(FTPClient ftpClient, String folder, InputStream in, String fileName) throws Exception {

        if (ftpClient == null) {
            throw new Exception("ftpClient can not be init");
        }

        BufferedInputStream buff = null;

        try {
            if (changeDirectory(ftpClient, folder)) {
                boolean cd = ftpClient.changeWorkingDirectory("/" + ftpDir + "/" + folder + "/");
                if (!cd) {
                    throw new Exception("切换到上传文件目录失败或者目录不存在！");
                }

                buff = new BufferedInputStream(in);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setBufferSize(4096);
                ftpClient.setFileType(2);
                String[] files = ftpClient.listNames();
                if (files != null) {
                    for (int i = 0; i < files.length; ++i) {
                        if ((new String(files[i].getBytes(StandardCharsets.ISO_8859_1), CharsetKit.GBK)).equals(fileName)) {
                            ftpClient.deleteFile(files[i]);
                        }
                    }
                }

                ftpClient.storeFile(new String(fileName.getBytes(CharsetKit.GBK), StandardCharsets.ISO_8859_1), buff);
                return folder + "/" + fileName;
            }
        } catch (Exception e) {
            throw new Exception("上传ftp文件失败，请检查系统参数配置和ftp服务器状态!");
        } finally {
            try {
                if (buff != null) {
                    buff.close();
                }
                closeFtp(ftpClient);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        throw new ApplicationException("上传ftp文件失败，请检查系统参数配置和ftp服务器状态!");
    }

    public static void closeFtp(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean changeDirectory(FTPClient ftpClient, String dirName) throws Exception {
        try {
            boolean r = ftpClient.changeWorkingDirectory(dirName);
            if (!r) {
                boolean f = ftpClient.makeDirectory(dirName);
                if (!f) {
                    throw new Exception("创建ftp目录失败，请检查用户是否有创建ftp目录权限!");
                }

                return true;
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return true;
    }
}
