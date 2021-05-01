package ${packagePrefix}.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString
public class ${classSimpleName}Vo implements Serializable {

	private static final long serialVersionUID = 1L;
	<#list columnList as column>

    /**
     *
     * ${column.columnComment}
     */
	private ${column.javaType} ${column.columnProperty};
	</#list>
}

