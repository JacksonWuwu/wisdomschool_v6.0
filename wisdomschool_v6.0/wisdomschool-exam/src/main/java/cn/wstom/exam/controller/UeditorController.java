package cn.wstom.exam.controller;


import cn.wstom.exam.constants.Data;
import cn.wstom.exam.constants.JwtConstant;
import cn.wstom.exam.constants.StorageConstants;
import cn.wstom.exam.exception.ApplicationException;
import cn.wstom.exam.exception.FileNameLimitExceededException;
import cn.wstom.exam.utils.FileUploadUtils;
import cn.wstom.exam.utils.FileUtils;
import cn.wstom.exam.utils.JwtUtils;
import cn.wstom.exam.utils.ServletUtils;
import com.baidu.ueditor.ActionEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "wstom.file")
public class UeditorController {
    @Value("${storage-url}")
    private String PathPre;



    @Autowired
    JwtUtils jwtUtils;

    @RequestMapping("/ueditor")
    public void config( HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String rootPath =  ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/js/plugins/ueditor/jsp";
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, String> uploadFile(MultipartFile upfile, HttpServletRequest request){
        String fileName = "";
        try {
            String token = ServletUtils.getRequest().getHeader(JwtConstant.tokenHeader);
            String userId = jwtUtils.getUserIdFromToken(token);
            Data result = FileUploadUtils.upload(userId,StorageConstants.STORAGE_THUMBNAIL, upfile, false, FileUtils.allowImage);
            fileName = (String) result.get(StorageConstants.FILE_ID);
        } catch (IOException | FileNameLimitExceededException | ApplicationException e) {
            e.printStackTrace();
        }
        Map<String,String> map = new HashMap<>();
        //返回给页面 用于回显的url，这里指向下面的showImage Action，此anction返回图片的输出流
        String path ="/storage/showCondensedPicture?fileId="+fileName;
        map.put("state", "SUCCESS");
        map.put("url", path);
        map.put("title", fileName);
        map.put("original", fileName);
        return map;
    }

    @RequestMapping("/showImage")
    @ResponseBody
    public String showImage(HttpServletRequest request,HttpServletResponse response){
        try {
            String fileName = request.getParameter("fileName");
            String revision = request.getParameter("revision");
            String realpath ="/storage/showCondensedPicture?fileId="+fileName;
            if(revision != null){
                realpath ="/storage/showCondensedPicture?fileId="+fileName;
            }
            InputStream inputStream = new FileInputStream(new File(realpath));
            byte[] bs = new byte[1024];
            int len = 0;
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            OutputStream os = response.getOutputStream();
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
            os.flush();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
