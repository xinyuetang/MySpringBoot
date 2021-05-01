package ${packagePrefix}.inner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
<#list columnList as column>
    <#if (column.javaType == "BigDecimal")>
import java.math.BigDecimal;
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
@ToString
public class ${classSimpleName} implements Serializable {

    private static final long serialVersionUID = 1L;
<#list columnList as column>

    /**
     * 字段备注:${column.columnComment} <p>
     * 数据库字段长度:(${column.columnPrecision},${column.columnScale}) <p>
     * 是否索引:${column.index?string('是', '不是')}
     */
    private ${column.javaType} ${column.columnProperty};
</#list>
}

