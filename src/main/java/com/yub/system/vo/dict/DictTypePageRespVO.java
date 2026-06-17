package com.yub.system.vo.dict;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 字典类型分页响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictTypePageRespVO {
    private Long id;
    private String name;
    private String code;
    private Integer sort;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
}
