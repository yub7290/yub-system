package com.yub.system.mapper.param;

import com.yub.system.entity.param.SysParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据参数编码查询系统参数
     *
     * @param code 参数编码
     * @return 系统参数，无则返回null
     */
    SysParam selectByCode(@Param("code") String code);

    /**
     * 根据分组编码查询参数列表
     *
     * @param groupCode 分组编码
     * @return 参数列表
     */
    List<SysParam> selectByGroupCode(@Param("groupCode") String groupCode);

    /**
     * 根据参数编码更新参数值
     *
     * @param code  参数编码
     * @param value 参数值
     * @return 影响行数
     */
    int updateValueByCode(@Param("code") String code, @Param("value") String value);

    /**
     * 新增系统参数
     *
     * @param sysParam 系统参数
     * @return 影响行数
     */
    int insert(SysParam sysParam);
}
