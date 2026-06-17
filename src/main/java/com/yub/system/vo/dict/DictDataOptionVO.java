package com.yub.system.vo.dict;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典数据简单 VO（供下拉框使用）
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictDataOptionVO {
    private Long id;
    private String label;
    private String value;
    private Integer sort;
}
