package cn.wstom.admin.controller.school.teacher;

import cn.wstom.admin.async.AsyncIntegral;

import cn.wstom.admin.async.AsyncManager;
import cn.wstom.admin.cache.Environment;
import cn.wstom.admin.exception.ApplicationException;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.config.Global;
import cn.wstom.common.constant.StorageConstants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.exception.file.FileNameLimitExceededException;
import cn.wstom.common.support.Convert;
import cn.wstom.common.utils.FileUtils;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dws
 * @date 2019/02/24
 */
@Controller
@RequestMapping("/recourse")
public class RecourseController extends BaseController {
    private static final String STORAGE_URL_PREFIX = Global.getConfig("wstom.file.storage-url");

    private final RecourseService recourseService;

    private final RestOperations restOperations;

    private final TeacherCourseService teacherCourseService;

    private final RecourseTypeService recourseTypeService;

    /**
     * 视频资源索引
     */
    private final String VIDEO_PREFIX = "/school/teacher/video";
    /**
     * 视频资源索引
     */
    private final String FILE_PREFIX = "/school/teacher/file";


    @Autowired
    public RecourseController(RecourseService recourseService, RestOperations restOperations, TeacherCourseService teacherCourseService, RecourseTypeService recourseTypeService) {
        this.recourseService = recourseService;
        this.restOperations = restOperations;
        this.teacherCourseService = teacherCourseService;
        this.recourseTypeService = recourseTypeService;
    }



    @RequestMapping(value = "/{recourseId}")
    @ResponseBody
    Recourse getRecourseById(@PathVariable(value = "recourseId")String recourseId){
        return recourseService.getById(recourseId);
    }
    @RequestMapping(value = "/recourseTypeMap")
    @ResponseBody
    public Map<String, RecourseType> recourseTypeMap(@RequestBody RecourseType recourseType){
        return recourseTypeService.map(recourseType);
    }

    @RequestMapping(value = "/recourseTypeList")
    @ResponseBody
    List<RecourseType> recourseTypeList(@RequestBody RecourseType recourseType){
        return recourseTypeService.list(recourseType);
    }

    @RequestMapping(value = "/recourseList")
    @ResponseBody
    List<Recourse> recourseList(@RequestBody Recourse recourse,
                                @RequestParam(required = false,value = "pageNum")Integer pageNum,
                                @RequestParam(required = false,value = "pageSize")Integer pageSize,
                                @RequestParam(required = false,value = "orderBy")String orderBy){
        PageHelper.startPage(pageNum, pageSize, orderBy);
        return recourseService.list(recourse);
    }

    @RequestMapping(value = "/getRecourseByStuId/{studentId}")
    @ResponseBody
    List<Recourse> getRecourseByStuId(@PathVariable(value = "studentId")String studentId){
        return recourseService.getResources(studentId);
    };


    @RequestMapping(value = "/recourseUpdate")
    @ResponseBody
    boolean recourseUpdate(@RequestBody Recourse recourse) throws Exception {
        return recourseService.update(recourse);
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 课程id
     * @return 视频教学资源页面
     */

    @GetMapping("/videoresource/{cid}")
    public String toVideoRecourse(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return VIDEO_PREFIX + "/list";
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 课程id
     * @return 视频教学资源页面
     */

    @GetMapping("/source/list/{cid}")
    public String toRecourseList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        modelMap.put("storage", STORAGE_URL_PREFIX);
        return "/school/teacher/resource/list";
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 课程id
     * @return 视频教学资源页面
     */

    @PostMapping("/source/{cid}/list")
    @ResponseBody
    public TableDataInfo recourseList(@PathVariable String cid, ModelMap modelMap, Recourse recourse) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);

        recourse.setTcId(teacherCourse.getId());
        startPage();
        List list = recourseService.list(recourse);
        return wrapTable(list);
    }

    /**
     * 获取视频教学资源页面
     * @param cid 课程id
     * @return 视频教学资源页面
     */

