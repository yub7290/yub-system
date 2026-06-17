package com.yub.system.vo.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色选项 VO（下拉框使用）
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 角色选项响应参数
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleOptionRespVO {

    /** 角色ID */
    private Long id;

    /** 角色名称 */
    private String name;

    /** 角色编码 */
    private String code;
}
