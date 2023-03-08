package cn.wstom.student.mapper;



import cn.wstom.student.entity.PptChapterUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PptChapterUserMapper extends BaseMapper<PptChapterUser> {
    PptChapterUser getBySidRid(PptChapterUser pptChapterUser);
    List<PptChapterUser> listbycrids(List<String> crids);
    List<PptChapterUser> listbycridsAndsid(PptChapterUser pptChapterUser);
}
