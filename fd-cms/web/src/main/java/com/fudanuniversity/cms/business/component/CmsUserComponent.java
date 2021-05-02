package com.fudanuniversity.cms.business.component;

import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by tidu at 2021-05-02 21:42:45
 */
@Component
public class CmsUserComponent {

    @Resource
    private CmsUserDao cmsUserDao;

    public CmsUser queryUser(String stuId) {
        CmsUserQuery query = CmsUserQuery.singletonQuery();
        query.setStuId(stuId);
        query.setDeleted(DeletedEnum.Normal.getCode());
        List<CmsUser> users = cmsUserDao.selectListByParam(query);
        if (CollectionUtils.isEmpty(users)) {
            throw new BusinessException("用户[" + stuId + "]不存在");
        }
        return users.get(0);
    }

    public Map<Long, CmsUser> queryUsersMap(List<Long> userIds) {
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
