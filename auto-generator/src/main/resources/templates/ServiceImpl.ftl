package ${packagePrefix}.combi.service.impl;

import ${packagePrefix}.inner.dao.${classSimpleName}Dao;
import ${packagePrefix}.inner.entity.${classSimpleName};
import ${packagePrefix}.inner.query.${classSimpleName}Query;
import ${packagePrefix}.combi.service.${classSimpleName}Service;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.ValueUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${classSimpleName}Service 实现类
 * <p>
 * Created by ${user} at ${datetime}
 */
@Service
public class ${classSimpleName}ServiceImpl implements ${classSimpleName}Service {

    private static final Logger logger = LoggerFactory.getLogger(${classSimpleName}ServiceImpl.class);

    @Resource
    private ${classSimpleName}Dao ${classVariableName}Dao;

    /**
     * 保存处理
     */
    @Override
    public void save${classSimpleName}(${classSimpleName} ${classVariableName}) {
        //TODO 校验与赋值映射

        int affect = ${classVariableName}Dao.insert(${classVariableName});
        logger.info("保存${classSimpleName} affect:{}, ${classVariableName}: {}", affect, ${classVariableName});
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void update${classSimpleName}ById(${classSimpleName} ${classVariableName}) {
        ${classSimpleName} updater = new ${classSimpleName}();
        //TODO 值映射校验与赋值映射

        int affect = ${classVariableName}Dao.updateById(updater);
        logger.info("更新${classSimpleName} affect:{}, updater: {}", affect, updater);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void delete${classSimpleName}ById(${idType} ${id}) {
        //TODO 补充状态检测业务逻辑
        int affect = ${classVariableName}Dao.deleteById(${id});
        logger.info("删除${classSimpleName} affect:{}, id: {}", affect, id);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<${classSimpleName}> queryPagingResultByParam(${classSimpleName}Query query) {
        PagingResult<${classSimpleName}> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = ${classVariableName}Dao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<${classSimpleName}> ${classVariableName}List = ${classVariableName}Dao.selectListByParam(query);
            pagingResult.setRows(${classVariableName}List);
        }

        return pagingResult;
    }
}