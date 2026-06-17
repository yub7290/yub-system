package com.yub.system.service.dict;

import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.dict.DictTypeCreateReqDTO;
import com.yub.system.dto.dict.DictTypeQueryDTO;
import com.yub.system.dto.dict.DictTypeUpdateReqDTO;
import com.yub.system.vo.dict.DictTypeDetailRespVO;
import com.yub.system.vo.dict.DictTypePageRespVO;

/**
 * 字典类型 Service 接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
public interface SysDictTypeService {
    PageResult<DictTypePageRespVO> page(PageQuery<DictTypeQueryDTO> pageQuery);
    DictTypeDetailRespVO getDetail(Long id);
    Long create(DictTypeCreateReqDTO req);
    void update(DictTypeUpdateReqDTO req);
    void delete(Long id);
    void changeStatus(Long id, Integer status);
}
