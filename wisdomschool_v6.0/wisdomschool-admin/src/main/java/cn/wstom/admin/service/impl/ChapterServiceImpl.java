package cn.wstom.admin.service.impl;


import cn.wstom.admin.service.impl.BaseServiceImpl;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.common.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 课程章节 服务层实现
 *
 * @author dws
 * @date 20190223
 */
@Service
public class ChapterServiceImpl extends BaseServiceImpl<ChapterMapper, Chapter>
        implements ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    @Resource
    private ChapterResourceMapper chapterResourceMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private RecourseMapper recourseMapper;

    @Override
    public List<Chapter> selectList(Chapter chapter){
        return chapterMapper.selectList(chapter);
    }

    @Override
    public List<Chapter> selectListByPreviewid(Chapter chapter) {
        return chapterMapper.selectListByPreviewid(chapter);
    }

    @Override
    public List<Map<String, Object>> selectListByPreviewidTwo(Chapter chapter) {
        List<Map<String, Object>> trees = null;
        if (chapter!=null) {
            List<Chapter> courseList = chapterMapper.selectListByPreviewid(chapter);
            trees = getTrees(courseList);
        }
        trees.forEach( node -> {
            String chapterId = (String) node.get("id");
            if (StringUtil.isNotNull(chapterId)) {
                ChapterResource resource = new ChapterResource();
                resource.setCid(chapterId);
                List<ChapterResource> chapterResources = chapterResourceMapper.selectList(resource);
                if (!chapterResources.isEmpty()) {
                    chapterResources.forEach(cr -> {
                        if (cr.getRid() != null && !cr.getRid().equals("")) {
                            Recourse r = recourseMapper.selectById(cr.getRid());
                            if (r != null) {
                                cr.setAttrId(r.getAttrId());
                            }
                        }
                    });
                }
                node.put("resource", chapterResources);
            }
        });

        return trees;
    }

    @Override
    public List<Map<String, Object>> getCourseChapterTree(String cid) {
        List<Map<String, Object>> trees = null;
        Chapter chapter = new Chapter();
        chapter.setCid(cid);
        if (StringUtil.isNotNull(cid)) {
            List<Chapter> courseList = chapterMapper.selectList(chapter);
            trees = getTrees(courseList);
        }
        return trees;
    }

    @Override
    public List<Map<String, Object>> getCourseTree(String cid) {
        List<Map<String, Object>> trees = null;
        Course course = new Course();
        course.setId(cid);
        if (StringUtil.isNotNull(cid)) {
            List<Course> courseList = courseMapper.selectList(course);
            trees = getCourseTrees(courseList);
        }
        return trees;
    }

    @Transactional
    @Override
    public List<Map<String, Object>> getCourseChapterInfoTree(String cid) {
        List<Map<String, Object>> trees = null;
        Chapter chapter = new Chapter();
        chapter.setCid(cid);
        if (StringUtil.isNotNull(cid)) {
            List<Chapter> courseList = chapterMapper.selectList(chapter);
            trees = getTrees(courseList);
        }
        trees.forEach( node -> {
            String chapterId = (String) node.get("id");
            if (StringUtil.isNotNull(chapterId)) {
                ChapterResource resource = new ChapterResource();
                resource.setCid(chapterId);
                List<ChapterResource> chapterResources = chapterResourceMapper.selectList(resource);
                if (!chapterResources.isEmpty()) {
                    chapterResources.forEach(cr -> {
                        if (cr.getRid() != null && !cr.getRid().equals("")) {
                            Recourse r = recourseMapper.selectById(cr.getRid());
                            if (r != null) {
                                cr.setAttrId(r.getAttrId());
                            }
                        }
                    });
                }

                node.put("resource", chapterResources);
            }
        });

        return trees;
    }

    @Override
    public PageVo<Comment> getCourseCommentListPage(String courseId, String userId, String createTime, String orderBy, String order, String chapterId, int pageNum, int rows, Integer userType) {
        PageVo<Comment> pageVo = new PageVo<>(pageNum);
        pageVo.setRows(rows);
        List<Comment> list = new ArrayList<>();

        pageVo.setList(chapterMapper.getCourseCommentList(courseId, userId, createTime, chapterId, orderBy, order, pageVo.getOffset(), pageVo.getRows(), userType));
        pageVo.setCount(chapterMapper.getCourseCommentCount(courseId, userId, createTime, chapterId));
        return pageVo;
    }

    /**
     * 对象转树
     *
     * @param courseList 课程列表
     * @return 课程树数据类型
     */
    private List<Map<String, Object>> getTrees(List<Chapter> courseList) {
        List<Map<String, Object>> trees = new ArrayList<>();
        courseList.sort(Comparator.naturalOrder());
        for (Chapter c : courseList) {
            Map<String, Object> chapterMap = new HashMap<>(16);
            chapterMap.put("id", c.getId());
            chapterMap.put("pId", c.getPid());
            chapterMap.put("name", c.getTitle() + " " + c.getName());
            chapterMap.put("title", c.getTitle() + " " + c.getName());
            chapterMap.put("checked", false);
            trees.add(chapterMap);
        }
        return trees;
    }
    private List<Map<String, Object>> getCourseTrees(List<Course> courseList) {
        List<Map<String, Object>> trees = new ArrayList<>();
        courseList.sort(Comparator.naturalOrder());
        for (Course c : courseList) {
            Map<String, Object> CourseMap = new HashMap<>(16);
            CourseMap.put("id", c.getId());
            CourseMap.put("name",  c.getName());
            CourseMap.put("checked", false);
            trees.add(CourseMap);
        }
        return trees;
    }
}