    @GetMapping("/source/{cid}/add")
    public String toRecourseAdd(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        System.out.println("cid:"+cid);
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        RecourseType recourseType = new RecourseType();
        recourseType.setTcId(teacherCourse.getId());
        List<RecourseType> list = recourseTypeService.list(recourseType);
        modelMap.put("type", list);
        System.out.println(list);
        System.out.println(teacherCourse.getId());
        System.out.println(teacherCourse.getCid());
        System.out.println(teacherCourse.getTid());
        modelMap.put("tcid", teacherCourse.getId());
        return "/school/teacher/resource/add";
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 课程id
     * @return 视频教学资源页面
     */

    @PostMapping("/source/{cid}/add")
    public Data recourseAdd(@PathVariable String cid, Recourse recourse) throws Exception {
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        recourse.setTcId(teacherCourse.getId());
        recourse.setCreateBy(getLoginName());
        recourse.setCount(0);
        return toAjax(recourseService.save(recourse));
    }

    /***
     *初始化教师课程资源类型
     */
    @GetMapping("/chushihua")
    @ResponseBody
    public String chushihua() {
        TeacherCourse tc = new TeacherCourse();
        List<TeacherCourse> tcs = teacherCourseService.list(tc);
        for ( int i=0 ; i<tcs.size();i++){
            System.out.println(  "tcid...."+tcs.get(i).getId());
            String tcid =tcs.get(i).getId();
            List<RecourseType> rts = recourseTypeService.findByTCid(tcid);
            if(rts.size()==0){
                RecourseType rt =new RecourseType();
                rt.setTcId(tcid);
                rt.setName("课程PPT");
                rt.setOrder("1");
                try {
                    recourseTypeService.save(rt);
                } catch (Exception e) {
                    System.out.println("初始化课程PPT失败");
                    e.printStackTrace();
                }
                RecourseType rt1 =new RecourseType();
                rt1.setTcId(tcid);
                rt1.setName("课程视频");
                rt1.setOrder("2");
                try {
                    recourseTypeService.save(rt1);
                } catch (Exception e) {
                    System.out.println("初始化课程视频失败");
                    e.printStackTrace();
                }
                RecourseType rt2 =new RecourseType();
                rt2.setTcId(tcid);
                rt2.setName("辅助资源");
                rt2.setOrder("3");
                try {
                    recourseTypeService.save(rt2);
                } catch (Exception e) {
                    System.out.println("初始化辅助资源失败");
                    e.printStackTrace();
                }
            };
        }
        return "sususus";
    }

    /**
     * 获取教学资源类型页面
     *
     * @param cid 课程id
     * @return 视频教学资源页面
     */

    @GetMapping("/type/{cid}")
    public String recourseType(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return "/school/teacher/resourcetype/list";
    }

    /**
     * 获取教学资源类型页面
     *
     * @param cid 课程id
     * @return 视频教学资源页面
     */

    @PostMapping("/type/{cid}/list")
    @ResponseBody
    public TableDataInfo recourseTypeList(@PathVariable String cid, ModelMap modelMap, RecourseType recourseType) {
        modelMap.put("id", cid);
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        modelMap.put("tcid", teacherCourse.getId());

        recourseType.setTcId(teacherCourse.getId());
        return wrapTable(recourseTypeService.list(recourseType));
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 课程id
     * @return 视频教学资源页面
     */

    @GetMapping("/type/{cid}/add")
    public String toRecourseTypeAdd(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        modelMap.put("tcid", teacherCourse.getId());
        return "/school/teacher/resourcetype/add";
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 课程id
     * @return 视频教学资源页面
     */

    @PostMapping("/type/{cid}/add")
    @ResponseBody
    public Data recourseTypeAdd(@PathVariable String cid, RecourseType recourseType) throws Exception {
        recourseType.setCreateBy(getLoginName());
        return toAjax(recourseTypeService.save(recourseType));
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 课程id
     * @return 视频教学资源页面
     */

    @PostMapping("/type/{cid}/remove")
    @ResponseBody
    public Data recourseTypeRemove(@PathVariable String cid, String ids) throws Exception {
        return toAjax(recourseTypeService.removeById(ids));
    }

    /**
     * 修改资源类型
     */

    @GetMapping("/type/{cid}/edit/{id}")

    public String toRecourseTypeEdit(@PathVariable("id") String id, ModelMap mmap) {
        RecourseType recourseType = recourseTypeService.getById(id);
        System.out.println("跳转"+recourseType);
        mmap.put("recourseType", recourseType);
        return "/school/teacher/resourcetype/edit";
    }

    /**
     * 修改保存资源类型
     */


    @Log(title = "资源类型", actionType = ActionType.UPDATE)
    @PostMapping("/type/{cid}/edit")
    @ResponseBody
    public Data edit(RecourseType recourseType) throws Exception {
        System.out.println("编辑");
        System.out.println(recourseType);
        return toAjax(recourseTypeService.update(recourseType));
    }
    /**
     * 获取文件教学资源页面
     *
     * @param cid 课程id
     * @return 文件教学资源页面
     */

    @GetMapping("/fileresource/{cid}")
    public String toFileRecourse(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return FILE_PREFIX + "/list";
    }

    /**
     * 添加视频资源
     *
     * @param recourse 资源
     * @return 添加结果
     */

    @PostMapping("/videoresource/add")
    @ResponseBody
    public Data addVideoRecourse(Recourse recourse) throws Exception {
        recourse.setCreateBy(getLoginName());
        System.out.println("recourse.getFileName()"+recourse.getFileName());
        try {
            if (FileUtils.checkFileType(recourse.getFileName(), FileUtils.allowVideo)) {
                recourse.setRecourseType(StorageConstants.RECOURSE_VIDEO);
                return toAjax(recourseService.save(recourse));
            }

            if (FileUtils.checkFileType(recourse.getFileName(), FileUtils.allowFile)) {
                recourse.setRecourseType(StorageConstants.RECOURSE_FILE);
                return toAjax(recourseService.save(recourse));
            }

            if (FileUtils.checkFileType(recourse.getFileName(), FileUtils.allowImage)) {
                recourse.setRecourseType(StorageConstants.RECOURSE_FILE);
                return toAjax(recourseService.save(recourse));
            }

            if (FileUtils.checkFileType(recourse.getFileName(), FileUtils.allowExt)) {
                recourse.setRecourseType(StorageConstants.RECOURSE_FILE);
                return toAjax(recourseService.save(recourse));
            }

            throw new Exception("添加失败，请检查资源格式");
        } catch (Exception e) {
            throw new Exception("添加失败，请检查资源格式");
        }
    }

    /**
     * 添加文件资源
     *
     * @param recourse 资源
     * @return 添加结果
     */

    @PostMapping("/fileresource/add")
    @ResponseBody
    public Data addFileRecourse(Recourse recourse) throws Exception {
        recourse.setCreateBy(getLoginName());
        FileUtils.checkFileType(recourse.getFileName(), FileUtils.allowFile);
        recourse.setRecourseType(StorageConstants.RECOURSE_FILE);
        return toAjax(recourseService.save(recourse));
    }

    /**
     * 获取视频教学资源添加页面
     *
     * @param cid 课程id
     * @return 教学资源添加页面路径
     */

    @GetMapping("/videoresource/add/{cid}")
    public String toVideoResourceAdd(@PathVariable String cid, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        modelMap.put("tcid", teacherCourse.getId());
        return VIDEO_PREFIX + "/add";
    }

    /**
     * 获取文件教学资源添加页面
     *
     * @param cid 课程id
     * @return 教学资源添加页面路径
     */

    @GetMapping("/fileresource/add/{cid}")
    public String toFileResourceAdd(@PathVariable String cid, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        modelMap.put("tcid", teacherCourse.getId());
        return FILE_PREFIX + "/add";
    }

    /**
     * 获取视频教学资源
     *
     * @param cid 课程id
     * @return 教学资源列表
     */

    @PostMapping("/videoresource/list/{cid}")
    @ResponseBody
    public TableDataInfo videoResourceList(@PathVariable String cid) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);

        Recourse recourse = new Recourse();
        recourse.setRecourseType(StorageConstants.RECOURSE_VIDEO);
        recourse.setTcId(teacherCourse.getId());
        startPage();
        List<Recourse> recourseList = recourseService.list(recourse);
        return wrapTable(recourseList);
    }

    /**
     * 获取文件教学资源
     *
     * @param courseId 课程id
     * @return 教学资源列表
     */

    @PostMapping("/fileresource/list/{courseId}")
    @ResponseBody
    public TableDataInfo fileResourceList(@PathVariable String courseId) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", courseId);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);

        Recourse recourse = new Recourse();
        recourse.setRecourseType(StorageConstants.RECOURSE_FILE);
        recourse.setTcId(teacherCourse.getId());
        startPage();
        List<Recourse> recourseList = recourseService.list(recourse);
        return wrapTable(recourseList);
    }
    /**
     * 删除视频教学资源
     *
     * @param ids 视频教学资源id
     * @return 教学资源列表
     */

    @PostMapping("/videoresource/remove")
    @ResponseBody
    public Data videoResourceRemove(String ids) throws Exception {
        return toAjax(recourseService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 删除视频教学资源
     *
     * @param ids 视频教学资源id
     * @return 教学资源列表
     */

    @PostMapping("/fileresource/remove")
    @ResponseBody
    public Data fileResourceRemove(String ids) throws Exception {
        return toAjax(recourseService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 删除对应课程资源
     *
     * @param rid
     * @return
     */

    @RequestMapping("/source/{cid}/remove/{rid}")
    @ResponseBody
    public Data sourceRemove(@PathVariable String rid) throws Exception {
        return toAjax(recourseService.removeByIds(Arrays.asList(Convert.toStrArray(rid))));
    }

    @RequestMapping({"/storageManager"})

    public String storageManager() {
        return "/common/recourse/manager/recourse";
    }

    @RequestMapping({"/getServerOS"})

    @ResponseBody
    public Data getServerOS() {/*restOperations*/
        return Data.success(Environment.getServerOS());
    }

    @RequestMapping("/folderView/get/{fid}")
    @ResponseBody
    public Data getFolderView(@PathVariable String fid) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(2);
        params.add("fid", fid);
        params.add("user", getUser());
        return restOperations.postForObject(STORAGE_URL_PREFIX + "/getFolderView", params, Data.class);
    }

    @PostMapping("/createFolder")
    @ResponseBody
    public Data createFolder(String pid, String fn, String fc) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(4);
        params.add("parentId", pid);
        params.add("folderName", fn);
        params.add("folderConstraint", fc);
        params.add("user", getUser());
        return restOperations.postForObject(STORAGE_URL_PREFIX + "/createFolder", params, Data.class);
    }

    @PostMapping("/renameFile")
    @ResponseBody
    public Data renameFile(String fid, String nn) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(2);
        params.add("fileId", fid);
        params.add("newFileName", nn);
        return restOperations.postForObject(STORAGE_URL_PREFIX + "/renameFile", params, Data.class);
    }

    @PostMapping("/renameFolder")
    @ResponseBody
    public Data renameFolder(String fid, String nn, String fc) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(2);
        params.add("folderId", fid);
        params.add("newName", nn);
        params.add("folderConstraint", fc);
        return restOperations.postForObject(STORAGE_URL_PREFIX + "/renameFolder", params, Data.class);
    }

    @PostMapping("/checkUploadFile")
    @ResponseBody
    public Data renameFolder(String fid, String[] n) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(2);
        params.add("folderId", fid);
        params.add("name", n);
        for (String s : n) {
            FileUtils.checkFileType(s, FileUtils.allowExt);
        }
        return restOperations.postForObject(STORAGE_URL_PREFIX + "/checkUploadFile", params, Data.class);
    }

