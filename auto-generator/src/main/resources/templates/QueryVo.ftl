package ${packagePrefix}.repository.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
<#list columnList as column>
<#if (column.javaType == "BigDecimal")>
import java.math.BigDecimal;]
<#break>
</#if>
</#list>

/**
 * ${tableComment}
 *
 * Created by ${user} at ${datetime}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ${classSimpleName}QueryVo {

    private static final long serialVersionUID = 1L;
    <#list columnList as column>

    <#if (column.columnProperty=='createTime') || (column.columnProperty=='modifyTime')>
    /**
     * 小于等于${column.columnComment}
     */
    private ${column.javaType} elt${column.columnProperty?cap_first};

    /**
     * 大于等于${column.columnComment}
     */
    private ${column.javaType} egt${column.columnProperty?cap_first};
    <#else>
    /**
     * ${column.columnComment}
     */
    private ${column.javaType} ${column.columnProperty};
    </#if>
    </#list>
}