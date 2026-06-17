package com.yub.system.dto.dict;

import lombok.Data;

/**
 * 字典数据查询 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Data
public class DictDataQueryDTO {
    private Long typeId;
    private String label;
    private Integer status;
}
