package com.yub.system.dto.dict;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 新增字典数据请求 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Data
public class DictDataCreateReqDTO {
    @NotNull(message = "字典类型ID不能为空")
    private Long typeId;

    @NotBlank(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String label;

    @NotBlank(message = "字典值不能为空")
    @Size(max = 100, message = "字典值长度不能超过100个字符")
    private String value;

    private Integer sort = 0;

    private Integer status = 1;
}
