package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.commons.model.wrapper.TripleTuple;
import com.fudanuniversity.cms.repository.dao.CmsBulletinStateDao;
import com.fudanuniversity.cms.repository.entity.CmsBulletinState;
import com.fudanuniversity.cms.repository.mapper.CmsBulletinStateMapper;
import com.fudanuniversity.cms.repository.query.CmsBulletinStateQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsBulletinStateDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Repository
public class CmsBulletinStateDaoImpl implements CmsBulletinStateDao {

    @Resource
    private CmsBulletinStateMapper cmsBulletinStateMapper;

    @Override
    public int insert(CmsBulletinState cmsBulletinState) {
        Assert.notNull(cmsBulletinState, "保存对象不能为空");

        validateEntity(cmsBulletinState);

        return cmsBulletinStateMapper.insert(cmsBulletinState);
    }

    @Override
    public int replace(CmsBulletinState cmsBulletinState) {
        Assert.notNull(cmsBulletinState, "保存对象不能为空");

        validateEntity(cmsBulletinState);

        return cmsBulletinStateMapper.replace(cmsBulletinState);
    }

    @Override
    public int bulkUpsert(List<CmsBulletinState> cmsBulletinStateList) {
        Assert.notEmpty(cmsBulletinStateList, "保存对象列表不能为空");

        for (CmsBulletinState cmsBulletinState : cmsBulletinStateList) {
            validateEntity(cmsBulletinState);
        }

        return cmsBulletinStateMapper.bulkUpsert(cmsBulletinStateList);
    }

    private void validateEntity(CmsBulletinState cmsBulletinState) {
        Assert.notNull(cmsBulletinState.getUserId(), "用户id必须有值");
        Assert.notNull(cmsBulletinState.getBulletinId(), "通知id必须有值");
        Assert.notNull(cmsBulletinState.getRead(), "是否已读必须有值");
        Assert.notNull(cmsBulletinState.getCreateTime(), "创建时间必须有值");
    }

    @Override
    public int updateById(CmsBulletinState cmsBulletinState) {
        Assert.notNull(cmsBulletinState, "更新对象不能为空");
        Assert.notNull(cmsBulletinState.getId(), "更新对象id不能为空");
        Assert.notNull(cmsBulletinState.getModifyTime(), "更新时间必须有值");

        return cmsBulletinStateMapper.updateById(cmsBulletinState);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsBulletinStateMapper.deleteById(id);
    }

    @Override
    public List<CmsBulletinState> selectListByParam(CmsBulletinStateQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsBulletinStateMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsBulletinStateQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsBulletinStateMapper.selectCountByParam(query);
    }

    @Override
    public TripleTuple<Long, Long, Long> queryCmsBulletinReadCount(CmsBulletinStateQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        query.validateBaseArgument();

        return cmsBulletinStateMapper.queryCmsBulletinReadCount(query);
    }

    private void validateQueryParameter(CmsBulletinStateQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
                && query.getUserId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}