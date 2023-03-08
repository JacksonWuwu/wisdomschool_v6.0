package cn.wstom.exam.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 *@Author lzj
 *@Description word导出
 **/
public class WordUtilOne {
    public void exportWord(XWPFDocument document, HttpServletResponse response, String fileName,File file){
//        response.setHeader("Content-Disposition","inline;fileName="+ fileName+".docx");
//        response.setContentType("application/msword");
        try {
            FileOutputStream outputStream = new FileOutputStream(file+"\\" + new String(fileName.getBytes("UTF-8"), "UTF-8")+".docx");
//        FileOutputStream outputStream = new FileOutputStream(file+"/" + new String(fileName.getBytes("UTF-8"), "UTF-8")+".docx");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName),"utf-8");
            OutputStream os = null;
            os = response.getOutputStream();
            document.write(outputStream);
            outputStreamWriter.flush();
            outputStreamWriter.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
