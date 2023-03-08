package cn.wstom.admin.directive;


import cn.wstom.admin.utils.TemplateModelUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

/**
 * 指令处理
 *
 * @author dws
 * @date 2018/11/25
 */
public class DirectiveHandler {
	private Environment env;

	private Map<String, TemplateModel> parameters;

	private TemplateModel[] loopVars;

	private TemplateDirectiveBody body;

	private Environment.Namespace namespace;

	public DirectiveHandler(Environment env, Map<String, TemplateModel> parameters, TemplateModel[] loopVars, TemplateDirectiveBody body) {
		this.env = env;
		this.parameters = parameters;
		this.loopVars = loopVars;
		this.body = body;
		this.namespace = env.getCurrentNamespace();
	}

	public void render() throws IOException, TemplateException {
		Assert.notNull(body, "must have template directive body");
		body.render(env.getOut());
	}

	public void renderString(String text) throws IOException {
		StringWriter writer = new StringWriter();
		writer.append(text);
		write(text);
	}

	public DirectiveHandler put(String key, Object value) throws TemplateModelException {
		namespace.put(key, wrap(value));
		return this;
	}

	public String getString(String name) throws TemplateModelException {
		return TemplateModelUtil.convertString(getModel(name));
	}

	public Integer getInteger(String name) throws TemplateModelException {
		return TemplateModelUtil.convertInteger(getModel(name));
	}

	public Short getShort(String name) throws TemplateModelException {
		return TemplateModelUtil.convertShort(getModel(name));
	}

	public Long getLong(String name) throws TemplateModelException {
		return TemplateModelUtil.convertLong(getModel(name));
	}

	public Double getDouble(String name) throws TemplateModelException {
		return TemplateModelUtil.convertDouble(getModel(name));
	}

	public String[] getStringArray(String name) throws TemplateModelException {
		return TemplateModelUtil.convertStringArray(getModel(name));
	}

	public Boolean getBoolean(String name) throws TemplateModelException {
		return TemplateModelUtil.convertBoolean(getModel(name));
	}

	public Date getDate(String name) throws TemplateModelException {
		return TemplateModelUtil.convertDate(getModel(name));
	}

	public String getString(String name, String defaultValue) throws TemplateModelException {
		String value = getString(name);
		return null == value ? defaultValue : value;
	}

	public Integer getInteger(String name, Integer defaultValue) throws TemplateModelException {
		Integer value = getInteger(name);
		return null == value ? defaultValue : value;
	}

	public Long getLong(String name, Long defaultValue) throws TemplateModelException {
		Long value = getLong(name);
		return null == value ? defaultValue : value;
	}

    public Boolean getBoolean(String name, boolean defaultValue) throws TemplateModelException {
        Boolean value = getBoolean(name);
        return null == value ? defaultValue : value;
    }

	public String getContextPath() {
		String contextPath = null;
		try {
			contextPath = TemplateModelUtil.convertString(getEnvModel("base"));
		} catch (TemplateModelException e) {
		}
		return contextPath;
	}

	/**
	 * 包装对象
	 * @param obj
	 * @return
	 */
	public TemplateModel wrap(Object obj) throws TemplateModelException {
		return env.getObjectWrapper().wrap(obj);
	}

	/**
	 * 获取环境变量
	 * @param name
	 * @return
	 * @throws TemplateModelException
	 */
	public TemplateModel getEnvModel(String name) throws TemplateModelException {
		return env.getVariable(name);
	}

	public void write(String text) throws IOException {
		env.getOut().write(text);
	}

	private TemplateModel getModel(String name) {
		return parameters.get(name);
	}
}
