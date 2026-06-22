package com.yub.system.mapper.system;

import com.yub.system.dto.banner.BannerPageReqDTO;
import com.yub.system.entity.system.SysBanner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Banner Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-22
 * @Description: Banner数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysBannerMapper {

    /**
     * 分页查询Banner列表
     *
     * @param query 查询参数
     * @return Banner列表
     */
    List<SysBanner> selectPage(@Param("query") BannerPageReqDTO query);

    /**
     * 根据ID查询Banner
     *
     * @param id Banner ID
     * @return Banner
     */
    SysBanner selectById(@Param("id") Long id);

    /**
     * 新增Banner
     *
     * @param banner Banner
     * @return 影响行数
     */
    int insert(SysBanner banner);

    /**
     * 更新Banner
     *
     * @param banner Banner
     * @return 影响行数
     */
    int updateById(SysBanner banner);

    /**
     * 删除Banner
     *
     * @param id Banner ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 查询所有启用Banner（按排序升序）
     *
     * @return Banner列表
     */
    List<SysBanner> selectAllEnabled();
}
