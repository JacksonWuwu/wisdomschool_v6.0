package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.Course;
import cn.wstom.exam.entity.MyTitleType;
import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.entity.TitleType;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.MyTitleTypeService;
import cn.wstom.exam.service.TitleTypeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 我的题型 信息操作处理
 *
 * @author hzh
 * @date 20190307
 */
@Controller
@RequestMapping("/school/onlineExam/myTitleType")
public class MyTitleTypeController extends BaseController {
    private final String prefix = "school/onlineExam/myTitleType";

    @Autowired
    private MyTitleTypeService myTitleTypeService;

    @Autowired
    private TitleTypeService titleTypeService;


    @RequestMapping("/findByCidAndTid")
    @ResponseBody
    List<MyTitleType>  findByCidAndTid(@RequestParam("tid") String tid,@RequestParam("cid")String cid){
        return myTitleTypeService.findByCidAndTid(cid,tid);
    }

    @RequestMapping("/saveMyTitleType")
    @ResponseBody
    Boolean saveMyTitleType(@RequestBody MyTitleType mty) throws Exception {
        return myTitleTypeService.save(mty);
    }


    @GetMapping("/find//{cid}")
    public String toList(@PathVariable("cid") String cid, ModelMap mmap) {
        List<MyTitleType> list = myTitleTypeService.findByCid(cid);
        mmap.put("TitleType", list);
        mmap.put("cid", cid);
        return prefix + "/list";
    }


    /**
     * 查询我的题型列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@Param("cid") String cid) {
        startPage();
        MyTitleType myTitleType = new MyTitleType();
        myTitleType.setCreateId(getUser().getUserAttrId());
        myTitleType.setCid(cid);
        List<MyTitleType> list = myTitleTypeService.list(myTitleType);
        return wrapTable(list);
    }

    /**
     * 新增我的题型
     */

    @GetMapping("/add/{id}")
    public String toAdd(@PathVariable("id") String id, ModelMap modelMap) {
        List<TitleType> titleType = titleTypeService.list(new TitleType());
        Course course = adminService.getCourseById(id);
        modelMap.put("titleType", titleType);
        modelMap.put("course", course);
        return prefix + "/add";
    }

    /**
     * 新增保存我的题型
     */

    @Log(title = "我的题型", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(MyTitleType myTitleType) throws Exception {
        myTitleType.setCreateBy(getLoginName());
        myTitleType.setCreateId(getUser().getUserAttrId());
        return toAjax(myTitleTypeService.save(myTitleType));
    }


    /**
     * 修改我的题型
     */
    @GetMapping("/edit/{id}")

    public String toEdit(@PathVariable("id") Integer id, ModelMap mmap) {

        MyTitleType myTitleType = myTitleTypeService.getById(id);
        TitleType titleType = titleTypeService.getById(myTitleType.getPublicTitleId());
        mmap.put("titleType", titleType);
        mmap.put("myTitleType", myTitleType);
        return prefix + "/edit";
    }

    /**
     * 修改保存我的题型
     */

    @Log(title = "我的题型", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(MyTitleType myTitleType) throws Exception {
        myTitleType.setUpdateBy(getLoginName());
        myTitleType.setUpdateId(getUser().getUserAttrId());
        return toAjax(myTitleTypeService.update(myTitleType));
    }

    /**
     * 删除我的题型
     */

    @Log(title = "我的题型", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(myTitleTypeService.removeById(ids));
    }

}
