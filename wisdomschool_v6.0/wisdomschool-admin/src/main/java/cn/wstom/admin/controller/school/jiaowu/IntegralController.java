package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 积分
 *
 * @author dws
 * @date 2019/03/22
 */

@Controller
@RequestMapping("/jiaowu/integral")
public class IntegralController extends BaseController {
    private String prefix = "/school/jiaowu/integral";

    @Autowired
    private IntegralService integralService;
    @Autowired
    private IntegralDetailService integralDetailService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SysUserService sysUserService;



    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }


    @GetMapping("/detail")
    public String toIntegralDetail() {
        return prefix + "/detail";
    }

    /**
     * 查询规则列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Integral integral) {
        startPage();
        List<Integral> list = integralService.list(integral);
        return wrapTable(list);
    }

    /**
     * 查询积分明细列表
     */

    @PostMapping("/detail/list")
    @ResponseBody
    public TableDataInfo detail(IntegralDetail integral) {
        startPage();
        List<IntegralDetail> list = integralDetailService.list(integral);
        return wrapTable(list);
    }

    /**
     * 查询积分明细列表
     */

    @PostMapping("/detail/remove")
    @Log(title = "规则明细", actionType = ActionType.DELETE)
    @ResponseBody
    public Data detailremove(String ids) throws Exception {
        return toAjax(integralDetailService.removeById(ids));
    }


    /**
     * 新增规则
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 新增保存积分规则
     */

    @Log(title = "积分规则", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(Integral integral) throws Exception {
        //保存积分规则信息
        integral.setCreateBy(getLoginName());
        return toAjax(integralService.save(integral));
    }

    /**
     * 修改积分规则
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap map) {
        Integral integral = integralService.getById(id);
        map.put("integral", integral);
        return prefix + "/edit";
    }

    /**
     * 修改保存积分规则
     */

    @Log(title = "积分规则", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(Integral integral) throws Exception {
        integral.setUpdateBy(getLoginName());
        return toAjax(integralService.update(integral));
    }

    @Log(title = "积分规则", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            return toAjax(integralService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @RequestMapping("/getsort")
    @ResponseBody
    public List<SysUser> getsort(String studentId) {
        Student student = studentService.getStudentById(studentId);
        Student student1 = new Student();
        System.out.println(studentId);
        student1.setCid(student.getCid());
        List<Student> studentList = studentService.studentList(student1);
        List<SysUser> sysUserList = new ArrayList<>();
        for (int i = 0; i < studentList.size(); i++) {
            SysUser sysUser = new SysUser();
            sysUser.setUserAttrId(studentList.get(i).getId());
            sysUser.setUserType(2);
            List<SysUser> sysUserList2 = new ArrayList<>();
            sysUserList2 = sysUserService.list(sysUser);

            if(sysUserList2.size()!=0){
                for (int j = 0; j <sysUserList2.size() ; j++) {
                    sysUserList.add(sysUserList2.get(j));
                }
            }
        }
        for (int i = 0; i < sysUserList.size(); i++) {
            int sum = 0;
            IntegralDetail integralDetail = new IntegralDetail();
            integralDetail.setUserId(sysUserList.get(i).getId());
            List<IntegralDetail> integralDetailList = integralDetailService.list(integralDetail);
            for (int j = 0; j < integralDetailList.size(); j++) {

                sum += integralDetailList.get(j).getCredit();
            }
            sysUserList.get(i).setCredit(sum);

        }

        sysUserList.sort((x, y) -> Double.compare(Double.parseDouble(String.valueOf(y.getCredit())), Double.parseDouble(String.valueOf(x.getCredit()))));
        return sysUserList;

    }

    /*  Lin_    */
    @PostMapping("/export")
    @ResponseBody
    public Data export(Integral integral) {
        try {
            List<Integral> list = integralService.list(integral);
            ExcelUtil<Integral> util = new ExcelUtil<>(Integral.class);
            return util.exportExcel(list, "integral");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }
}
