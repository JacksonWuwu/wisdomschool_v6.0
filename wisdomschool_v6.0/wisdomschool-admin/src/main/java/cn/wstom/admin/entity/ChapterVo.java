package cn.wstom.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dws
 * @date 2019/03/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChapterVo extends Chapter {

    private static final long serialVersionUID = -5813278430349364957L;
    private List<Chapter> children = new LinkedList<>();
}
