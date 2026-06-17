package com.yub.system.dto.dict;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 编辑字典类型请求 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Data
public class DictTypeUpdateReqDTO {
    @NotNull(message = "字典类型ID不能为空")
    private Long id;

    @Size(max = 100, message = "字典名称长度不能超过100个字符")
    private String name;

    @Size(max = 100, message = "字典编码长度不能超过100个字符")
    private String code;

    private Integer sort;

    private Integer status;

    @Size(max = 500, message = "描述长度不能超过500个字符")
    private String description;
}
