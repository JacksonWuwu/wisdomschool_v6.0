package cn.wstom.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;

/**
 * @author dws
 * @date 2019/03/26
 */
@Controller
@Getter
@Setter
@ToString
public class ChapterResourceVo extends ChapterResource {
    private static final long serialVersionUID = 2786888352158140255L;

    private Recourse recourse;
}
