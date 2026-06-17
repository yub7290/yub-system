package com.yub.system.service.dict.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.framework.security.SecurityUtils;
import com.yub.framework.util.BeanUtils;
import com.yub.system.dto.dict.DictDataCreateReqDTO;
import com.yub.system.dto.dict.DictDataQueryDTO;
import com.yub.system.dto.dict.DictDataUpdateReqDTO;
import com.yub.system.entity.dict.SysDictData;
import com.yub.system.exception.SystemErrorCode;
import com.yub.system.exception.SystemException;
import com.yub.system.mapper.dict.SysDictDataMapper;
import com.yub.system.service.dict.SysDictDataService;
import com.yub.system.vo.dict.DictDataOptionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典数据 Service 实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl implements SysDictDataService {

    private final SysDictDataMapper sysDictDataMapper;

    @Override
    public PageResult<?> page(PageQuery<DictDataQueryDTO> pageQuery) {
        PageHelper.startPage(pageQuery.getPageParam().getPageNum(), pageQuery.getPageParam().getPageSize());
        List<SysDictData> list = sysDictDataMapper.selectPage(pageQuery.getQueryParam());
        PageInfo<SysDictData> pageInfo = new PageInfo<>(list);
        return PageResult.of(list, pageInfo.getTotal());
    }

    @Override
    public Object getDetail(Long id) {
        SysDictData data = sysDictDataMapper.selectById(id);
        if (data == null) {
            throw new SystemException(SystemErrorCode.DICT_DATA_NOT_FOUND);
        }
        return data;
    }

    @Override
    @Transactional
    public Long create(DictDataCreateReqDTO req) {
        SysDictData entity = new SysDictData();
        entity.setTypeId(req.getTypeId());
        entity.setLabel(req.getLabel());
        entity.setValue(req.getValue());
        entity.setSort(req.getSort() != null ? req.getSort() : 0);
        entity.setStatus(req.getStatus());
        Long currentUserId = SecurityUtils.getCurrentUserId();
        entity.setCreateBy(currentUserId);
        entity.setUpdateBy(currentUserId);
        sysDictDataMapper.insert(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public void update(DictDataUpdateReqDTO req) {
        SysDictData exist = sysDictDataMapper.selectById(req.getId());
        if (exist == null) {
            throw new SystemException(SystemErrorCode.DICT_DATA_NOT_FOUND);
        }
        SysDictData entity = new SysDictData();
        entity.setId(req.getId());
        entity.setLabel(req.getLabel());
        entity.setValue(req.getValue());
        entity.setSort(req.getSort());
        entity.setStatus(req.getStatus());
        entity.setUpdateBy(SecurityUtils.getCurrentUserId());
        sysDictDataMapper.updateById(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysDictData exist = sysDictDataMapper.selectById(id);
        if (exist == null) {
            throw new SystemException(SystemErrorCode.DICT_DATA_NOT_FOUND);
        }
        sysDictDataMapper.deleteById(id);
    }

    @Override
    public List<DictDataOptionVO> getOptionsByCode(String code) {
        return BeanUtils.copyList(sysDictDataMapper.selectByCode(code), DictDataOptionVO.class);
    }
}
