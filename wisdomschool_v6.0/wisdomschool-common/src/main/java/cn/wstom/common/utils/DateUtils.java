package cn.wstom.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author dws
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将java time格式转成Solr支持的时间
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String getSolrDate(String time) throws ParseException {
        //格式化成 时间成  年-月-日
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        //格式化成 时间：分：秒
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        Date date = sdfTime.parse(time);
        String result = sdf1.format(date) + "T" + sdf2.format(date) + "Z";
        return result;
    }

    /**
     * 计算两个时间的时间差(毫秒)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long differ(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return date2.getTime() - date1.getTime();
        }
        return 0;
    }

    /**
     * 判断time是否在now的n天之内
     * @param time
     * @param now
     * @return
     */
    public static boolean belongDate(Date time, Date now) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        Date before7days = calendar.getTime();   //得到n前的时间
        if (before7days.getTime() < time.getTime()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断time是否在from，to之内
     *
     * @param time 指定日期
     * @param from 开始日期
     * @param to   结束日期
     * @return
     */
    public static boolean belongCalendar(Date time, Date from, Date to) {
        Calendar date = Calendar.getInstance();
        date.setTime(time);

        Calendar after = Calendar.getInstance();
        after.setTime(from);

        Calendar before = Calendar.getInstance();
        before.setTime(to);

        if (date.after(after) && date.before(before)) {
            return true;
        } else {
            return false;
        }
    }

    public enum FormatDate {
        /**
         * 日期时间格式
         */
        YYYY("yyyy"),
        YYYYMM("yyyyMM"),
        YYYYMMDD("yyyyMMdd"),
        YYYYMMDDHH("yyyyMMddHH"),
        YYYYMMDDHHmm("yyyyMMddHHmm"),
        YYYYMMDDHHmmss("yyyyMMddHHmmss"),
        YYYYMMDDHHmmssSSS("yyyyMMddHHmmssSSS"),
        /**
         * 以"-"分隔
         */
        YYYYMMDD_HOR_LINE("yyyy-MM-dd"),
        YYYYMMDDHH_HOR_LINE("yyyy-MM-dd HH"),
        YYYYMMDDHHmm_HOR_LINE("yyyy-MM-dd HH:mm"),
        YYYYMMDDHHmmss_HOR_LINE("yyyy-MM-dd HH:mm:ss"),
        YYYYMMDDHHmmssSSS_HOR_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
        /**
         * 以"/"分隔
         */
        YYYYMMDD_OBL_LINE("yyyy/MM/dd"),
        YYYYMMDDHH_OBL_LINE("yyyy/MM/dd HH"),
        YYYYMMDDHHmm_OBL_LINE("yyyy/MM/dd HH:mm"),
        YYYYMMDDHHmmss_OBL_LINE("yyyy/MM/dd HH:mm:ss"),
        YYYYMMDDHHmmssSSS_OBL_LINE("yyyy/MM/dd HH:mm:ss.SSS"),
        /**
         * 以"."分隔
         */
        YYYYMMDD_DOT("yyyy.MM.dd"),
        YYYYMMDDHH_DOT("yyyy.MM.dd HH"),
        YYYYMMDDHHmm_DOT("yyyy.MM.dd HH:mm"),
        YYYYMMDDHHmmss_DOT("yyyy.MM.dd HH:mm:ss"),
        YYYYMMDDHHmmssSSS_DOT("yyyy.MM.dd HH:mm:ss.SSS"),
        /**
         * 中文
         */
        YYYYMMDD_ZH("yyyy年MM月dd日"),
        YYYYMMDDHH_ZH("yyyy年MM月dd日 HH时"),
        YYYYMMDDHHmm_ZH("yyyy年MM月dd日 HH时mm分"),
        YYYYMMDDHHmmss_ZH("yyyy年MM月dd日 HH时mm分ss秒"),
        YYYYMMDDHHmmssSSS_ZH("yyyy年MM月dd日 HH时mm分ss秒SSS毫秒"),
        /**
         * 时间格式
         */
        HH("HH"),
        HHmm("HH:mm"),
        HHmmss("HH:mm:ss"),
        HHmmssSSS("HH:mm:ss:SSS");

        SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance();
        private String pattern;

        FormatDate(String pattern) {
            this.pattern = pattern;
        }

        public String getDate(Date d) {
            this.sdf.applyPattern(this.pattern);
            return this.sdf.format(d);
        }

        public Date getDate(String d) {
            try {
                this.sdf.applyPattern(this.pattern);
                return this.sdf.parse(d);
            } catch (ParseException e) {
                LOGGER.error("转换日期字符串必须与转换格式相匹配!", e);
            }
            return null;
        }

        public String getCurrentDate() {
            this.sdf.applyPattern(this.pattern);
            return this.sdf.format(getNowDate());
        }

        public String getPattern() {
            return this.pattern;
        }
    }
}
