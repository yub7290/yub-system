package com.yub.system.vo.dict;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 字典类型详情响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictTypeDetailRespVO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Integer sort;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
