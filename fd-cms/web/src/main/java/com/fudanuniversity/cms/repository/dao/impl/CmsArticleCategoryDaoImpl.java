package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsArticleCategoryDao;
import com.fudanuniversity.cms.repository.mapper.CmsArticleCategoryMapper;
import com.fudanuniversity.cms.repository.entity.CmsArticleCategory;
import com.fudanuniversity.cms.repository.query.CmsArticleCategoryQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsArticleCategoryDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Repository
public class CmsArticleCategoryDaoImpl implements CmsArticleCategoryDao {

    @Resource
    private CmsArticleCategoryMapper cmsArticleCategoryMapper;

    @Override
    public int insert(CmsArticleCategory cmsArticleCategory) {
        Assert.notNull(cmsArticleCategory, "保存对象不能为空");

        validateEntity(cmsArticleCategory);

        return cmsArticleCategoryMapper.insert(cmsArticleCategory);
    }

    @Override
    public int bulkUpsert(List<CmsArticleCategory> cmsArticleCategoryList){
        Assert.notEmpty(cmsArticleCategoryList, "保存对象列表不能为空");

        for(CmsArticleCategory cmsArticleCategory : cmsArticleCategoryList){
            validateEntity(cmsArticleCategory);
        }

        return cmsArticleCategoryMapper.bulkUpsert(cmsArticleCategoryList);
    }

    private void validateEntity(CmsArticleCategory cmsArticleCategory) {
        Assert.notNull(cmsArticleCategory.getTag(), "标签不能为空");
        Assert.notNull(cmsArticleCategory.getName(), "名称不能为空");
        Assert.notNull(cmsArticleCategory.getCreateTime(), "创建时间不能为空");
    }

    @Override
    public int updateById(CmsArticleCategory cmsArticleCategory) {
        Assert.notNull(cmsArticleCategory, "更新对象不能为空");
        Assert.notNull(cmsArticleCategory.getId(), "更新对象id不能为空");
        Assert.notNull(cmsArticleCategory.getModifyTime(), "更新时间不能为空");

        return cmsArticleCategoryMapper.updateById(cmsArticleCategory);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsArticleCategoryMapper.deleteById(id);
    }

    @Override
    public List<CmsArticleCategory> selectListByParam(CmsArticleCategoryQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsArticleCategoryMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsArticleCategoryQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsArticleCategoryMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsArticleCategoryQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getTag() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}