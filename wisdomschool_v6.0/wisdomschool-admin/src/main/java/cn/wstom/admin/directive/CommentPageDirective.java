package cn.wstom.admin.directive;


import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/12
 */
@Component
public class CommentPageDirective extends BaseTemplateDirective {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

    }
//    private final ArticleService articleService;
/*    private final CourseService courseService;
    private final TopicCommentService commentService;
    private final SysUserService userService;

    @Autowired
    public CommentPageDirective(*//*ArticleService articleService,*//*
                                CourseService courseService,
                                TopicCommentService commentService,
                                SysUserService userService) {
//        this.articleService = articleService;
        this.courseService = courseService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "commentPage";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        // 获取页面的参数
        //文章id
        Integer type = handler.getInteger("type");
        String typeId = handler.getString("typeId");
        //用户id
        String userId = handler.getString("userId");
        //添加时间
        String createTime = handler.getString("createTime");

        String orderBy = handler.getString("orderBy");

        String parentId = handler.getString("parentId");

        String order = handler.getString("order");
        //当前页数
        int p = handler.getInteger("p", 1);
        //每页记录数
        int rows = handler.getInteger("rows", 10);
        // 获取文件的分页
        try {

            //父级信息
            PageVo<TopicComment> pageVo = new PageVo<>(0);
            if (type == ForumConstants.INFO_TYPE_ARTICLE) {
//                pageVo = articleService.getArticleCommentListPage(
//                        typeId, userId, createTime, parentId, orderBy, order, p, rows);
            } else {
                pageVo = courseService.getCourseCommentListPage(
                        typeId, userId, createTime, parentId, orderBy, order, p, rows);
            }
//            List<TopicComment> list = pageVo.getList();
            List<TopicComment> list = new LinkedList<>();

            Set<String> userIds = new LinkedHashSet<>();
            List<ArticleCommentVo> commentVos = fillChildrenCommen(list, userIds);

            //先填充评论信息再处理用户信息
            fillUser(commentVos, userIds);

            PageVo<ArticleCommentVo> page = new PageVo<>(pageVo.getPageNum());
            BeanUtils.copyProperties(pageVo, page);
            page.setList(commentVos);


            handler.put("commentpage", page).render();
        } catch (Exception e) {
            e.printStackTrace();
            handler.put(MSG, e.getMessage()).render();
        }
    }

    *//**
     * @param list 父评论信息
     * @return
     *//*
    private List<ArticleCommentVo> fillChildrenCommen(List<TopicComment> list, Set<String> userIds) {

        if (list.isEmpty()) {
            return new LinkedList<>();
        }

        //获取子级评论
        Set<String> parentIds = Sets.newLinkedHashSet();
        list.forEach(l -> parentIds.add(l.getId()));
        List<TopicComment> childrenCommentList = commentService.listByPids(Lists.newArrayList(parentIds));

        //将子评论转为vo类型
        List<ArticleCommentVo> childrenCommentVos = new LinkedList<>();
        childrenCommentList.forEach(l -> {
            userIds.add(l.getUserId());
            ArticleCommentVo vo = new ArticleCommentVo();
            BeanUtils.copyProperties(l, vo);
            childrenCommentVos.add(vo);
        });

        //将父评论转为vo类型
        List<ArticleCommentVo> parentCommentVos = new LinkedList<>();
        list.forEach(l -> {
            userIds.add(l.getUserId());
            ArticleCommentVo vo = new ArticleCommentVo();
            BeanUtils.copyProperties(l, vo);
            parentCommentVos.add(vo);
        });

        //构建评论结构
        parentCommentVos.forEach(l ->
                l.setChildren(
                        childrenCommentVos.stream().filter(articleComment -> l.getId()
                                .equals(articleComment.getParentId())).sorted(Comparator
                                .comparing(BaseEntity::getCreateTime)).collect(Collectors.toList())
                ));
        return parentCommentVos;
    }

    private void fillUser(List<ArticleCommentVo> list, Set<String> userIds) {
        Map<String, SysUser> userMap = userService.mapByIds(Lists.newArrayList(userIds));

        list.forEach(l -> {
            SysUser pu = userMap.get(l.getUserId());
            l.setUser(pu);
            List<ArticleCommentVo> children = l.getChildren();
            if (children != null) {
                children.forEach(c -> {
                    c.setUser(userMap.get(c.getUserId()));
                    c.setParentUser(pu);
                });
            }
        });

    }*/

}
