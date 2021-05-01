package ${packagePrefix}.inner.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
<#list columnList as column>
<#if (column.javaType == "BigDecimal")>
import java.math.BigDecimal;]
<#break>
</#if>
</#list>

/**
 * ${tableComment}
 * <p>
 * Created by ${user} at ${datetime}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ${classSimpleName}QueryDto {

    private static final long serialVersionUID = 1L;
    <#list columnList as column>

    <#if (column.columnProperty=='createTime') || (column.columnProperty=='modifyTime')>
    /**
     * 字段备注:小于等于${column.columnComment} <p>
     * 数据库字段长度:(${column.columnPrecision},${column.columnScale}) <p>
     * 索引字段:${column.index?string("是","不是")}
     */
    private ${column.javaType} elt${column.columnProperty?cap_first};

    /**
     * 字段备注:大于等于${column.columnComment} <p>
     * 数据库字段长度:(${column.columnPrecision},${column.columnScale}) <p>
     * 索引字段:${column.index?string("是","不是")}
     */
    private ${column.javaType} egt${column.columnProperty?cap_first};
    <#else>
    /**
     * 字段备注:${column.columnComment} <p>
     * 数据库字段长度:(${column.columnPrecision},${column.columnScale}) <p>
     * 索引字段:${column.index?string("是","不是")}
     */
    private ${column.javaType} ${column.columnProperty};
    </#if>
    </#list>
}