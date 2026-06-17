package com.yub.system.dto.dict;

import lombok.Data;

/**
 * 字典类型查询 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Data
public class DictTypeQueryDTO {
    private String code;
    private String name;
    private Integer status;
}
