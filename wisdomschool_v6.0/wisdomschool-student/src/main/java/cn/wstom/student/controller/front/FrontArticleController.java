package cn.wstom.student.controller.front;



import cn.wstom.student.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文章管理
 *
 * @author dws
 */
@Controller
@RequestMapping("/article")
public class FrontArticleController extends BaseController {
    /*private final String PREFIX = "/front/article";

    private final ArticleService articleService;

    private final FilterKeywordService filterKeywordService;

    private final ArticleCategoryService articleCategoryService;

    private final ArticleVotesService articleVotesService;
    private final SolrService solrService;
    private final SysUserService userService;
    private final TopicCommentService articleCommentService;


    @Autowired
    public ArticleController(ArticleService articleService,
                             FilterKeywordService filterKeywordService,
                             ArticleCategoryService articleCategoryService,
                             ArticleVotesService articleVotesService,
                             SolrService solrService, SysUserService userService, TopicCommentService articleCommentService) {
        this.articleService = articleService;
        this.filterKeywordService = filterKeywordService;
        this.articleCategoryService = articleCategoryService;
        this.articleVotesService = articleVotesService;
        this.solrService = solrService;
        this.userService = userService;
        this.articleCommentService = articleCommentService;
    }

    *//**
     * 文章列表
     *
     * @param id
     * @param p
     * @param modelMap
     * @return
     *//*
    @GetMapping(value = {"", "/{id}"})
    public String getArticleList(@PathVariable(value = "id", required = false) String id, @RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap) {
        ArticleCategory category = null;
        if (id != null) {
            category = articleCategoryService.findCategoryById(id, Constants.ARTICLE_CATEGORY_NORMAL);
            Assert.notNull(category, "非法参数");
        }
        System.out.println(id);
        modelMap.addAttribute("id", id);
        modelMap.addAttribute("category", category);
        modelMap.addAttribute("p", p);
        return PREFIX + "/index";
    }

    *//**
     * 文章详细页面
     *
     * @param id
     * @param p
     * @param modelMap
     * @return
     *//*
    @GetMapping(value = "/detail/{id}")
    public String detail(@PathVariable(value = "id") String id, @RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap) {

        //查询文章内容信息，由于缓存问题，统计要实时更新，所以统计和内容分开查询
        Article article = articleService.findArticleById(id);
        Assert.notNull(article, "非法参数");
        //违禁词过滤
        article.setTitle(filterKeywordService.doFilter(article.getTitle()));
        article.setContent(filterKeywordService.doFilter(article.getContent()));
        //查询统计信息
        ArticleCount count = articleService.findArticleCountById(article.getId());
        article.setCountDig(count.getCountDig());
        article.setCountBury(count.getCountBury());
        article.setCountView(count.getCountView());
        article.setCountComment(count.getCountComment());
        AsyncManager.async().execute(
                AsyncIntegral.recordIntegral(ShiroUtils.getUser(), "article_read", "阅读文章【" + article.getTitle() + "】"));
        modelMap.addAttribute("article", article);
        modelMap.addAttribute("typeId", article.getId());
        modelMap.addAttribute("p", p);
        modelMap.addAttribute("type", ForumConstants.INFO_TYPE_ARTICLE);
        return PREFIX + "/detail";
    }

    *//**
     * 文章详细页面
     *
     * @param id
     * @param modelMap
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = "/findArticleById/{id}")
    public Data findArticleById(@PathVariable(value = "id") String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");
        Map<String, Object> params = Maps.newLinkedHashMap();
        params.put("id", id);
        params.put("status", Constants.ARTICLE_CATEGORY_NORMAL);
        Article article = articleService.getOne(params);
        Assert.notNull(article, "该内容不存在！");
        return Data.success("", Lists.newArrayList(article.getContent()));
    }

    *//**
     * 添加文章
     *
     * @param modelMap
     * @return
     *//*
    @GetMapping(value = "/edit")
    public String getAddArticle(ModelMap modelMap) {
        return PREFIX + "/edit";
    }

    *//**
     * 保存添加文章
     *
     * @param article
     * @param result
     * @return
     *//*
    @PostMapping("/save")
    public String save(@Valid Article article, BindingResult result, RedirectAttributesModelMap modelMap) throws Exception {
        checkValid(result);
        article.setUserId(getUserId());
        int save = articleService.addArticle(article);

        if (save > 0) {
            AsyncManager.async().execute(
                    AsyncIntegral.recordIntegral(ShiroUtils.getUser(), "article_edit", "编辑文章【" + article.getTitle() + "】"));
        }

        //索引本条信息
        solrService.indexArticleId(article.getId());
        modelMap.addFlashAttribute("article", article);
        return save > 0 ? redirect("http://localhost:808/article/success") : PREFIX + "edit";
    }

    @RequestMapping("/success")
    public String doSuccess(Article article) {
        if (article == null || StringUtil.isBlank(article.getId())) {
            return PREFIX + "/edit";
        }
        return PREFIX + "/success";
    }

    *//**
     * 保存用户文章评论内容
     *
     * @param articleComment
     * @param result
     * @return
     *//*
    @PostMapping("/comment/submit")
    @ResponseBody
    public Data comment(@Valid TopicComment articleComment, BindingResult result) throws Exception {
        checkValid(result);
        articleComment.setType(ForumConstants.INFO_TYPE_ARTICLE);
        articleComment.setUserId(getUserId());
        int res = articleService.addArticleComment(articleComment);
        if (res > 0) {
            AsyncManager.async().execute(
                    AsyncIntegral.recordIntegral(ShiroUtils.getUser(), "article_comment", "评论文章，评论内容【" + articleComment.getContent() + "】"));
        }
        return toAjax(res);
    }

    *//**
     * 获取用户文章评论内容
     *
     * @param articleId
     * @return
     *//*
    @RequestMapping("/comments/{articleId}")
    @ResponseBody
    public Data articleComment(@PathVariable String articleId, HttpServletRequest request) throws Exception {
        int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", defaultPageSize);
        int pageNum = ServletRequestUtils.getIntParameter(request, "pageNum", defaultPageNum);

        //用户id
        String userId = ServletRequestUtils.getStringParameter(request, "userId");
        String createTime = ServletRequestUtils.getStringParameter(request, "createTime");
        String orderBy = ServletRequestUtils.getStringParameter(request, "orderBy");
        String order = ServletRequestUtils.getStringParameter(request, "order");

        String parentId = "0";

        //分页获取父级信息
        //分页数据量以父级数据量为基础
        PageVo<TopicComment> pageVo = articleService.getArticleCommentListPage(
                articleId, userId, createTime, parentId, orderBy, order, pageNum, pageSize);
        List<TopicComment> list = pageVo.getList();

        List<String> userIds = new LinkedList<>();
        List<ArticleCommentVo> commentVos = fillChildrenCommen(list, userIds);

        //先填充评论信息再处理用户信息
        fillUser(commentVos, userIds);

        PageVo<ArticleCommentVo> page = new PageVo<>(pageVo.getPageNum());
        BeanUtils.copyProperties(pageVo, page);
        page.setList(commentVos);
        return Data.success().put(Data.RESULT_KEY_DATA, page);
    }

    *//**
     *
     * @param list 父评论信息
     * @return
     *//*
    private List<ArticleCommentVo> fillChildrenCommen(List<TopicComment> list, List<String> userIds) {

        if (list.isEmpty()) {
            return new LinkedList<>();
        }

        //获取子级评论
        Set<String> parentIds = Sets.newLinkedHashSet();
        list.forEach(l -> parentIds.add(l.getId()));
        List<TopicComment> childrenCommentList = articleCommentService.listByPids(Lists.newArrayList(parentIds));

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

    private void fillUser(List<ArticleCommentVo> list, List<String> userIds) {
        Map<String, SysUser> userMap = userService.mapByIds(userIds);

        list.forEach(l -> {
            l.setUser(userMap.get(l.getUserId()));
            List<ArticleCommentVo> children = l.getChildren();
            if (children != null) {
                children.forEach(c -> {
                    c.setUser(userMap.get(c.getUserId()));
                    c.setParentUser(userMap.get(c.getReplyUserId()));
                });
            }
        });

    }

    *//**
     * 修改文章
     *
     * @param id
     * @param modelMap
     * @return
     *//*
    @GetMapping(value = "/edit/{id}")
    public String getEditArticle(@PathVariable(value = "id") String id, ModelMap modelMap) {
        Map<String, Object> params = Maps.newLinkedHashMap();
        params.put("status", Constants.ARTICLE_CATEGORY_NORMAL);
        params.put("id", id);
        Article article = articleService.getOne(params);

        Assert.notNull(article, "非法参数");
        Assert.isTrue(getUserId().equals(article.getUserId()), "只能修改属于自己的文章！");

        //article.setContent(StringEscapeUtils.escapeHtml4(article.getContent()));
        modelMap.addAttribute("article", article);
        return PREFIX + "/edit";
    }

    *//**
     * 保存修改文章
     *
     * @param article
     * @param result
     * @return
     *//*
    @PostMapping("/update")
    @ResponseBody
    public Data editArticleById(@Valid Article article, BindingResult result) throws Exception {
        checkValid(result);
        Map<String, Object> params = Maps.newLinkedHashMap();
        params.put("status", Constants.ARTICLE_CATEGORY_NORMAL);
        params.put("id", article.getId());
        Article oldArticle = articleService.getOne(params);
        Assert.notNull(oldArticle, "该内容不存在！");
        Assert.isTrue(getUserId().equals(oldArticle.getUserId()), "只能修改属于自己的文章！");
        article.setUpdateBy(getUserId());
        return toAjax(articleService.update(article));
    }

    *//**
     * 按父级id查询id下所有分类列表
     *
     * @param parentId
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = "/category")
    public List<ArticleCategory> getCategoryChildList(@RequestParam(value = "parentId", defaultValue = "0") String parentId) {

        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setParentId(parentId);
        return articleCategoryService.list(articleCategory);
    }

    *//**
     * 文章浏览量更新
     *
     * @param id
     * @return
     *//*
    @ResponseBody
    @GetMapping(value = "/viewcount")
    public Data updateArticleByViewCount(@RequestParam(value = "id") String id) {
        Assert.notNull(id, "非法参数");
        articleService.updateArticleViewCount(id);
        return Data.success();
    }

    *//**
     * 处理顶（用户推荐）信息
     *
     * @param id
     * @param type
     * @return
     *//*
    @ResponseBody
    @PostMapping(value = "/article/dig")
    public Data articleDig(@RequestParam(value = "id") String id, @RequestParam(value = "type", defaultValue = "0") Integer type) throws Exception {
        Assert.notNull(type, "非法参数");
        ArticleVotes articleVotes = new ArticleVotes();
        articleVotes.setInfoType(type);
        articleVotes.setUserId(getUserId());
        articleVotes.setInfoId(id);
        articleVotes.setDig(1);
        return toAjax(articleVotesService.update(articleVotes));
    }

    private void checkValid(BindingResult result) throws ApplicationException {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error : list) {
                throw new ApplicationException(error.getDefaultMessage());
            }
        }
    }
*/
}
