package cn.wstom.storage.boot;


import cn.wstom.storage.WstomStorageApp;
import cn.wstom.storage.filesystemmanager.FileSystemManager;
import cn.wstom.storage.filesystemmanager.pojo.FolderView;
import cn.wstom.storage.server.enumeration.Constants;
import cn.wstom.storage.server.model.Folder;
import cn.wstom.storage.server.model.Node;
import cn.wstom.storage.server.util.ConfigureReader;
import cn.wstom.storage.server.util.FileNodeUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * <h2>命令模式启动器</h2>
 * <p>
 * 该启动器将以命令模式启动，请执行静态build()方法开启界面并初始化服务器引擎。
 * </p>
 *
 * @author dws
 * @version 1.0
 */
public class ConsoleRunner {
    private final Logger LOGGER = LoggerFactory.getLogger(ConsoleRunner.class);

    private static ConsoleRunner consoleRunner;
    private static WstomStorageApp wstomStorageApp;
    private static FolderView currentFolder;
    private Scanner reader;

    private static ScheduledExecutorService worker;

    private ConsoleRunner() {
    }

    /**
     * <h2>以命令模式运行</h2>
     * <p>
     * 启动命令模式操作并初始化服务器引擎，该方法将返回本启动器的唯一实例。
     * </p>
     * <p>
     * 注：此处使用先加载资源文件再启动的应用的方式，故不能用类路径的方式加载系统资源
     * </p>
     *
     * @return kohgylw..mc.ConsoleRunner 本启动器唯一实例
     */
    public static ConsoleRunner build() {
        ConsoleRunner.wstomStorageApp = new WstomStorageApp();
        worker = new ScheduledThreadPoolExecutor(5,
                new BasicThreadFactory.Builder().namingPattern("loader-schedule-pool-%d").daemon(true).build());

        if (ConfigureReader.instance().getPropertiesStatus() == 0) {
            if (!ConsoleRunner.wstomStorageApp.started()) {
                ConsoleRunner.wstomStorageApp.start();
            }
        }
        return ConsoleRunner.consoleRunner;
    }

    /**
     * 得到指定ID的文件夹视图
     */
    private void getFolderView(String fid) throws SQLException {
        currentFolder = FileSystemManager.getInstance().getFolderView(fid);
    }

    /**
     * 打印当前文件夹内容（ls）
     */
    private void showCurrentFolder() {
        try {
            currentFolder = FileSystemManager.getInstance().getFolderView(currentFolder.getCurrent().getFolderId());
        } catch (SQLException e) {
            openFolderError();
        }
        List<Folder> fls = currentFolder.getFolders();
        int index = 1;
        for (Folder f : fls) {
            System.out.println("--" + index + " [文件夹] " + f);
            index++;
        }
        List<Node> fs = currentFolder.getFiles();
        for (Node f : fs) {
            System.out.println("--" + index + " [文件] " + f.getFileName());
            index++;
        }
    }

    /**
     * 进入某一文件夹，可以输入文件夹名或是前方编号（例如cd foo或是cd --1）
     */
    private void gotoFolder(String fname) {
        try {
            currentFolder = FileSystemManager.getInstance().getFolderView(currentFolder.getCurrent().getFolderId());
            try {
                String fid = getSelectFolderOrFileId(fname);
                if (fid != null) {
                    getFolderView(fid);
                }
                return;
            } catch (NoSuchElementException e) {
            }
            LOGGER.error("错误：该文件夹不存在或其不是一个文件夹（" + fname + "）。");
        } catch (SQLException e) {
            openFolderError();
        }
    }

