package cn.wstom.face.controller;

import cn.wstom.face.constants.Data;
import cn.wstom.face.constants.JwtConstant;
import cn.wstom.face.entity.FaceInfo;
import cn.wstom.face.entity.UserExam;
import cn.wstom.face.feign.ExamService;
import cn.wstom.face.service.FaceInfoService;
import cn.wstom.face.utils.JwtUtils;
import cn.wstom.face.utils.ServletUtils;

import com.lzw.face.FaceHelper;
import com.seetaface2.model.FaceLandmark;
import com.seetaface2.model.RecognizeResult;
import com.seetaface2.model.SeetaRect;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 人脸识别
 * @author Yaosh
 * @version 1.0
 * @commpany 星瑞国际
 * @date 2020/8/20 14:34
 * @return
 */
@RequestMapping("/face")
@RestController
public class SeetafaceController {

    @Autowired
    private  JwtUtils jwtUtils;

    @Autowired
     private   FaceInfoService faceInfoService;

    @Autowired
    private  ExamService examService;


    @RequestMapping("/compare")
    public Data compare(@RequestParam("file") String file,@RequestParam("paperId") String paperId) throws IOException {
        System.out.println(file);
        System.out.println(paperId);
        String userId = jwtUtils.getUserIdFromToken(getToken());
        if(!"".equals(paperId)){
           UserExam userExam = new UserExam();
           userExam.setTestPaperOneId(paperId);
           List<UserExam> userExams = examService.selectUserExamListBase(userExam);
           int flag=0;
           for (UserExam exam : userExams) {
               if (exam.getUserId().equals(userId)) {
                   flag = 1;
                   break;
               }
           }
           if (flag==0){
               return  Data.error(1,"该用户不存在");
           }
        }
        FaceInfo faceInfo = new FaceInfo();
        faceInfo.setUserId(userId);
        List<FaceInfo> faceInfoList = faceInfoService.getFaceInfoList(faceInfo);
         List<Float> results = new LinkedList<>();
        Map<String,Object> retMap = new HashMap<>();
        byte[] decode = Base64.decode(base64Process(file));
        for (FaceInfo info : faceInfoList) {
            byte[] dbFaceInfo = Base64.decode(base64Process(info.getFaceInfo()));
            float compare = FaceHelper.compare(dbFaceInfo, decode);
            System.out.println("相似度"+compare);
            retMap.put("dbImage", info.getFaceInfo());
            results.add(compare);
        }

        retMap.put("result",results);
        for (int i = 0; i < results.size(); i++) {
            if(results.get(i)>0.5){
               retMap.put("dbImage", faceInfoList.get(i).getFaceInfo());
               retMap.put("compare", results.get(i));
                return Data.success(0,"识别成功",retMap);
            }
        }
        retMap.put("compare",findMaxByList(results));
        return  Data.success(1,"识别失败",retMap);
    }
    private static float findMaxByList(List<Float> arr) {
        float max = 0; // 最大值
        for (float item : arr) {
            if (item > max) { // 当前值大于最大值，赋值为最大值
                max = item;
            }
        }
        return max;
    }


    ///**
    // * 初始化人脸库
    // * @return
    // */
    //@RequestMapping("/init")
    //public Map<String,Object> init(){
    //    Map<String,Object> retMap = new HashMap<String,Object>();
    //    retMap.put("code",0);
    //    retMap.put("Message","初始化人脸库成功!");
    //    Collection<File> files = FileUtils.listFiles(new File("H:\\seetaface\\test"), new String[]{"jpg", "png"}, false);
    //    for (File file : files) {
    //        String key = file.getName();
    //        try {
    //            FaceHelper.register(key, FileUtils.readFileToByteArray(file));
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
    //    return retMap;
    //}

    /**
     * 初始化人脸库
     * @return
     */
    @RequestMapping("/register")
    public Data register(@RequestParam("file") String file){
        String userId = jwtUtils.getUserIdFromToken(getToken());
        FaceInfo faceInfo = new FaceInfo();
        faceInfo.setUserId(userId);
        List<FaceInfo> faceInfoList = faceInfoService.getFaceInfoList(faceInfo);
        if(faceInfoList.size()>=4){
            return Data.success("数据库已有数据");
        }
        faceInfo.setFaceInfo(file);
        int i = faceInfoService.insertFaceInfo(faceInfo);
        if (i>0){
            return Data.success("注册成功");
        }else {
            return Data.error("注册失败");
        }
        //try {
        //    //FaceHelper.register(decode);
        //} catch (IOException e) {
        //    retMap.put("code",-1);
        //    retMap.put("Message",e);
        //    e.printStackTrace();
        //}


    }
    @RequestMapping("/search")
    public Map<String,Object> search(@RequestParam("file") String file) throws IOException {
        byte[] decode = Base64.decode(base64Process(file));
        Map<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("code",0);
        retMap.put("Message","Success");
        long l = System.currentTimeMillis();
        RecognizeResult search = FaceHelper.search(decode);
        long timeDiff = (System.currentTimeMillis() - l);
        retMap.put("timeDiff",timeDiff);
        retMap.put("result",search);
        return retMap;
    }

    @RequestMapping("/detect")
    public Map<String,Object> detect() throws IOException {
        Map<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("code",0);
        retMap.put("Message","Success");
        long l = System.currentTimeMillis();
        SeetaRect[] seetaRects = FaceHelper.detect(FileUtils.readFileToByteArray(new File("H:\\seetaface\\test\\2.jpg")));
        long timeDiff = (System.currentTimeMillis() - l);
        retMap.put("timeDiff",timeDiff);
        retMap.put("seetaRects",seetaRects);
        return retMap;
    }

    @RequestMapping("/detectLandmark")
    public Map<String,Object> detectLandmark() throws IOException {
        Map<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("code",0);
        retMap.put("Message","Success");
        long l = System.currentTimeMillis();
        FaceLandmark faceLandmark = FaceHelper.detectLandmark(ImageIO.read(new File("H:\\seetaface\\test\\2.jpg")));
        long timeDiff = (System.currentTimeMillis() - l);
        retMap.put("timeDiff",timeDiff);
        retMap.put("faceLandmark",faceLandmark);
        return retMap;
    }

    /**
     * 清除人脸库
     * @return
     */
    @RequestMapping("/clear")
    public Map<String,Object> clear() throws IOException {
        Map<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("code",0);
        retMap.put("Message","清除人脸库成功!");
        FaceHelper.clear();
        return retMap;
    }


    private String base64Process(String base64Str) {
        if (!StringUtils.isEmpty(base64Str)) {
            String photoBase64 = base64Str.substring(0, 30).toLowerCase();
            int indexOf = photoBase64.indexOf("base64,");
            if (indexOf > 0) {
                base64Str = base64Str.substring(indexOf + 7);
            }

            return base64Str;
        } else {
            return "";
        }
    }

    public String getToken(){
        String token = ServletUtils.getRequest().getParameter("token");
        if (!"".equals(token)&&token!=null){
            return ServletUtils.getRequest().getParameter("token");
        };

        return ServletUtils.getRequest().getHeader(JwtConstant.tokenHeader);
    }

}
