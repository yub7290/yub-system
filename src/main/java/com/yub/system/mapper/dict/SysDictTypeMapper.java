package com.yub.system.mapper.dict;

import com.yub.system.dto.dict.DictTypeQueryDTO;
import com.yub.system.entity.dict.SysDictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典类型 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Mapper
public interface SysDictTypeMapper {

    List<SysDictType> selectPage(@Param("query") DictTypeQueryDTO query);

    SysDictType selectById(@Param("id") Long id);

    SysDictType selectByCode(@Param("code") String code);

    List<SysDictType> selectAllEnabled();

    int insert(SysDictType dictType);

    int updateById(SysDictType dictType);

    int deleteById(@Param("id") Long id);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