    /**
     * 根据路径获取文件夹ID，例如“/ROOT/foo/bar”这样的，如果不能被解析则返回null，否则返回文件夹ID
     */
    public Object getPath(String path) {
        if (path.startsWith("/ROOT")) {
            String[] paths = path.split("/");
            try {
                String parent = "null";
                for (int i = 1; i < paths.length - 1; i++) {
                    String folderName = paths[i];
                    parent = FileSystemManager.getInstance().getFoldersByParentId(parent).parallelStream()
                            .filter((e) -> e.getFolderName().equals(folderName)).findFirst().get().getFolderId();
                }
                String fname = paths[paths.length - 1];
                List<Folder> folders = FileSystemManager.getInstance().getFoldersByParentId(parent);
                if (path.endsWith("/") || folders.parallelStream().anyMatch((e) -> e.getFolderName().equals(fname))) {
                    return folders.parallelStream().filter((e) -> e.getFolderName().equals(fname)).findFirst().get();
                } else {
                    return FileSystemManager.getInstance().selectNodesByFolderId(parent).parallelStream()
                            .filter((e) -> e.getFileName().equals(fname)).findFirst().get();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 根据用户输入的序号或者名称得到相应的ID
     */
    private String getSelectFolderOrFileId(String fname) {
        if ("../".equals(fname) || "..".equals(fname)) {
            if (currentFolder.getCurrent().getFolderId().equals("root")) {
                return "root";
            } else {
                return currentFolder.getCurrent().getFolderParent();
            }
        }
        if ("./".equals(fname) || Constants.SEPARATOR_DOT.equals(fname)) {
            return currentFolder.getCurrent().getFolderId();
        }
        if (fname.startsWith("--")) {
            int index = Integer.parseInt(fname.substring(2));
            try {
                if (index >= 1 && index <= currentFolder.getFolders().size()) {
                    return currentFolder.getFolders().get(index - 1).getFolderId();
                } else {
                    return currentFolder.getFiles().get(index - currentFolder.getFolders().size() - 1).getFileId();
                }
            } catch (Exception e) {

            }
            return null;
        }
        try {
            return currentFolder.getFolders().parallelStream().filter((e) -> e.getFolderName().equals(fname))
                    .findFirst().get().getFolderId();
        } catch (NoSuchElementException e) {
            try {
                return currentFolder.getFiles().parallelStream().filter((m) -> m.getFileName().equals(fname))
                        .findFirst().get().getFileId();
            } catch (NoSuchElementException e2) {
            }
        }
        return null;
    }

    /**
     * 导出一个文件或文件夹（直接应用的简化版）
     */
    private void doImport(String[] args) {
        // 针对简化命令（只有1个参数），认为要将整个ROOT导出至某位置
        try {
            FileNodeUtil.initNodeTableToDataBase();
            String importTarget;
            String importPath;
            Object path;
            File target;
            if (args.length == 2) {
                importTarget = args[1];
                importPath = "/ROOT";
                path = FileSystemManager.getInstance().selectFolderById("root");
                target = new File(importTarget);
            } else if (args.length == 3 || args.length == 4) {
                importPath = args[1];
                importTarget = args[2];
                target = new File(importTarget);
                path = getPath(importPath);
            } else {
                LOGGER.error("错误：导入失败，必须指定导入目标（示例：“-import /ROOT/ /home/your/import/file.txt”）。");
                return;
            }
            if (!(path instanceof Folder)) {
                LOGGER.error("错误：导入位置（" + importPath + "）必须是一个文件夹（示例：“/ROOT”）。");
                return;
            }
            String folderId = ((Folder) path).getFolderId();
            if (!target.exists()) {
                LOGGER.error("错误：导入失败，要导入的文件或文件夹不存在（" + importTarget + "）。");
                return;
            }
            File[] files = new File[]{target};
            String type;
            if (FileSystemManager.getInstance().hasExistsFilesOrFolders(files, folderId) > 0) {
                if (args.length == 4) {
                    switch (args[3]) {
                        case "-C":
                            type = FileSystemManager.COVER;
                            break;
                        case "-B":
                            type = FileSystemManager.BOTH;
                            break;
                        default:
                            LOGGER.error("错误：导入失败，导入路径下存在相同的文件或文件夹（请使用以下参数：[-C]覆盖 [-B]保留两者）。");
                            return;
                    }
                } else if (args.length == 2) {
                    type = FileSystemManager.COVER;
                } else {
                    LOGGER.error("错误：导入失败，导入路径下存在相同的文件或文件夹（请增加以下参数：[-C]覆盖 [-B]保留两者）。");
                    return;
                }
            } else {
                type = "cancel";
            }
            if (FileSystemManager.getInstance().importFrom(files, folderId, type)) {
                return;
            } else {
                LOGGER.error("错误：导入失败，可能导入全部文件。");
            }
        } catch (Exception e1) {
            LOGGER.error("错误：导入失败，出现意外错误。");
        }
    }

    /**
     * 导入一个文件或文件夹
     */
    private void doImport(String fpath) {
        File f = new File(fpath);
        if (!f.exists()) {
            LOGGER.error("错误：无法导入文件或文件夹，该目标不存在（" + fpath + "），请重新检查。");
            return;
        }
        String targetFolder = currentFolder.getCurrent().getFolderId();
        String type = "";
        File[] importFiles = new File[]{f};
        try {
            if (FileSystemManager.getInstance().hasExistsFilesOrFolders(importFiles, targetFolder) > 0) {
                System.out.println("提示：该路径下已经存在同名文件或文件夹（" + f.getName() + "），您希望？[C]取消 [V]覆盖 [B]保留两者");
                q:
                while (true) {
                    String command = new String(reader.nextLine().getBytes("UTF-8"), "UTF-8");
                    switch (command) {
                        case "C":
                            LOGGER.info("导入被取消。");
                            return;
                        case "V":
                            type = FileSystemManager.COVER;
                            break q;
                        case "B":
                            type = FileSystemManager.BOTH;
                            break q;
                        default:
                            System.out.println("请输入C、V或B：");
                            break;
                    }
                }
            }
            LOGGER.info("正在导入，请稍候...");
            ProgressListener pl = new ProgressListener();
            worker.execute(pl);
            FileSystemManager.getInstance().importFrom(importFiles, targetFolder, type);
            pl.c = false;
            LOGGER.info("导入完成。");
        } catch (Exception e) {
            LOGGER.error("错误：无法导入该文件（或文件夹），请重试。");
        }
    }

    /**
     * 导出一个文件或文件夹（直接应用的简化版）
     */
    private void doExport(String[] args) {
        // 针对简化命令（只有1个参数），认为要将整个ROOT导出至某位置
        try {
            FileNodeUtil.initNodeTableToDataBase();
            String exportTarget;
            String exportPath;
            Object target;
            File path;
            if (args.length == 2) {
                exportPath = args[1];
                exportTarget = "/ROOT";
                path = new File(exportPath);
                target = FileSystemManager.getInstance().selectFolderById("root");
            } else if (args.length == 3 || args.length == 4) {
                exportTarget = args[1];
                exportPath = args[2];
                target = getPath(exportTarget);
                path = new File(exportPath);
            } else {
                LOGGER.error("错误：导出失败，必须指定导出路径（示例：“-export /ROOT/ /home/your/export/folder”）。");
                return;
            }
            if (!path.isDirectory()) {
                LOGGER.error("错误：导出路径（" + exportPath + "）必须是一个文件夹。");
                return;
            }
            if (target == null) {
                LOGGER.error("错误：导出失败，要导出的文件或文件夹不存在（" + exportTarget + "）。");
                return;
            }
            String[] foldersId;
            String[] filesId;
            String type;
            if (target instanceof Node) {
                foldersId = new String[]{};
                filesId = new String[]{((Node) target).getFileId()};
            } else if (target instanceof Folder) {
                foldersId = new String[]{((Folder) target).getFolderId()};
                filesId = new String[]{};
            } else {
                LOGGER.error("错误：导出失败，出现意外错误。");
                return;
            }
            if (FileSystemManager.getInstance().hasExistsFilesOrFolders(foldersId, filesId, path) > 0) {
                if (args.length == 4) {
                    switch (args[3]) {
                        case "-C":
                            type = FileSystemManager.COVER;
                            break;
                        case "-B":
                            type = FileSystemManager.BOTH;
                            break;
                        default:
                            LOGGER.error("错误：导出失败，导出路径下存在相同的文件或文件夹（请使用以下参数：[-C]覆盖 [-B]保留两者）。");
                            return;
                    }
                } else if (args.length == 2) {
                    type = FileSystemManager.COVER;
                } else {
                    LOGGER.error("错误：导出失败，导出路径下存在相同的文件或文件夹（请增加以下参数：[-C]覆盖 [-B]保留两者）。");
                    return;
                }
            } else {
                type = "cancel";
            }
            if (FileSystemManager.getInstance().exportTo(foldersId, filesId, path, type)) {
                return;
            } else {
                LOGGER.error("错误：导出失败，可能导出全部文件。");
            }
        } catch (Exception e1) {
            LOGGER.error("错误：导出失败，出现意外错误。");
        }
    }

    /**
     * 导出一个文件或文件夹
     */
    private void doExport(String command) {
        String[] args = command.split(" ");
        String id;
        String path;
        if (args.length == 1) {
            path = args[0];
            id = currentFolder.getCurrent().getFolderId();
        } else if (args.length == 2) {
            id = getSelectFolderOrFileId(args[0]);
            path = args[1];
        } else {
            LOGGER.error("错误：导出失败，输入参数不正确。");
            return;
        }
        File targetPath = new File(path);
        if (targetPath.isDirectory()) {
            if (id == null) {
                LOGGER.error("错误：导出失败，该文件或文件夹不存在（" + args[0] + "）。");
                return;
            }
            try {
                String[] foldersId = null;
                String[] filesId = null;
                String type = "";
                if (id.equals(currentFolder.getCurrent().getFolderId())
                        || currentFolder.getFolders().parallelStream().anyMatch((e) -> e.getFolderId().equals(id))) {
                    foldersId = new String[]{id};
                    filesId = new String[]{};
                } else if (currentFolder.getFiles().parallelStream().anyMatch((e) -> e.getFileId().equals(id))) {
                    foldersId = new String[]{};
                    filesId = new String[]{id};
                } else {
                    LOGGER.error("错误：要导出的文件（或文件夹）不合法，只允许在当前文件夹内的选择（" + path + "）。");
                    return;
                }
                if (FileSystemManager.getInstance().hasExistsFilesOrFolders(foldersId, filesId, targetPath) > 0) {
                    System.out.println("提示：该路径下已经存在同名文件或文件夹（" + targetPath.getName() + "），您希望？[C]取消 [V]覆盖 [B]保留两者");
                    q:
                    while (true) {
                        String command2 = new String(reader.nextLine().getBytes("UTF-8"), "UTF-8");
                        switch (command2) {
                            case "C":
                                LOGGER.info("导出被取消。");
                                return;
                            case "V":
                                type = FileSystemManager.COVER;
                                break q;
                            case "B":
                                type = FileSystemManager.BOTH;
                                break q;
                            default:
                                System.out.println("请输入C、V或B：");
                                break;
                        }
                    }
                }
                LOGGER.info("正在导出，请稍候...");
                ProgressListener pl = new ProgressListener();
                worker.execute(pl);
                FileSystemManager.getInstance().exportTo(foldersId, filesId, targetPath, type);
                pl.c = false;
                LOGGER.info("导出完成。");
            } catch (Exception e1) {
                LOGGER.error("错误：无法导出该文件（或文件夹），请重试。");
            }
        } else {
            LOGGER.error("错误：导出失败，导出的目标必须是一个文件夹（" + path + "）。");
        }
    }

    /**
     * 删除某一文件或文件夹
     */
    private void doDelete(String fname) {
        try {
            currentFolder = FileSystemManager.getInstance().getFolderView(currentFolder.getCurrent().getFolderId());
        } catch (SQLException e2) {
            openFolderError();
            return;
        }
        String id = getSelectFolderOrFileId(fname);
        try {
            if (currentFolder.getFolders().parallelStream().anyMatch((e) -> e.getFolderId().equals(id))) {
                if (confirmOpt("确认要删除该文件夹么？")) {
                    LOGGER.info("正在删除文件夹，请稍候...");
                    ProgressListener pl = new ProgressListener();
                    worker.execute(pl);
                    if (FileSystemManager.getInstance().delete(new String[]{id}, new String[]{})) {
                        LOGGER.info("删除完毕。");
                    } else {
                        LOGGER.info("删除失败，可能未能删除该文件夹，请重试。");
                    }
                    pl.c = false;
                } else {
                    LOGGER.info("已取消删除。");
                }
                return;
            }
            if (currentFolder.getFiles().parallelStream().anyMatch((e) -> e.getFileId().equals(id))) {
                if (confirmOpt("确认要删除该文件么？")) {
                    LOGGER.info("正在删除文件，请稍候...");
                    ProgressListener pl = new ProgressListener();
                    worker.execute(pl);
                    if (FileSystemManager.getInstance().delete(new String[]{}, new String[]{id})) {
                        LOGGER.info("删除完毕。");
                    } else {
                        LOGGER.info("删除失败，可能未能删除该文件，请重试。");
                    }
                    pl.c = false;
                } else {
                    LOGGER.info("已取消删除。");
                }
                return;
            }
        } catch (Exception e1) {
            LOGGER.error("错误：无法删除文件，请重试。");
        }
        LOGGER.error("错误：该文件或文件夹不存在（" + fname + "）。");
    }

    /**
     * 对于关键操作（主要就是删除）进行进一步确认，避免误操作。
     */
    private boolean confirmOpt(String tip) {
        System.out.println("提示：" + tip + " [Y/N]");
        while (true) {
            String command;
            try {
                System.out.print("> ");
                command = new String(reader.nextLine().getBytes("UTF-8"), "UTF-8");
                switch (command) {
                    case "Y":
                        return true;
                    case "N":
                        return false;
                    default:
                        System.out.println("必须正确输入Y或N：");
                        break;
                }
            } catch (UnsupportedEncodingException e) {
                System.out.println("错误：无法识别输入的内容，重新输入。");
            }
        }
    }

    /**
     * 这是一个专门用于显示进度的内部类，在耗时操作中应使用它，每秒显示一次进度状态。
     */
    class ProgressListener implements Runnable {

        private boolean c = true;

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            while (c) {
                System.out.println(FileSystemManager.message);
                System.out.println("当前进度：" + FileSystemManager.per + "%");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }

    }

    /**
     * 如果一个文件夹不可访问（例如被删除），则提示下列错误信息
     */
    private void openFolderError() {
        LOGGER.error("错误：无法读取指定文件夹，是否返回根目录？[Y/N]");
        System.out.print("> ");
        String command = new String(reader.nextLine().getBytes(Charset.forName("UTF-8")), Charset.forName("UTF-8"));
        while (true) {
            switch (command) {
                case "Y":
                    try {
                        currentFolder = FileSystemManager.getInstance().getFolderView("root");
                    } catch (SQLException e1) {
                        LOGGER.error("错误：无法读取根目录，请尝试重新打开文件管理系统或重启。");
                    }
                    return;
                case "N":
                    LOGGER.error("错误：无法读取指定文件夹，请重试。");
                    return;
                default:
                    System.out.println("请输入Y或N：");
                    break;
            }
        }
    }
}
