package cn.wstom.storage.server.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件名格式工具
 *
 * @author HP
 */
public class TextFormatUtil {
    private final Pattern folderPattern = Pattern.compile("^[0-9a-zA-Z_\u4e00-\u9fff]+$");
    private final Pattern filePattern = Pattern.compile("[|\\/*<> \"]+");
    private static TextFormatUtil textFormatUtil;

    public static TextFormatUtil instance() {
        return TextFormatUtil.textFormatUtil;
    }

    /**
     * 检验文件夹名
     * @param folderName 文件夹名
     * @return 检验结果
     */
    public boolean matcherFolderName(String folderName) {
        Matcher m = folderPattern.matcher(folderName);
        return m.matches();
    }

    public boolean matcherFileName(String fileName) {
        Matcher m = filePattern.matcher(fileName);
        return !m.find();
    }

    static {
        TextFormatUtil.textFormatUtil = new TextFormatUtil();
    }
}