    @PostMapping("/upload")
    @ResponseBody
    public Data upload(String fid, MultipartFile f) throws FileNameLimitExceededException, ApplicationException, IOException {
        return FileUploadUtils.upload(getUserId(),fid, f, true, FileUtils.allowExt);
    }

    @RequestMapping({"/deleteFile/{fid}"})
    @ResponseBody
    public Data deleteFile(@PathVariable String fid) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(1);
        params.add("fileId", fid);
        return restOperations.postForObject(STORAGE_URL_PREFIX + "/deleteFile", params, Data.class);
    }

    @RequestMapping({"/playVideo/{fid}"})
    @ResponseBody
    public Data playVideo(@PathVariable String fid) {
        System.out.println("fid "+fid);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(1);
        params.add("fileId", fid);
        Data result = restOperations.postForObject(STORAGE_URL_PREFIX + "/playVideo", params, Data.class);

        AsyncManager.async().execute(
                AsyncIntegral.recordIntegral(getUser(), "video_view",
                        "观看课程视频【" + result.get("data") + "】"));
//        return result.put("pf", STORAGE_URL_PREFIX);
        result.put("pf", STORAGE_URL_PREFIX);
        System.out.println(result);
        return result;
    }

    @RequestMapping({"/downloadFile/{fid}"})
    @ResponseBody
    public Data downloadFile(@PathVariable String fid) {
        Recourse recourse = recourseService.getById(fid);
        AsyncManager.async().execute(
                AsyncIntegral.recordIntegral(getUser(), "download_file",
                        "下载课件【" + fid + "】"));
        return Data.success().put("pf", STORAGE_URL_PREFIX).put("fid", recourse.getAttrId());
    }

    @RequestMapping({"/fileresource/review"})
    public String review() {
        return "/common/recourse/pdfview/web/viewer";
    }

    @RequestMapping({"/deleteFolder/{fid}"})
    @ResponseBody
    public Data deleteFolder(@PathVariable String fid) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(1);
        params.add("folderId", fid);
        return restOperations.postForObject(STORAGE_URL_PREFIX + "/deleteFolder", params, Data.class);
    }

    @RequestMapping({"/video/{fid}"})
    public String toPlayVideo(@PathVariable String fid, ModelMap modelMap) {
        Recourse recourse = recourseService.getById(fid);
        modelMap.put("fileId", recourse.getAttrId());
        return "/common/recourse/manager/video";
    }

    @RequestMapping({"/demo/{fid}"})
    public String toDemo(@PathVariable String fid, ModelMap modelMap) {
        Recourse recourse = recourseService.getById(fid);
        String path=OfficeUtil.demo(recourse.getFileName());
        modelMap.put("fileId", recourse.getAttrId());
        modelMap.put("path", path);
        System.out.println("path===----"+path);
        return "/common/recourse/manager/demo";
    }


    @RequestMapping({"/huaban"})
    public String huaban() {
        return "/common/recourse/manager/huaban";
    }

    @RequestMapping({"/video/file/{fid}"})
    public String toPlayVideoByFileId(@PathVariable String fid, ModelMap modelMap) {
        modelMap.put("fileId", fid);
        return "/common/recourse/manager/video";
    }

    @RequestMapping({"/file/{fid}"})
    public String toFileReviewer(@PathVariable String fid, ModelMap modelMap) {
        modelMap.put("fileId", fid);
        return "/js/recourse/pdfview/web/viewer";
    }

    @RequestMapping({"/pdfview"})
    public String toPdfView() {
        return "/common/recourse/pdfview/web/viewer";
    }



}
