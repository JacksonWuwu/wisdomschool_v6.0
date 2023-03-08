package cn.wstom.student.controller.account;


import cn.wstom.student.constants.Constants;
import cn.wstom.student.constants.Data;
import cn.wstom.student.constants.StorageConstants;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.SysUser;
import cn.wstom.student.exception.ApplicationException;
import cn.wstom.student.exception.FileNameLimitExceededException;
import cn.wstom.student.utils.FileUploadUtils;
import cn.wstom.student.utils.FileUtils;
import cn.wstom.student.utils.StringUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author liniec
 * @date 2020/01/28 09:29
 *  学生管理个人话题
 */
@Controller
@RequestMapping("/account/info")
public class AccountInfoController extends BaseController {



   // @ApiOperation("修改头像")
    @PostMapping(value = "/thumb", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Data updateThumb(@RequestParam(value = "file") MultipartFile file) throws Exception {
        String filename = null;
        if (!file.isEmpty()) {
            try {
                //保存图片
                Data result = FileUploadUtils.upload(StorageConstants.STORAGE_THUMBNAIL, file, false, FileUtils.allowImage);
                System.out.println(result);
                if (StringUtil.nvl(result.get(Data.RESULT_KEY_CODE).toString(), "").equals(Constants.FAILURE)) {
                    return result;
                }
                filename = (String) result.get(StorageConstants.FILE_ID);
            } catch (FileNameLimitExceededException | IOException | ApplicationException e) {
                return Data.error(e.getMessage());
            }
        }

        SysUser user = getUser();
        user.setAvatar(filename);
        return toAjax(adminService.updateUser(user));
    }
}
