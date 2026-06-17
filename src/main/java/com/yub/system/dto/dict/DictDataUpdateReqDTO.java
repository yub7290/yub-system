package com.yub.system.dto.dict;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 编辑字典数据请求 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Data
public class DictDataUpdateReqDTO {
    @NotNull(message = "字典数据ID不能为空")
    private Long id;

    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String label;

    @Size(max = 100, message = "字典值长度不能超过100个字符")
    private String value;

    private Integer sort;

    private Integer status;
}
