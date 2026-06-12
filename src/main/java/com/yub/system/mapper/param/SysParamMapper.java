package com.yub.system.mapper.param;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统参数 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 系统参数数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysParamMapper {

    /**
     * 根据参数编码查询参数值
     *
     * @param code 参数编码
     * @return 参数值，无则返回null
     */
    String selectValueByCode(@Param("code") String code);
}
