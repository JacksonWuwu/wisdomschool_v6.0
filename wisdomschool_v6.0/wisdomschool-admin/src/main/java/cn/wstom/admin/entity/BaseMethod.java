package cn.wstom.admin.entity;


import cn.wstom.admin.utils.TemplateModelUtil;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.Date;
import java.util.List;

/**
 * 模板基础方法
 *
 * @author dws
 * @date 2018/11/25
 */
public abstract class BaseMethod implements TemplateMethodModelEx {

	public String getString(List<TemplateModel> args, int index) throws TemplateModelException {
		return TemplateModelUtil.convertString(getModel(args, index));
	}

	public Integer getInteger(List<TemplateModel> args, int index) throws TemplateModelException {
		return TemplateModelUtil.convertInteger(getModel(args, index));
	}

	public Long getLong(List<TemplateModel> args, int index) throws TemplateModelException {
		return TemplateModelUtil.convertLong(getModel(args, index));
	}

	public Date getDate(List<TemplateModel> args, int index) throws TemplateModelException {
		return TemplateModelUtil.convertDate(getModel(args, index));
	}

	public TemplateModel getModel(List<TemplateModel> args, int index) {
		if (index < args.size()) {
			return args.get(index);
		}
		return null;
	}
}
