package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.FilterKeywordService;
import cn.wstom.admin.service.impl.BaseServiceImpl;
import cn.wstom.common.utils.SymbolConvertUtils;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author dws
 * @date 2019/03/07
 */
@Service
public class FilterKeywordServiceImpl extends BaseServiceImpl
        <FilterKeywordMapper, FilterKeyword>
        implements FilterKeywordService {

    private static final FilterSet SET = new FilterSet();
    /**
     * 存储节点
     */
    private static final Map<Integer, WordNode> NODES =
            new HashMap<>(1024, 1);
    /**
     * 大写转化为小写 全角转化为半角，转化的48个干扰符号如下：
     * <p>
     * [!, ., ,, #, $, %, &, *, (, ), |, ?, /, @, ", ', ;, [, ],
     * {, }, +, ~, -, _, =, ^, <, >, 　, ！, 。, ，, ￥, （, ）,
     * ？, 、, “, ‘, ；, 【, 】, ——, ……, 《, 》]
     */
    private static final Integer[] STAFFS = new Integer[]{64, 12289, 12290, 12298, 12299, 12304, 12305, 8212, 8216, 91, 8220, 93, 94, 95, 32, 33, 34, 35, 36, 37, 65509, 38, 8230, 39, 40, 41, 42, 43, 44, 45, 46, 47, 59, 123, 124, 60, 125, 61, 126, 62, 63};
    private static final Set<Integer> STOP_WD_SET = new HashSet<>(Arrays.asList(STAFFS));
    /**
     * 敏感词过滤替换
     */
    private static final char SIGN = '*';

    @Resource
    private FilterKeywordMapper filterKeywordMapper;

    /**
     * 过滤判断 将敏感词转化为成屏蔽词
     *
     * @param content
     * @return
     */
    @Override
    public final String doFilter(final String content) {
        addSensitiveWord(this.getFilterKeywordAllList());
        //干扰符号选择文本方式加载，由于ehcache里特殊字符串不能缓存，所以选择了使用文本方式加载特殊字符串
        //addStopWord(readWordFromFile("stopwd.txt"));
        char[] chs = content.toCharArray();
        int length = chs.length;
        int currc; // 当前检查的字符
        int cpcurrc; // 当前检查字符的备份
        int k;
        WordNode node;
        for (int i = 0; i < length; i++) {
            currc = charConvert(chs[i]);
            if (!SET.contains(currc)) {
                continue;
            }
            //日
            node = NODES.get(currc);
            if (node == null) {
                continue;
            }
            boolean couldMark = false;
            int markNum = -1;
            if (node.isLast()) {
                // 单字匹配（日）
                couldMark = true;
                markNum = 0;
            }
            // 继续匹配（日你/日你妹），以长的优先
            // 你-3 妹-4 夫-5
            k = i;
            // 当前字符的拷贝
            cpcurrc = currc;
            for (; ++k < length; ) {
                int temp = charConvert(chs[k]);
                if (temp == cpcurrc) {
                    continue;
                }
                if (STOP_WD_SET.contains(temp)) {
                    continue;
                }
                node = node.querySub(temp);
                if (node == null) {
                    // 没有了
                    break;
                }
                if (node.isLast()) {
                    couldMark = true;
                    // 3-2
                    markNum = k - i;
                }
                cpcurrc = temp;
            }
            if (couldMark) {
                for (k = 0; k <= markNum; k++) {
                    chs[k + i] = SIGN;
                }
                i = i + markNum;
            }
        }
        return new String(chs);

    }

    /**
     * 所有违禁关键词列表
     *
     * @return
     */
    @Cacheable(value = "longterm")
    public List<String> getFilterKeywordAllList() {
        return filterKeywordMapper.getFilterKeywordAllList();
    }

    /**
     * 添加DFA节点
     *
     * @param words
     */
    private void addSensitiveWord(final List<String> words) {

        char[] chs;
        int fchar;
        int lastIndex;
        WordNode fnode; // 首字母节点
        for (String curr : words) {
            chs = curr.toCharArray();
            fchar = charConvert(chs[0]);
            if (!SET.contains(fchar)) {
                // 没有首字定义
                // 首字标志位 可重复add,反正判断了，不重复了
                SET.add(fchar);
                fnode = new WordNode(fchar, chs.length == 1);
                NODES.put(fchar, fnode);
            } else {
                fnode = NODES.get(fchar);
                if (!fnode.isLast() && chs.length == 1) {
                    fnode.setLast(true);
                }
            }
            lastIndex = chs.length - 1;
            for (int i = 1; i < chs.length; i++) {
                fnode = fnode.addIfNoExist(charConvert(chs[i]), i == lastIndex);
            }
        }
    }

    /**
     * 大写转化为小写 全角转化为半角
     *
     * @param src
     * @return
     */
    private static int charConvert(char src) {
        int r = SymbolConvertUtils.qj2bj(src);
        return (r >= 'A' && r <= 'Z') ? r + 32 : r;
    }
}
