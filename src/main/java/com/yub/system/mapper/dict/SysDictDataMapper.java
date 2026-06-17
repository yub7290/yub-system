package com.yub.system.mapper.dict;

import com.yub.system.dto.dict.DictDataQueryDTO;
import com.yub.system.entity.dict.SysDictData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典数据 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@Mapper
public interface SysDictDataMapper {

    List<SysDictData> selectPage(@Param("query") DictDataQueryDTO query);

    SysDictData selectById(@Param("id") Long id);

    List<SysDictData> selectByTypeId(@Param("typeId") Long typeId);

    List<SysDictData> selectByCode(@Param("code") String code);

    int insert(SysDictData dictData);

    int updateById(SysDictData dictData);

    int deleteById(@Param("id") Long id);

    int deleteByTypeId(@Param("typeId") Long typeId);
}
