package com.yub.system.service.banner;

import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.banner.BannerCreateReqDTO;
import com.yub.system.dto.banner.BannerPageReqDTO;
import com.yub.system.dto.banner.BannerUpdateReqDTO;
import com.yub.system.vo.banner.BannerDetailRespVO;
import com.yub.system.entity.system.SysBanner;
import com.yub.system.vo.banner.BannerPageRespVO;

import java.util.List;

/**
 * Banner 服务接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-22
 * @Description: Banner 管理服务
 * @Version: 1.0.0
 */
public interface SysBannerService {

    /**
     * 分页查询 Banner
     *
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    PageResult<BannerPageRespVO> page(PageQuery<BannerPageReqDTO> pageQuery);

    /**
     * 获取 Banner 详情
     *
     * @param id Banner ID
     * @return Banner 详情
     */
    BannerDetailRespVO getDetail(Long id);

    /**
     * 新增 Banner
     *
     * @param dto 新增参数
     * @return Banner ID
     */
    Long create(BannerCreateReqDTO dto);

    /**
     * 编辑 Banner
     *
     * @param dto 编辑参数
     */
    void update(BannerUpdateReqDTO dto);

    /**
     * 删除 Banner
     *
     * @param id Banner ID
     */
    void delete(Long id);

    /**
     * 查询所有启用状态的 Banner
     *
     * @return 启用状态的 Banner 列表
     */
    List<SysBanner> selectAllEnabled();
}
