package cn.wstom.admin.entity;


import freemarker.template.TemplateModelException;

import java.util.Date;
import java.util.List;

/**
 * 时间处理
 *
 * @author dws
 * @date 2018/9/2
 */
public class TimeAgoMethod extends BaseMethod {

    private static final long ONE_SECOND = 1000L;
    private static final long ONE_MINUTE = 60 * ONE_SECOND;
    private static final long ONE_HOUR = 60 * ONE_MINUTE;
    private static final long ONE_DAY = 24 * ONE_HOUR;
    private static final long ONE_WEEK = 7 * ONE_DAY;
    private static final long ONE_MONTH = 30 * ONE_DAY;
    private static final long ONE_YEAR = 12 * ONE_MONTH;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";
    private static final String UNKNOWN = "未知";

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Date date = getDate(arguments, 0);
        return format(date);
    }

    public static String format(Date date) {
        if (null == date) {
            return UNKNOWN;
        }

        long interval = System.currentTimeMillis() - date.getTime();
        if (interval < ONE_MINUTE) {
            long seconds = toSeconds(interval);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        } else if (interval < ONE_HOUR) {
            long minutes = toMinutes(interval);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        } else if (interval < ONE_DAY) {
            long hours = toHours(interval);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        } else if (interval < 2L * ONE_DAY) {
            return "昨天";
        } else if (interval < ONE_MONTH) {
            long days = toDays(interval);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        } else if (interval < ONE_YEAR) {
            long months = toMonths(interval);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(interval);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / ONE_SECOND;
    }

    private static long toMinutes(long date) {
        return date / ONE_MINUTE;
    }

    private static long toHours(long date) {
        return date / ONE_HOUR;
    }

    private static long toDays(long date) {
        return date / ONE_DAY;
    }

    private static long toMonths(long date) {
        return date / ONE_MONTH;
    }

    private static long toYears(long date) {
        return date / ONE_YEAR;
    }
}
