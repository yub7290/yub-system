package com.yub.system.service.param.impl;

import com.yub.system.entity.param.SysParam;
import com.yub.system.mapper.param.SysParamMapper;
import com.yub.system.service.param.SysParamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统参数 Service 实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-07-03
 * @Description: 系统参数业务实现
 * @Version: 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysParamServiceImpl implements SysParamService {

    private final SysParamMapper sysParamMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValueByCode(String code) {
        return sysParamMapper.selectValueByCode(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveValue(String code, String value) {
        SysParam exist = sysParamMapper.selectByCode(code);
        if (exist != null) {
            sysParamMapper.updateValueByCode(code, value);
            log.info("系统参数已更新, code={}, value={}", code, value);
        } else {
            SysParam param = new SysParam();
            param.setCode(code);
            param.setValue(value);
            param.setSort(0);
            param.setStatus(1);
            param.setDeleted(0);
            sysParamMapper.insert(param);
            log.info("系统参数已新增, code={}, value={}", code, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SysParam> getListByGroupCode(String groupCode) {
        return sysParamMapper.selectByGroupCode(groupCode);
    }
}
