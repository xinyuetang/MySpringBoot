package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsArticleDao;
import com.fudanuniversity.cms.repository.mapper.CmsArticleMapper;
import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsArticleDao 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Repository
public class CmsArticleDaoImpl implements CmsArticleDao {

    @Resource
    private CmsArticleMapper cmsArticleMapper;

    @Override
    public int insert(CmsArticle cmsArticle) {
        Assert.notNull(cmsArticle, "保存对象不能为空");

        validateEntity(cmsArticle);

        return cmsArticleMapper.insert(cmsArticle);
    }

    @Override
    public int bulkUpsert(List<CmsArticle> cmsArticleList){
        Assert.notEmpty(cmsArticleList, "保存对象列表不能为空");

        for(CmsArticle cmsArticle : cmsArticleList){
            validateEntity(cmsArticle);
        }

        return cmsArticleMapper.bulkUpsert(cmsArticleList);
    }

    private void validateEntity(CmsArticle cmsArticle) {
        Assert.notNull(cmsArticle.getCategoryTag(), "标签必须有值");
        Assert.hasText(cmsArticle.getTitle(), "名称不能为空");
        Assert.hasText(cmsArticle.getContent(), "内容不能为空");
        Assert.notNull(cmsArticle.getCreateTime(), "创建时间必须有值");
    }

    @Override
    public int updateById(CmsArticle cmsArticle) {
        Assert.notNull(cmsArticle, "更新对象不能为空");
        Assert.notNull(cmsArticle.getId(), "更新对象id不能为空");
        Assert.notNull(cmsArticle.getModifyTime(), "更新时间必须有值");

        return cmsArticleMapper.updateById(cmsArticle);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsArticleMapper.deleteById(id);
    }

    @Override
    public List<CmsArticle> selectListByParam(CmsArticleQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsArticleMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsArticleQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsArticleMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsArticleQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getCategoryTag() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}