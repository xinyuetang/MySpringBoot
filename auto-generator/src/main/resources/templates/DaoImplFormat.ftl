package ${packagePrefix}.repository.dao.impl;

import ${packagePrefix}.repository.dao.${classSimpleName}Dao;
import ${packagePrefix}.repository.mapper.${classSimpleName}Mapper;
import ${packagePrefix}.repository.entity.${classSimpleName};
import ${packagePrefix}.repository.query.${classSimpleName}Query;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${classSimpleName}Dao 实现类
 * <p>
 * Created by ${user} at ${datetime}
 */
@Repository
public class ${classSimpleName}DaoImpl implements ${classSimpleName}Dao {

    @Resource
    private ${classSimpleName}Mapper ${classVariableName}Mapper;

    @Override
    public int insert(${classSimpleName} ${classVariableName}) {
        Assert.notNull(${classVariableName}, "保存对象不能为空");

        validateEntity(${classVariableName});

        return ${classVariableName}Mapper.insert(${classVariableName});
    }

    @Override
    public int bulkUpsert(List<${classSimpleName}> ${classVariableName}List){
        Assert.notEmpty(${classVariableName}List, "保存对象列表不能为空");

        for(${classSimpleName} ${classVariableName} : ${classVariableName}List){
            validateEntity(${classVariableName});
        }

        return ${classVariableName}Mapper.bulkUpsert(${classVariableName}List);
    }

    private void validateEntity(${classSimpleName} ${classVariableName}) {
<#list columnList as column>
    <#if (column.allowEmpty == false)>
        <#if (column.javaType == "String")>
        Assert.hasText(${classVariableName}.get${column.columnProperty?cap_first}(), "${column.columnComment}不能为空");
        <#else>
        Assert.notNull(${classVariableName}.get${column.columnProperty?cap_first}(), "${column.columnComment}必须有值");
        </#if>
    </#if>
</#list>
    }

    @Override
    public int updateById(${classSimpleName} ${classVariableName}) {
        Assert.notNull(${classVariableName}, "更新对象不能为空");
        Assert.notNull(${classVariableName}.getId(), "更新对象id不能为空");
        Assert.notNull(${classVariableName}.getModifyTime(), "更新时间必须有值");

        return ${classVariableName}Mapper.updateById(${classVariableName});
    }

    @Override
    public int deleteById(${idType} ${id}) {
        Assert.notNull(${id}, "删除记录id不能为空");

        return ${classVariableName}Mapper.deleteById(${id});
    }

    @Override
    public List<${classSimpleName}> selectListByParam(${classSimpleName}Query query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return ${classVariableName}Mapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(${classSimpleName}Query query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return ${classVariableName}Mapper.selectCountByParam(query);
    }

    private void validateQueryParameter(${classSimpleName}Query query) {
        query.validateBaseArgument();

        if (query.get${primaryColumnClass.columnProperty?cap_first}() == null
                && query.getGt${primaryColumnClass.columnProperty?cap_first}() == null
        <#list indexColumnList as column>       && query.get${column.columnProperty?cap_first}() == null<#if (column_has_next)>
        </#if></#list>) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }
    }
}