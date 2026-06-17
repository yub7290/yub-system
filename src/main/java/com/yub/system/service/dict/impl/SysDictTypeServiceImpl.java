package com.yub.system.service.dict.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.framework.security.SecurityUtils;
import com.yub.framework.util.BeanUtils;
import com.yub.system.dto.dict.DictTypeCreateReqDTO;
import com.yub.system.dto.dict.DictTypeQueryDTO;
import com.yub.system.dto.dict.DictTypeUpdateReqDTO;
import com.yub.system.entity.dict.SysDictType;
import com.yub.system.exception.SystemErrorCode;
import com.yub.system.exception.SystemException;
import com.yub.system.mapper.dict.SysDictTypeMapper;
import com.yub.system.service.dict.SysDictTypeService;
import com.yub.system.vo.dict.DictTypeDetailRespVO;
import com.yub.system.vo.dict.DictTypePageRespVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典类型 Service 实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl implements SysDictTypeService {

    private final SysDictTypeMapper sysDictTypeMapper;

    @Override
    public PageResult<DictTypePageRespVO> page(PageQuery<DictTypeQueryDTO> pageQuery) {
        PageHelper.startPage(pageQuery.getPageParam().getPageNum(), pageQuery.getPageParam().getPageSize());
        List<SysDictType> list = sysDictTypeMapper.selectPage(pageQuery.getQueryParam());
        PageInfo<SysDictType> pageInfo = new PageInfo<>(list);
        List<DictTypePageRespVO> records = BeanUtils.copyList(list, DictTypePageRespVO.class);
        return PageResult.of(records, pageInfo.getTotal());
    }

    @Override
    public DictTypeDetailRespVO getDetail(Long id) {
        SysDictType dictType = sysDictTypeMapper.selectById(id);
        if (dictType == null) {
            throw new SystemException(SystemErrorCode.DICT_TYPE_NOT_FOUND);
        }
        return BeanUtils.copy(dictType, DictTypeDetailRespVO.class);
    }

    @Override
    @Transactional
    public Long create(DictTypeCreateReqDTO req) {
        SysDictType exist = sysDictTypeMapper.selectByCode(req.getCode());
        if (exist != null) {
            throw new SystemException(SystemErrorCode.DICT_TYPE_EXISTS);
        }
        SysDictType entity = new SysDictType();
        entity.setName(req.getName());
        entity.setCode(req.getCode());
        entity.setSort(req.getSort() != null ? req.getSort() : 0);
        entity.setStatus(req.getStatus());
        entity.setDescription(req.getDescription());
        Long currentUserId = SecurityUtils.getCurrentUserId();
        entity.setCreateBy(currentUserId);
        entity.setUpdateBy(currentUserId);
        sysDictTypeMapper.insert(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public void update(DictTypeUpdateReqDTO req) {
        SysDictType exist = sysDictTypeMapper.selectById(req.getId());
        if (exist == null) {
            throw new SystemException(SystemErrorCode.DICT_TYPE_NOT_FOUND);
        }
        SysDictType entity = new SysDictType();
        entity.setId(req.getId());
        entity.setName(req.getName());
        entity.setCode(req.getCode());
        entity.setSort(req.getSort());
        entity.setStatus(req.getStatus());
        entity.setDescription(req.getDescription());
        entity.setUpdateBy(SecurityUtils.getCurrentUserId());
        sysDictTypeMapper.updateById(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysDictType exist = sysDictTypeMapper.selectById(id);
        if (exist == null) {
            throw new SystemException(SystemErrorCode.DICT_TYPE_NOT_FOUND);
        }
        sysDictTypeMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void changeStatus(Long id, Integer status) {
        SysDictType exist = sysDictTypeMapper.selectById(id);
        if (exist == null) {
            throw new SystemException(SystemErrorCode.DICT_TYPE_NOT_FOUND);
        }
        sysDictTypeMapper.updateStatus(id, status);
    }
}
