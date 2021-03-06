package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsUserMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
@Mapper
public interface CmsUserMapper {

    /**
     * 保存处理
     */
    int insert(CmsUser cmsUser);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsUser> cmsUserList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsUser cmsUser);

    /**
     * 根据条件查询信息列表
     */
    List<CmsUser> selectListByParam(CmsUserQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsUserQuery query);

    /**
     *
     */
    List<CmsUser> selectAvailableAllocationUserListByParam(CmsUserQuery query);
}
