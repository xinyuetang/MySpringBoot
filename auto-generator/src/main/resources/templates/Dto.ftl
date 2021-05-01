package ${packagePrefix}.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<#list columnList as column>
	<#if (column.javaType == "BigDecimal")>
    import java.math.BigDecimal;
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
public class ${classSimpleName}Dto implements Serializable {

	private static final long serialVersionUID = 1L;
	<#list columnList as column>

    /**
    *
    * 字段备注:${column.columnComment} <p>
    * 数据库字段长度:(${column.columnPrecision},${column.columnScale}) <p>
    * 是否索引:${column.index?string('是', '不是')}
    */
	private ${column.javaType} ${column.columnProperty};
	</#list>
}

