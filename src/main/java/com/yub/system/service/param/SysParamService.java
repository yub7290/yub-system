package com.yub.system.service.param;

import com.yub.system.entity.param.SysParam;

import java.util.List;

/**
 * 系统参数 Service 接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-07-03
 * @Description: 系统参数业务接口
 * @Version: 1.0
 */
public interface SysParamService {

    /**
     * 根据参数编码查询参数值
     *
     * @param code 参数编码
     * @return 参数值，无则返回null
     */
    String getValueByCode(String code);

    /**
     * 保存参数值（编码存在则更新，不存在则新增）
     *
     * @param code  参数编码
     * @param value 参数值
     */
    void saveValue(String code, String value);

    /**
     * 根据分组编码查询参数列表
     *
     * @param groupCode 分组编码
     * @return 参数列表
     */
    List<SysParam> getListByGroupCode(String groupCode);
}
