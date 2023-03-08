package cn.wstom.student.controller.front;


import cn.wstom.student.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dws
 */
@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController {
    /*private static final String PREFIX = "/front/question";

    private final QuestionService questionService;

    private final AnswerService answerService;

    @Autowired
    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    *//**
     * 问答首页
     *
     * @return
     *//*
    @GetMapping(value = {"/qc/", "/qc/index"})
    public String indexShare(@RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap) {
        modelMap.addAttribute("p", p);
        modelMap.addAttribute("user", getUser());
        return PREFIX + "/index";
    }

    *//**
     * 问答详细页面
     *
     * @param id
     * @param p
     * @param modelMap
     * @return
     *//*
    @GetMapping(value = "/q/{id}")
    public String question(@PathVariable(value = "id") String id, @RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");

        Question question = questionService.getById(id);
        Assert.notNull(question, "非法参数");
        //查询统计信息

        QuestionCount count = questionService.findQuestionCountById(question.getId());
        question.setCountAnswer(count.getCountAnswer());
        question.setCountView(count.getCountView());
        question.setCountFollow(count.getCountFollow());
        if (getUser() != null) {
            //检查登陆后是否已关注该问题
            Boolean questionFollow = questionService.checkQuestionFollow(question.getId(), getUserId());
            modelMap.addAttribute("questionFollow", questionFollow);
        }
        modelMap.addAttribute("p", p);
        modelMap.addAttribute("question", question);
        return PREFIX + "/detail";
    }

    *//**
     * 问答详细页面
     *
     * @param id
     * @param p
     * @param modelMap
     * @return
     *//*
    @GetMapping(value = "/tc/q/{id}")
    public String toQuestion(@PathVariable(value = "id") String id, @RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");

        Question question = questionService.getById(id);
        Assert.notNull(question, "非法参数");
        //查询统计信息

        QuestionCount count = questionService.findQuestionCountById(question.getId());
        question.setCountAnswer(count.getCountAnswer());
        question.setCountView(count.getCountView());
        question.setCountFollow(count.getCountFollow());
        if (getUser() != null) {
            //检查登陆后是否已关注该问题
            Boolean questionFollow = questionService.checkQuestionFollow(question.getId(), getUserId());
            modelMap.addAttribute("questionFollow", questionFollow);
        }
        modelMap.addAttribute("p", p);
        modelMap.addAttribute("question", question);
        return PREFIX + "/detail1";
    }

    *//**
     * 问答详细页面
     *
     * @param id
     * @param modelMap
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = "/question/{id}")
    public Data findQuestionById(@PathVariable(value = "id") String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");

        Map<String, Object> params = Maps.newHashMap();
        params.put("id", id);
        params.put("status", Constants.QUESTION_NORMAL);

        Question question = questionService.getOne(params);
        Assert.notNull(question, "该内容不存在");

        params.clear();
        //没有答案的时候直接读取问题内容，有回答的时候读取最新回答的答案内容
        String content;
        if (question.getCountAnswer() == 0) {
            content = question.getContent();
        } else {
            Answer answer = answerService.findNewestAnswerById(id);
            content = answer.getContent();
        }
        return Data.success("", Lists.newArrayList(content));
    }

    *//**
     * 发布问题
     *
     * @return
     *//*
    @GetMapping(value = "/edit")
    public String addQuestion() {
        return PREFIX + "/edit";
    }

    @PostMapping(value = "/add")
    public String addQuestion(Question question, RedirectAttributesModelMap modelMap) throws Exception {

        Assert.notNull(question, "非法参数");
        Assert.notNull(question.getTitle(), "标题不能为空");
        Assert.notNull(question.getContent(), "话题不能为空");

        question.setTitle(StringHelperUtils.htmlReplace(question.getTitle()));
        question.setCreateBy(getLoginName());
        question.setCreateTime(new Date());
        question.setUserId(getUserId());

        question.setTabs(StringHelperUtils.htmlReplace(question.getTabs()));

        boolean save = questionService.save(question);
        modelMap.addFlashAttribute("question", question);
        AsyncManager.async().execute(
                AsyncIntegral.recordIntegral(ShiroUtils.getUser(), "question_editor",
                        "提出问题【" + question.getTitle() + "】"));

        return save ? redirect("http://localhost:808/question/success") : PREFIX + "edit";
    }

    @RequestMapping("/success")
    public String doSuccess(Question question) {
        if (question == null || StringUtil.isBlank(question.getId())) {
            return PREFIX + "/edit";
        }
        return PREFIX + "/success";
    }


    *//**
     * 编辑问题
     *
     * @param id
     * @param modelMap
     * @return
     *//*
    @GetMapping(value = "/ucenter/question/edit")
    public String editQuestion(@PathVariable(value = "id") String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");
        Question question = questionService.getById(id);

        Assert.notNull(question, "该话题不存在或已删除");
        modelMap.addAttribute("question", question);
        modelMap.addAttribute("user", getUser());
        return PREFIX + "/edit";
    }

    @ResponseBody
    @PostMapping(value = "/answer/add")
    public Data addAnswer(@RequestParam(value = "questionId") String questionId,
                          @RequestParam(value = "content") String content) throws Exception {
        Assert.notNull(questionId, "话题参数错误");

        Question question = questionService.getById(questionId);
        Assert.notNull(question, "该话题不存在或已删除");
        //content=StringHelperUtil.htmlReplace(content);
        if (StringUtils.isBlank(content)) {
            return Data.error("内容不能为空");
        }
        Answer answer = new Answer();
        answer.setUserId(getUserId());
        answer.setQuestionId(questionId);
        answer.setContent(content);
        AsyncManager.async().execute(
                AsyncIntegral.recordIntegral(ShiroUtils.getUser(), "answer_editor",
                        "回复问题【" + question.getTitle() + "】"));

        return toAjax(answerService.addAnswer(questionId, getUserId(), content));
    }

    *//**
     * 编辑问题
     *
     * @param id
     * @param modelMap
     * @return
     *//*
    @GetMapping(value = "/ucenter/answer/edit/{id}")
    public String editAnswer(@PathVariable(value = "id") String id, ModelMap modelMap) {
        Assert.notNull(id, "非法参数");

        Answer answer = answerService.findAnswerByIdAndUserId(id, getUserId());
        Assert.notNull(answer, "该答案不存在或已删除");

        answer.setContent(StringHelperUtils.htmlspecialchars(answer.getContent()));
        Question question = questionService.getById(answer.getQuestionId());

        modelMap.addAttribute("question", question);
        modelMap.addAttribute("answer", answer);
        return PREFIX + "/answer";
    }

    @ResponseBody
    @PostMapping(value = "/answer/edit_save")
    public Data editAnswer(@RequestParam(value = "id") String id,
                           @RequestParam(value = "content") String content,
                           ModelMap modelMap) throws Exception {
        Assert.notNull(id, "非法参数");
        Assert.notNull(content, "答案内容不能为空");
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setUserId(getUserId());
        answer.setQuestionId(id);
        return toAjax(answerService.update(answer));
    }

    *//**
     * 处理关注信息
     *
     * @param questionId
     * @return
     *//*
    @ResponseBody
    @PostMapping(value = "/follow")
    public Data questionFollow(@RequestParam(value = "questionId") String questionId) throws Exception {
        Assert.notNull(questionId, "话题参数错误");
        return Data.success().put(Data.RESULT_KEY_DATA, questionService.questionFollow(questionId, getUserId()));
    }

    @ResponseBody
    @GetMapping(value = "/viewcount")
    public Data updateQuestionByViewCount(@RequestParam(value = "id") String id) {
        Assert.notNull(id, "话题参数错误");
        return Data.success().put(Data.RESULT_KEY_DATA, questionService.updateQuestionByViewCount(id));
    }*/
}
