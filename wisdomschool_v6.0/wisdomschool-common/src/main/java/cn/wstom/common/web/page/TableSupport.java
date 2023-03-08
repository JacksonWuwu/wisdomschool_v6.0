package cn.wstom.common.web.page;


import cn.wstom.common.constant.Constants;
import cn.wstom.common.support.Convert;
import cn.wstom.common.utils.JSONUtils;
import cn.wstom.common.utils.ServletUtils;


/**
 * 表格数据处理
 *
 * @author dws
 */
public class TableSupport {
    /**
     * 封装分页对象
     */
    private static PageDomain getPageDomain() throws Exception {
        PageDomain pageDomain = new PageDomain();
        //两种请求分页方式
        //1、bootstrap-table表格的分页插件
        //2、纯页面分页方式
        String pager = ServletUtils.getParameter("pager");
        if (pager != null && !"".equals(pager.trim())) {
            PageDomain page = JSONUtils.readValue(pager, PageDomain.class);
            if (page != null) {
                page.setPageNum(Convert.toInt(page.getPageNum(), 0));
                page.setPageSize(Convert.toInt(page.getPageSize(), 10));
                return page;
            }
        }
        pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM, 0));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE, 10));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
//        String params = ServletUtils.getParameter(Constants.TABLE_PARAMS);
//        JSON.readValue(params, Map.class);
        return pageDomain;
    }

    public static PageDomain buildPageRequest() throws Exception {
        return getPageDomain();
    }
}
