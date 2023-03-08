package cn.wstom.common.page;/*
package cn.wstom.common.page;

import cn.edu.school.scampus.test.entity.TestEntity;
import cn.edu.school.scampus.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * 分页工具类
 *
 * @version 1.0 2015年2月7日下午9:25:03
 *//*

public final class PageUtils {

    */
/**
     * JSON解析
     *//*

    private static Gson GSON = new GsonBuilder().serializeNulls()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting().create();

    private PageUtils() {

    }

    */
/**
     * 获取hql统计总记录数hql语句
     *
     * @param hql
     * @return
     *//*

    public static String getCountHql(String hql) {
        int fromIndex = hql.indexOf("from ");
        //截取from后的语句
        String hqlFrom = hql.substring(fromIndex);

        return "select count(*) " + hqlFrom;
    }

    */
/**
     * 获取jqGrid的json格式数据
     *
     * @param pageModel
     * @return
     *//*

    public static String getJqGridJson(PageModel pageModel) {
        */
/*
         * {"page":"1","total":2,"records":"13", "rows":[]}
         *//*

        Map<String, Object> jqGridMap = new HashMap<String, Object>();
        jqGridMap.put(Constants.JQGRID_PAGE, pageModel.getCurrentPage());
        jqGridMap.put(Constants.JQGRID_TOTAL, pageModel.getTotalPage());
        jqGridMap.put(Constants.JQGRID_RECORDS, pageModel.getTotalRecords());
        jqGridMap.put(Constants.JQGRID_ROWS, pageModel.getResult());
        return GSON.toJson(jqGridMap);
    }

    public static void main(String[] args) {
        PageModel<TestEntity> pm = new PageModel<TestEntity>();
        pm.setTotalRecords(124);
        List<TestEntity> result = new ArrayList<TestEntity>();
        for (int i = 0; i < 10; i++) {
            TestEntity te = new TestEntity();
            te.setStrField(String.valueOf(i));
            result.add(te);
        }
        pm.setResult(result);
        System.out.println(getJqGridJson(pm));
    }
}
*/
