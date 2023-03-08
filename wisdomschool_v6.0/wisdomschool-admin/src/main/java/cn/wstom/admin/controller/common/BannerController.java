package cn.wstom.admin.controller.common;

import java.io.IOException;
import java.util.List;

import cn.wstom.admin.exception.ApplicationException;
import cn.wstom.admin.utils.FileUploadUtils;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.constant.StorageConstants;
import cn.wstom.admin.controller.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.common.exception.file.FileNameLimitExceededException;
import cn.wstom.common.utils.FileUtils;
import cn.wstom.common.utils.StringUtil;
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
import org.springframework.web.multipart.MultipartFile;

/**
* 轮播图片 信息操作处理
*
* @author dws
* @date 20200131
*/
@Controller
@RequestMapping("/jiaowu/banner")
public class BannerController extends BaseController {
    private String prefix = "/common/banner";

    @Autowired
    private BannerService bannerService;


    @GetMapping("/toList")
    public String toList() {
        return prefix + "/list";
    }

    /**
    * 查询轮播图片列表
    */
    @PostMapping("/bannerList")
    @ResponseBody
    public List<Banner> bannerList(Banner banner) {
        startPage();
        return bannerService.list(banner);
    }

    /**
     * 查询轮播图片列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Banner banner) {
        startPage();
        List<Banner> list = bannerService.list(banner);
        return wrapTable(list);
    }

    /**
    * 新增轮播图片
    */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
    * 新增保存轮播图片
    */

    @Log(title = "轮播图片")
    @PostMapping("/add")
    @ResponseBody
    public Data add(Banner banner, MultipartFile thumbnail) throws Exception  {

        String filename = null;
        if (!thumbnail.isEmpty()) {
            try {
                //保存图片
                Data result = FileUploadUtils.upload(getUserId(),StorageConstants.STORAGE_THUMBNAIL, thumbnail, false, FileUtils.allowImage);
                if (StringUtil.nvl(result.get(Data.RESULT_KEY_CODE).toString(), "").equals(Constants.FAILURE)) {
                    return result;
                }
                filename = (String) result.get(StorageConstants.FILE_ID);
            } catch (FileNameLimitExceededException | IOException | ApplicationException e) {
                return Data.error(e.getMessage());
            }
        }

        banner.setCreateBy(getUserId());
        banner.setCover(filename);
        return toAjax(bannerService.save(banner));
    }

    /**
    * 修改轮播图片
    */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, ModelMap mmap) {
        Banner banner = bannerService.getById(id);
        mmap.put("banner", banner);
        return prefix + "/edit";
    }

    /**
    * 修改保存轮播图片
    */

    @Log(title = "轮播图片")
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(Banner banner, MultipartFile thumbnail) throws Exception {
        String filename = null;
        if (!thumbnail.isEmpty()) {
            try {
                //保存图片
                Data result = FileUploadUtils.upload(getUserId(),StorageConstants.STORAGE_THUMBNAIL, thumbnail, false, FileUtils.allowImage);
                System.out.println(result);
                if (StringUtil.nvl(result.get(Data.RESULT_KEY_CODE).toString(), "").equals(Constants.FAILURE)) {
                    return result;
                }
                filename = (String) result.get(StorageConstants.FILE_ID);
            } catch (FileNameLimitExceededException | IOException | ApplicationException e) {
                return Data.error(e.getMessage());
            }
        }

        banner.setCreateBy(getUserId());
        banner.setCover(filename);

        return toAjax(bannerService.update(banner));
    }

    /**
    * 删除轮播图片
    */

    @Log(title = "轮播图片")
    @PostMapping( "/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(bannerService.removeById(ids));
    }
}
