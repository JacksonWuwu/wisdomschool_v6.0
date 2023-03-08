package cn.wstom.admin.utils;

import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 模板工具
 *
 * @author dws
 * @date 2018/11/25
 */
public class TemplateModelUtil {
	public static final DateFormat FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final int FULL_DATE_LENGTH = 19;

	public static final DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final int SHORT_DATE_LENGTH = 10;

	/**
	 * 转换String类型
	 * @param model 模板对象
	 * @return 转换结果
	 * @throws TemplateModelException 转换异常
	 */
	public static String convertString(TemplateModel model) throws TemplateModelException {
		if (null != model) {
			if (model instanceof TemplateScalarModel) {
				return ((TemplateScalarModel) model).getAsString();
			} else if (model instanceof TemplateNumberModel) {
				return ((TemplateNumberModel) model).getAsNumber().toString();
			}
		}
		return null;
	}

	/**
	 * 转换Map类型
	 * @param model 模板对象
	 * @return 转换结果
	 */
	public static TemplateHashModel convertMap(TemplateModel model) {
		if (null != model) {
			if (model instanceof TemplateHashModelEx) {
				return (TemplateHashModelEx) model;
			} else if (model instanceof TemplateHashModel) {
				return (TemplateHashModel) model;
			}
		}
		return null;
	}

	/**
	 * 转换Integer类型
	 * @param model 模板对象
	 * @return 转换结果
	 * @throws TemplateModelException 转换异常
	 */
	public static Integer convertInteger(TemplateModel model) throws TemplateModelException {
		if (null != model) {
			if (model instanceof TemplateNumberModel) {
				return ((TemplateNumberModel) model).getAsNumber().intValue();
			} else if (model instanceof TemplateScalarModel) {
				String value = ((TemplateScalarModel) model).getAsString();
				if (StringUtils.isNotBlank(value)) {
					try {
						return Integer.parseInt(value);
					} catch (NumberFormatException e) {
					}
				}
			}
		}
		return null;
	}

	/**
	 * 转换Short类型
	 * @param model 模板对象
	 * @return 转换结果
	 * @throws TemplateModelException 转换异常
	 */
	public static Short convertShort(TemplateModel model) throws TemplateModelException {
		if (null != model) {
			if (model instanceof TemplateNumberModel) {
				return ((TemplateNumberModel) model).getAsNumber().shortValue();
			} else if (model instanceof TemplateScalarModel) {
				String value = ((TemplateScalarModel) model).getAsString();
				if (StringUtils.isNotBlank(value)) {
					try {
						return Short.parseShort(value);
					} catch (NumberFormatException e) {
					}
				}
			}
		}
		return null;
	}

	/**
	 * 转换Long类型
	 * @param model 模板对象
	 * @return 转换结果
	 * @throws TemplateModelException 转换异常
	 */
	public static Long convertLong(TemplateModel model) throws TemplateModelException {
		if (null != model) {
			if (model instanceof TemplateNumberModel) {
				return ((TemplateNumberModel) model).getAsNumber().longValue();
			} else if (model instanceof TemplateScalarModel) {
				String value = ((TemplateScalarModel) model).getAsString();
				if (StringUtils.isNotBlank(value)) {
					try {
						return Long.parseLong(value);
					} catch (NumberFormatException e) {
					}
				}
			}
		}
		return null;
	}

	/**
	 * 转换double类型
	 * @param model 模板对象
	 * @return 转换结果
	 * @throws TemplateModelException 转换异常
	 */
	public static Double convertDouble(TemplateModel model) throws TemplateModelException {
		if (null != model) {
			if (model instanceof TemplateNumberModel) {
				return ((TemplateNumberModel) model).getAsNumber().doubleValue();
			} else if (model instanceof TemplateScalarModel) {
				String value = ((TemplateScalarModel) model).getAsString();
				if (StringUtils.isNotBlank(value)) {
					try {
						return Double.parseDouble(value);
					} catch (NumberFormatException e) {
					}
				}
			}
		}
		return null;
	}

	/**
	 * 转换StringArray类型
	 * @param model 模板对象
	 * @return 转换结果
	 * @throws TemplateModelException 转换异常
	 */
	public static String[] convertStringArray(TemplateModel model) throws TemplateModelException {
		if (null != model) {
			if (model instanceof TemplateSequenceModel) {
				TemplateSequenceModel sequenceModel = (TemplateSequenceModel) model;
                int size = sequenceModel.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
					values[i] = convertString(sequenceModel.get(i));
				}
				return values;
			} else {
				String value = convertString(model);
				if (StringUtils.isNotBlank(value)) {
					return StringUtils.split(value, ",");
				}
			}
		}
		return null;
	}

	/**
	 * 转换boolean类型
	 * @param model 模板对象
	 * @return 转换结果
	 * @throws TemplateModelException 转换异常
	 */
	public static Boolean convertBoolean(TemplateModel model) throws TemplateModelException {
		if (null != model) {
			if (model instanceof TemplateBooleanModel) {
				return ((TemplateBooleanModel) model).getAsBoolean();
			} else if (model instanceof TemplateNumberModel) {
				return 0 != ((TemplateNumberModel) model).getAsNumber().intValue();
			} else if (model instanceof TemplateScalarModel) {
				String value = ((TemplateScalarModel) model).getAsString();
				if (StringUtils.isNotBlank(value)) {
					return Boolean.valueOf(value);
				}
			}
		}
		return null;
	}

	/**
	 * 转换date类型
	 * @param model 模板对象
	 * @return 转换结果
	 * @throws TemplateModelException 转换异常
	 */
	public static Date convertDate(TemplateModel model) throws TemplateModelException {
		if (null != model) {
			if (model instanceof TemplateDateModel) {
				return ((TemplateDateModel) model).getAsDate();
			} else if (model instanceof TemplateScalarModel) {
				String date = StringUtils.trimToEmpty(((TemplateScalarModel) model).getAsString());
				return parseDate(date);
			}
		}
		return null;
	}

	/**
	 * 格式化Date类型
	 * @param date 字符串类型时间
	 * @return Date类型时间
	 */
	private static Date parseDate(String date) {
		try {
			if (FULL_DATE_LENGTH == date.length()) {
				return FULL_DATE_FORMAT.parse(date);
			} else if (SHORT_DATE_LENGTH == date.length()) {
				return SHORT_DATE_FORMAT.parse(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
