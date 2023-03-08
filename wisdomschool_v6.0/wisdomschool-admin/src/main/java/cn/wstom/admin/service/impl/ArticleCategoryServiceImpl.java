package cn.wstom.admin.service.impl;

import cn.wstom.admin.entity.ArticleCategory;
import cn.wstom.admin.mapper.ArticleCategoryMapper;
import cn.wstom.admin.service.ArticleCategoryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dws
 */
@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl
        <ArticleCategoryMapper, ArticleCategory>
        implements ArticleCategoryService {

    @Resource
    protected ArticleCategoryMapper articleCategoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addArticleCategory(String pid, String name) throws Exception {
        if (this.checkArticleCategoryByName(name, null)) {
            throw new Exception("分类名称不得重复");
        }
        ArticleCategory category = new ArticleCategory();
        category.setParentId(pid);
        category.setName(name);
        return articleCategoryMapper.addArticleCategory(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editArticleCategoryById(String id, String name) throws Exception {
        if (this.checkArticleCategoryByName(name, id)) {
            throw new Exception("分类名称已存在！");
        }
        ArticleCategory category = new ArticleCategory();
        category.setId(id);
        category.setName(name);
        return articleCategoryMapper.editArticleCategoryById(category);
    }

    /**
     * 拖拽操作保存分类归属和排序
     *
     * @param id
     * @param pId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editCategoryDragsById(String id, String pId) throws Exception {
        if (this.checkCategoryById(id, 0)) {
            throw new Exception("分类不存在！");
        }
        ArticleCategory category = new ArticleCategory();
        category.setId(id);
        category.setParentId(pId);
        return articleCategoryMapper.editArticleCategoryById(category);
    }

    /**
     * 按id查询分类信息
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public ArticleCategory findCategoryById(String id, Integer status) {
        return articleCategoryMapper.findCategoryById(id, status);
    }

    /**
     * 查询分类名称是否已存在！
     *
     * @param id     查询id
     * @param status 审核状态
     * @return
     */
    @Override
    public boolean checkCategoryById(String id, Integer status) {
        return articleCategoryMapper.findCategoryById(id, status) == null;
    }

    /**
     * 查询分类名称是否已存在！
     *
     * @param name 分类名称
     * @return
     */
    @Override
    public boolean checkArticleCategoryByName(String name, String id) {
        int totalCount = articleCategoryMapper.checkArticleCategoryByName(name, id);
        return totalCount > 0;
    }

    /**
     * 根据分类id查询所属的所有子类
     *
     * @param parentId
     * @return
     */
    @Override
    public List<ArticleCategory> getCategoryListByParentId(String parentId) {
        return articleCategoryMapper.getCategoryListByParentId(parentId);
    }

    /**
     * 查询所有分类
     *
     * @return
     */
    @Override
    public List<ArticleCategory> getCategoryAllList() {
        return articleCategoryMapper.getCategoryAllList();
    }


    /**
     * 
     * @Title: queryItemListByTmpl
     * @Description:
     * @param itemList
     * @param id
     * @return List<Map < String , Object>> 返回类型
     * @throws
     */
    /**
     * 根据list以及根节点id获取树形展示数据
     *
     * @param itemList
     * @param node
     * @return
     */
    public static List<Map<String, Object>> queryItemListByTmpl(List<ArticleCategory> itemList, ArticleCategory node) {
        try {
            Map<String, Object> map;
            List<Map<String, Object>> rusult = new ArrayList<>();
            ArticleCategory nodemap = null;
            for (ArticleCategory item : itemList) {
                if (node != null) {
                    if (item.getParentId().equals(node.getParentId())) {
                        map = new HashMap<String, Object>();
                        map.put("id", item.getId());
                        map.put("fatherId", item.getParentId());
                        map.put("name", item.getName());
                        //map.put("expanded", "true");
                        rusult.add(map);
                    }
                    if (!"".equals(node.getParentId()) && item.getId().equals(node.getParentId())) {
                        nodemap = new ArticleCategory();
                        if (item.getId().equals(node.getParentId())) {
                            nodemap.setId(item.getId());
                            nodemap.setName(item.getName());
                            nodemap.setParentId(item.getParentId());
                        }
                    }
                } else {
                    if ("".equals(node.getParentId())) {
                        map = new HashMap<String, Object>();
                        map.put("id", item.getId());
                        map.put("fatherId", item.getParentId());
                        map.put("name", item.getName());
                        //map.put("expanded", "true");
                        rusult.add(map);
                    }
                }
            }
            if (node != null) {
                if (!"".equals(node.getParentId())) {
                    rusult = getSonTree(rusult, itemList, nodemap);
                }
            }
            return rusult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 
     * @Title: getSonTree
     * @Description: 递归获取所有的根节点
     * @param rusult
     * @param itemList
     * @return Map<String , Object> 返回类型
     * @throws
     */
    /**
     * 递归获取所有的父类根节点
     *
     * @param rusult
     * @param itemList
     * @param node
     * @return
     */
    private static List<Map<String, Object>> getSonTree(List<Map<String, Object>> rusult, List<ArticleCategory> itemList, ArticleCategory node) {
        List<Map<String, Object>> sonList = new ArrayList<Map<String, Object>>();
        Map<String, Object> sonMap;
        ArticleCategory nodemapz = null;
        for (ArticleCategory item : itemList) {
            if (node.getParentId().equals(item.getParentId())) {
                sonMap = new HashMap<>();
                sonMap.put("id", item.getId());
                sonMap.put("fatherId", item.getParentId());
                sonMap.put("name", item.getName());
                //sonMap.put("expanded", "true");
                if (node.getId() == item.getId()) {
                    sonMap.put("children", rusult);
                }
                sonList.add(sonMap);
            }
            if (!"".equals(node.getParentId()) && item.getId().equals(node.getParentId())) {
                if (item.getId() == node.getParentId()) {
                    nodemapz = new ArticleCategory();
                    nodemapz.setId(item.getId());
                    nodemapz.setName(item.getName());
                    nodemapz.setParentId(item.getParentId());
                }
            }
        }
        if (nodemapz != null && !"".equals(node.getParentId())) {
            sonList = getSonTree(sonList, itemList, nodemapz);
        }
        return sonList;
    }
}
