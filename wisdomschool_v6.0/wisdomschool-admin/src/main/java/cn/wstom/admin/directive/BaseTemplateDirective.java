package cn.wstom.admin.directive;

import cn.wstom.common.utils.StringUtil;

import cn.wstom.common.web.page.PageDomain;
import cn.wstom.common.web.page.TableSupport;
import com.github.pagehelper.PageHelper;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

/**
 * 基础模板引擎指令，业务逻辑在<code>execute</code>实现
 *
 * @author dws
 * @date 2018/11/25
 */
public abstract class BaseTemplateDirective implements TemplateDirectiveModel {
    protected static final String DATA = "data";
    protected static final String MSG = "msg";

	@Override
	public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
		try {
			execute(new DirectiveHandler(environment, map, templateModels, templateDirectiveBody));
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new TemplateException(e, environment);
		}
	}

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

	/**
     * 获取模板指令名
	 * @return 模板指令名
	 */
	public abstract String getName();

	/**
	 * 指令执行体，由子类实现指令逻辑功能
	 * @param handler 指令处理
	 * @throws Exception 异常
	 */
    public abstract void execute(DirectiveHandler handler) throws Exception;
}
