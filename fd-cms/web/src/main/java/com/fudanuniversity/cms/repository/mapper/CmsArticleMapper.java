package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsArticleMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Mapper
public interface CmsArticleMapper {

    /**
     * 保存处理
     */
    int insert(CmsArticle cmsArticle);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsArticle> cmsArticleList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsArticle cmsArticle);

    /**
     * 根据条件查询信息列表
     */
    List<CmsArticle> selectListByParam(CmsArticleQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsArticleQuery query);

}
