package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.mapper.CmsUserMapper;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsUserDao 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Repository
public class CmsUserDaoImpl implements CmsUserDao {

    @Resource
    private CmsUserMapper cmsUserMapper;

    @Override
    public int replace(CmsUser cmsUser) {
        Assert.notNull(cmsUser, "保存对象不能为空");

        validateEntity(cmsUser);

        return cmsUserMapper.replace(cmsUser);
    }

    @Override
    public int bulkUpsert(List<CmsUser> cmsUserList){
        Assert.notEmpty(cmsUserList, "保存对象列表不能为空");

        for(CmsUser cmsUser : cmsUserList){
            validateEntity(cmsUser);
        }

        return cmsUserMapper.bulkUpsert(cmsUserList);
    }

    private void validateEntity(CmsUser cmsUser) {
        Assert.hasText(cmsUser.getStuId(), "学号不能为空");
        Assert.notNull(cmsUser.getRoleId(), "权限身份必须有值");
        Assert.hasText(cmsUser.getName(), "用户名不能为空");
        Assert.hasText(cmsUser.getTelephone(), "手机不能为空");
        Assert.hasText(cmsUser.getEmail(), "邮箱不能为空");
        Assert.hasText(cmsUser.getMentor(), "导师不能为空");
        Assert.hasText(cmsUser.getLeader(), "汇报人不能为空");
        Assert.notNull(cmsUser.getStudyType(), "就读类型必须有值");
        Assert.notNull(cmsUser.getKeshuo(), "论文必须有值");
        Assert.notNull(cmsUser.getStatus(), "状态必须有值");
        Assert.notNull(cmsUser.getDeleted(), "删除状态必须有值");
        Assert.notNull(cmsUser.getCreateTime(), "创建时间必须有值");
    }

    @Override
    public int updateById(CmsUser cmsUser) {
        Assert.notNull(cmsUser, "更新对象不能为空");
        Assert.notNull(cmsUser.getId(), "更新对象id不能为空");
        Assert.notNull(cmsUser.getModifyTime(), "更新时间必须有值");

        return cmsUserMapper.updateById(cmsUser);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsUserMapper.deleteById(id);
    }

    @Override
    public List<CmsUser> selectListByParam(CmsUserQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsUserMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsUserQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsUserMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsUserQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getStuId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}