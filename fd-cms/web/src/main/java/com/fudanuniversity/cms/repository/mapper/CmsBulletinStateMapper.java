package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.commons.model.wrapper.TripleTuple;
import com.fudanuniversity.cms.repository.entity.CmsBulletinState;
import com.fudanuniversity.cms.repository.query.CmsBulletinStateQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsBulletinStateMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Mapper
public interface CmsBulletinStateMapper {

    /**
     * 保存处理
     */
    int insert(CmsBulletinState cmsBulletinState);

    int replace(CmsBulletinState cmsBulletinState);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsBulletinState> cmsBulletinStateList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsBulletinState cmsBulletinState);

    /**
     * 根据条件查询信息列表
     */
    List<CmsBulletinState> selectListByParam(CmsBulletinStateQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsBulletinStateQuery query);

    /**
     *
     */
    List<Long> selectUnreadBulletinIdList(CmsBulletinStateQuery query);

    /**
     *
     */
    TripleTuple<Long, Long, Long> queryCmsBulletinReadCount(CmsBulletinStateQuery query);
}
