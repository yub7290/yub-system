package com.yub.system.service.dict;

import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.dict.DictDataCreateReqDTO;
import com.yub.system.dto.dict.DictDataQueryDTO;
import com.yub.system.dto.dict.DictDataUpdateReqDTO;
import com.yub.system.vo.dict.DictDataOptionVO;

import java.util.List;

/**
 * 字典数据 Service 接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
public interface SysDictDataService {

    /**
     * 分页查询字典数据
     */
    PageResult<?> page(PageQuery<DictDataQueryDTO> pageQuery);

    /**
     * 获取详情
     */
    Object getDetail(Long id);

    /**
     * 新增
     */
    Long create(DictDataCreateReqDTO req);

    /**
     * 编辑
     */
    void update(DictDataUpdateReqDTO req);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 根据字典类型编码查询启用的字典数据列表
     */
    List<DictDataOptionVO> getOptionsByCode(String code);
}
