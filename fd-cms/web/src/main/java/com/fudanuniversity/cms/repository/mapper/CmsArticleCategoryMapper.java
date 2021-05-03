package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsArticleCategory;
import com.fudanuniversity.cms.repository.query.CmsArticleCategoryQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsArticleCategoryMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Mapper
public interface CmsArticleCategoryMapper {

    /**
     * 保存处理
     */
    int insert(CmsArticleCategory cmsArticleCategory);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsArticleCategory> cmsArticleCategoryList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsArticleCategory cmsArticleCategory);

    /**
     * 根据条件查询信息列表
     */
    List<CmsArticleCategory> selectListByParam(CmsArticleCategoryQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsArticleCategoryQuery query);

}
