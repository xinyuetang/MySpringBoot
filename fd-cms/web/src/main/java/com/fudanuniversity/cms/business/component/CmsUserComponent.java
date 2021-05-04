package com.fudanuniversity.cms.business.component;

import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Xinyue.Tang at 2021-05-02 21:42:45
 */
@Component
public class CmsUserComponent {

    @Resource
    private CmsUserDao cmsUserDao;

    public CmsUser queryUser(String stuId) {
        AssertUtils.hasText(stuId, "学号/工号不能为空");
        CmsUserQuery query = CmsUserQuery.singletonQuery();
        query.setStuId(stuId);
        query.setDeleted(DeletedEnum.Normal.getCode());
        List<CmsUser> users = cmsUserDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(users)) {
            return users.get(0);
        }
        return null;
    }

    public Map<String, CmsUser> queryUserMap(String... stuIdArray) {
        List<String> stuIds = Arrays.stream(stuIdArray)
                .filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(stuIds)) {
            CmsUserQuery query = CmsUserQuery.singletonQuery();
            query.setStuIdList(stuIds);
            query.setDeleted(DeletedEnum.Normal.getCode());
            List<CmsUser> users = cmsUserDao.selectListByParam(query);
            if (CollectionUtils.isNotEmpty(users)) {
                return users.stream().collect(Collectors.toMap(CmsUser::getStuId, Function.identity()));
            }
        }
        return Collections.emptyMap();
    }

    public Map<Long, CmsUser> queryUserMap(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            CmsUserQuery query = CmsUserQuery.listQuery();
            query.setIdList(userIds);
            query.setLimit(userIds.size());
            List<CmsUser> users = cmsUserDao.selectListByParam(query);
            if (CollectionUtils.isNotEmpty(users)) {
                return users.stream().collect(Collectors.toMap(CmsUser::getId, Function.identity()));
            }
        }
        return Collections.emptyMap();
    }
}
