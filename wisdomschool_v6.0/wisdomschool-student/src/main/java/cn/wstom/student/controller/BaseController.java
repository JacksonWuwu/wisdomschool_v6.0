package cn.wstom.student.controller;


import cn.wstom.student.constants.Data;
import cn.wstom.student.constants.JwtConstant;
import cn.wstom.student.entity.PageDomain;
import cn.wstom.student.entity.SysUser;
import cn.wstom.student.entity.TableDataInfo;
import cn.wstom.student.entity.TableSupport;
import cn.wstom.student.feign.AdminService;
import cn.wstom.student.utils.JwtUtils;
import cn.wstom.student.utils.ServletUtils;
import cn.wstom.student.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 控制层基类
 *
 * @author dws
 */
@Component
@Slf4j
public abstract class BaseController {
    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @Autowired
    public JwtUtils jwtUtils;

    @Autowired
    public AdminService adminService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    public String getUrl() {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }

    /**
     * 加密密码
     *
     * @param username
     * @param password
     * @param salt
     * @return
     */
    public static String encryptPassword(String username, String password, String salt) {
        return new Md5Hash(username + password + salt).toHex();
    }


    /**
     * 默认当前页码
     */
    protected int defaultPageNum = 1;

    /**
     * 默认页面大小
     */
    protected int defaultPageSize = 8;

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        try {
            PageDomain pageDomain = TableSupport.buildPageRequest();
            Integer pageNum = pageDomain.getPageNum();
            Integer pageSize = pageDomain.getPageSize();
            if (StringUtil.isNotNull(pageNum) && StringUtil.isNotNull(pageSize)) {
                String orderBy = pageDomain.getOrderBy();
                PageHelper.startPage(pageNum, pageSize, orderBy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Page loadNumData(Integer pageNum) {
        Page page = null;
        try {
            if (StringUtil.isNotNull(pageNum)) {
                page = PageHelper.startPage(pageNum, defaultPageSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    /**
     * 响应请求分页数据
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo wrapTable(List<?> list) {
        PageInfo pageInfo = new PageInfo(list);
        return wrapTable(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    /**
     * 响应请求分页数据
     */
    protected TableDataInfo wrapTable(List<?> list, Long total) {
        if (!Optional.ofNullable(list).isPresent()) {
            list = Lists.newArrayList();
        }
        PageInfo pageInfo = new PageInfo(list);
        return wrapTable(list, total, pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    /**
     * 响应请求分页数据
     */
    protected TableDataInfo wrapTable(List<?> list, Long total, int pageNum, int pageSize, int pages) {
        TableDataInfo rspData = new TableDataInfo();
        if (list == null) {
            list = new ArrayList<>(0);
        }
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(total);
        rspData.setPageNum(pageNum);
        rspData.setPageSize(pageSize);
        rspData.setPages(pages);
        return rspData;
    }
    /**
     * 生成随机盐
     */
    public static String randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        return hex;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected Data toAjax(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     *
     * @param result 执行结果
     * @return 操作结果
     */
    protected Data toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 返回成功
     */
    public Data success() {
        return Data.success();
    }

    /**
     * 返回失败消息
     */
    public Data error() {
        return Data.error();
    }

    /**
     * 返回成功消息
     */
    public Data success(String message) {
        return Data.success(message);
    }

    /**
     * 返回失败消息
     */
    public Data error(String message) {
        return Data.error(message);
    }

    /**
     * 返回错误码消息
     */
    public Data error(int code, String message) {
        return Data.error(code, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtil.format("redirect:%1$s", url);
    }

    public String getToken(){
        String token = ServletUtils.getRequest().getParameter("token");
        if (!"".equals(token)&&token!=null){
            return ServletUtils.getRequest().getParameter("token");
        };
        return ServletUtils.getRequest().getHeader(JwtConstant.tokenHeader);
    }
    public SysUser getUser() {
        String userId = jwtUtils.getUserIdFromToken(getToken());
        return adminService.getUserById(userId);
    }

    public String getUserId() {
        return getUser().getId();
    }

    public String getLoginName() {
        return getUser().getLoginName();
    }
    //密码判断

    public static Boolean isPwdTure(SysUser loginUser,SysUser dbuser) {
        String s = new Md5Hash(loginUser.getLoginName() + loginUser.getPassword() + dbuser.getSalt()).toHex();
        System.out.println(s);
        return dbuser.getPassword().equals(s);
    }
}
