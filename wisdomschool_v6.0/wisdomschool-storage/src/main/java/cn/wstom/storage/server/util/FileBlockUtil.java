package cn.wstom.storage.server.util;


import cn.wstom.storage.exception.ApplicationException;
import cn.wstom.storage.server.enumeration.Constants;

import cn.wstom.storage.server.mapper.*;
import cn.wstom.storage.server.model.*;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.zip.FileSource;
import org.zeroturnaround.zip.ZipEntrySource;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.zip.ZipEntry;

/**
 * @author dws
 */
public class FileBlockUtil {

    private static final String FILE_BLOCKS = ConfigureReader.instance().getFileBlockPath();

    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(5,
            new BasicThreadFactory.Builder().namingPattern("file-pool-%d").daemon(false).build());

    /**
     * <h2>将上传文件存入文件节点</h2>
     * <p>
     * 将一个MultipartFile类型的文件对象存入节点，并返回保存的路径。路径为“UUID.block”形式。
     * </p>
     *
     * @param f MultipartFile 上传文件对象
     * @return String 随机生成的保存路径，如果保存失败则返回“ERROR”
     */
    public static String saveToFileBlocks(MultipartFile f) throws ApplicationException {
        String fileBlocks = ConfigureReader.instance().getFileBlockPath();
        String FILE_SYSTEM_PATH = "C:/Users/HP/Desktop/course/ffmpeg/";
        String id = UUID.randomUUID().toString().replace("-", "");
        String fileName = f.getOriginalFilename();
        int pos = fileName.lastIndexOf(Constants.SEPARATOR_DOT);
//        System.out.println(pos);
//        System.out.println(fileName.substring(pos + 1));
//        System.out.println(fileBlocks);
        String path = "file_" + id + "." + fileName.substring(pos + 1);
//        String path = "file_" + id + ".block";
//        if (fileName.substring(pos + 1).equals("mp4")){
//            fileBlocks=FILE_SYSTEM_PATH;
//        }
        File file = new File(fileBlocks, path);
        try {
            f.transferTo(file);
//            if (fileName.substring(pos + 1).equals("mp4")){
////                Thread thread = new MyThread1(path,FILE_SYSTEM_PATH);
////                thread.start();
////              FileUtils.deleteFile(FILE_SYSTEM_PATH+path);
//                Boolean b= FfmpegUtil.processFLV(path);
//                if(b){
//                    System.out.println("成功");
//                }else {
//                    path="defeated";
//                }

        return path;
        } catch (Exception e) {
            throw new ApplicationException("文件保存出错", e);
        }
    }
    //视频转换线程
    static class MyThread1 extends Thread {
        private String path;
        private String FILE_SYSTEM_PATH;
        public MyThread1(String path,String FILE_SYSTEM_PATH) {
            this.path = path;
            this.FILE_SYSTEM_PATH=FILE_SYSTEM_PATH;
        }
        @Override
        public void run() {
            Boolean b= FfmpegUtil.processFLV(path);
//            FileUtils.deleteFile(FILE_SYSTEM_PATH+path);
        }
    }

    public static String getFileSize(MultipartFile f) {
        long size = f.getSize();
        int mb = (int) (size / 1024L / 1024L);
        return "" + mb;
    }

    public static boolean deleteFromFileBlocks(Node f) {
        File file = new File(ConfigureReader.instance().getFileBlockPath(), f.getFilePath());
        return file.exists() && file.isFile() && file.delete();
    }

    public static File getFileFromBlocks(Node f) {
        File file = new File(ConfigureReader.instance().getFileBlockPath(), f.getFilePath());
        if (file.exists() && file.isFile()) {
            return file;
        }
        return null;
    }

    public static void checkFileBlocks(NodeMapper nodeMapper) {
        String fileblocks = ConfigureReader.instance().getFileBlockPath();

        EXECUTOR.execute(() -> {
            List<Node> nodes = nodeMapper.queryAll();
            for (Node node : nodes) {
                File block = new File(fileblocks, node.getFilePath());
                if (!block.exists()) {
                    nodeMapper.deleteById(node.getFileId());
                }
            }
            File blocks = new File(fileblocks);
            String[] bn = blocks.list();
            for (String n : bn) {
                Node node = nodeMapper.queryByPath(n);
                if (node == null) {
                    File f = new File(fileblocks, n);
                    f.delete();
                }
            }
        });
    }

    public static String createZip(List<String> idList, List<String> fidList, String account) {
        String zipname = "tf_" + UUID.randomUUID().toString() + ".zip";
        String tempPath = ConfigureReader.instance().getTemporaryfilePath();
        NodeMapper nodeMapper = SpringUtils.getBean(NodeMapper.class);
        File f = new File(tempPath, zipname);
        try {
            List<ZipEntrySource> zs = new ArrayList<>();
            for (String fid : fidList) {
                addFoldersToZipEntrySourceArray(fid, zs, account, "");
            }
            idList.forEach(id -> {
                Node node = nodeMapper.queryById(id);
                if (node != null) {
                    zs.add(new FileSource(node.getFileName(),
                            new File(FILE_BLOCKS, node.getFilePath())));
                }
            });
            ZipUtil.pack(zs.toArray(new ZipEntrySource[0]), f);
            return zipname;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 迭代生成ZIP文件夹单元，将一个文件夹内的文件和文件夹也进行打包
     */
    private static void addFoldersToZipEntrySourceArray(String fid, List<ZipEntrySource> zs, String account,
                                                        String parentPath) {
        FolderMapper folderMapper = SpringUtils.getBean(FolderMapper.class);
        NodeMapper nodeMapper = SpringUtils.getBean(NodeMapper.class);
        Folder f = folderMapper.queryById(fid);
        if (f != null && ConfigureReader.instance().accessFolder(f, account)) {
            String originFoldername = f.getFolderName();
            String folderName = originFoldername;
            if (nodeMapper.queryByParentFolderId(f.getFolderParent()).parallelStream().anyMatch((e) -> e.getFileName().equals(originFoldername))) {
                folderName = folderName + "_与文件重名" + UUID.randomUUID().toString().replaceAll("-", "");
            }
            String thisPath = folderName + "/";
            zs.add(new ZipEntrySource() {

                @Override
                public String getPath() {
                    return thisPath;
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return null;
                }

                @Override
                public ZipEntry getEntry() {
                    return new ZipEntry(thisPath);
                }
            });
            String[] folders = folderMapper.queryByParentId(fid).parallelStream().map(Folder::getFolderId)
                    .toArray(String[]::new);
            for (String cf : folders) {
                addFoldersToZipEntrySourceArray(cf, zs, account, thisPath);
            }
            List<Node> nodes = nodeMapper.queryByParentFolderId(fid);
            for (Node n : nodes) {
                zs.add(new FileSource(thisPath + n.getFileName(), new File(FILE_BLOCKS, n.getFilePath())));
            }
        }
    }
}
