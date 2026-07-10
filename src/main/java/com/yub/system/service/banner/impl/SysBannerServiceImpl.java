package com.yub.system.service.banner.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.banner.BannerCreateReqDTO;
import com.yub.system.dto.banner.BannerPageReqDTO;
import com.yub.system.dto.banner.BannerUpdateReqDTO;
import com.yub.system.entity.system.SysBanner;
import com.yub.system.mapper.system.SysBannerMapper;
import com.yub.system.service.banner.SysBannerService;
import com.yub.system.vo.banner.BannerDetailRespVO;
import com.yub.system.vo.banner.BannerPageRespVO;
import com.yub.framework.security.SecurityUtils;
import com.yub.framework.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Banner 服务实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-22
 * @Description: Banner 管理服务实现
 * @Version: 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysBannerServiceImpl implements SysBannerService {

    private final SysBannerMapper sysBannerMapper;

    @Override
    public PageResult<BannerPageRespVO> page(PageQuery<BannerPageReqDTO> pageQuery) {
        BannerPageReqDTO queryParam = pageQuery.getQueryParam();
        com.yub.common.model.PageParam pageParam = pageQuery.getPageParam();

        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysBanner> list = sysBannerMapper.selectPage(queryParam);
        PageInfo<SysBanner> pageInfo = new PageInfo<>(list);

        List<BannerPageRespVO> records = BeanUtils.copyList(list, BannerPageRespVO.class);
        return PageResult.of(records, pageInfo.getTotal());
    }

    @Override
    public BannerDetailRespVO getDetail(Long id) {
        SysBanner banner = sysBannerMapper.selectById(id);
        return BeanUtils.copy(banner, BannerDetailRespVO.class);
    }

    @Override
    public Long create(BannerCreateReqDTO dto) {
        SysBanner banner = new SysBanner();
        banner.setImageUrl(dto.getImageUrl());
        banner.setLinkUrl(dto.getLinkUrl() != null ? dto.getLinkUrl() : "");
        banner.setSort(dto.getSort() != null ? dto.getSort() : 0);
        banner.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        Long currentUserId = SecurityUtils.getCurrentUserId();
        banner.setCreateUser(String.valueOf(currentUserId));
        banner.setUpdateUser(String.valueOf(currentUserId));
        sysBannerMapper.insert(banner);
        return banner.getId();
    }

    @Override
    public void update(BannerUpdateReqDTO dto) {
        SysBanner banner = new SysBanner();
        banner.setId(dto.getId());
        banner.setImageUrl(dto.getImageUrl());
        banner.setLinkUrl(dto.getLinkUrl() != null ? dto.getLinkUrl() : "");
        banner.setSort(dto.getSort() != null ? dto.getSort() : 0);
        banner.setStatus(dto.getStatus());
        banner.setUpdateUser(String.valueOf(SecurityUtils.getCurrentUserId()));
        sysBannerMapper.updateById(banner);
    }

    @Override
    public void delete(Long id) {
        sysBannerMapper.deleteById(id);
    }

    @Override
    public List<SysBanner> selectAllEnabled() {
        return sysBannerMapper.selectAllEnabled();
    }
}
