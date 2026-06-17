package com.yub.system.dto.dict;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 新增字典类型请求 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Data
public class DictTypeCreateReqDTO {
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 100, message = "字典名称长度不能超过100个字符")
    private String name;

    @NotBlank(message = "字典编码不能为空")
    @Size(max = 100, message = "字典编码长度不能超过100个字符")
    private String code;

    private Integer sort = 0;

    private Integer status = 1;

    @Size(max = 500, message = "描述长度不能超过500个字符")
    private String description;
}
