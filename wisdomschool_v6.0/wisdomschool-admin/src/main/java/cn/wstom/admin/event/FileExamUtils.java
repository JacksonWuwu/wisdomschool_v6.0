package cn.wstom.admin.event;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileExamUtils {
    public static final String SERVICE_NAME="animalhouse_admin-course1.0-master10.11";
    public static final String ZIP_PATH="zip/";
    public static final String WORD_PATH="word/";
    public static String getPath(String path,String name){
        int length=path.indexOf(SERVICE_NAME);
        System.out.println("length"+length);
        String newPath=path.substring(0,length)+name;
        System.out.println(newPath);
        File file = new File(newPath);
        //根据文件夹路径创建文件夹
        if(!file.exists()){
            file.mkdirs();
        }
        return newPath;
    }
    /**
     * 下载zip包
     * @param file
     * @param response
     * @return
     */
    public static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response

            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            //如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
