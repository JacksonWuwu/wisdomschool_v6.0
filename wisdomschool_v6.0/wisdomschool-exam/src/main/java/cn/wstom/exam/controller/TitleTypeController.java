package cn.wstom.exam.controller;


import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.entity.TitleType;
import cn.wstom.exam.service.TitleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台试题类型
 *
 * @author hzh
 */
@Controller
@RequestMapping("/school/onlineExam/titleType")
public class TitleTypeController extends BaseController {
    private String prefix = "school/onlineExam/titleType";
    @Autowired
    private TitleTypeService titleTypeService;

    /**
     * 查所有类型
     * @param titleType
     * @return
     */
    @ResponseBody
    @RequestMapping("/findTitleType")
    public List<TitleType> findTitleType(TitleType titleType) {
        List<TitleType> allSem = new ArrayList<TitleType>();
        allSem =  titleTypeService.list(titleType);
        return allSem;
    }


    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TitleType titleType) {
        startPage();
        List<TitleType> list = titleTypeService.list(titleType);
        return wrapTable(list);
    }




}
