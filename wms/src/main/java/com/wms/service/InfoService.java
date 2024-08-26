package com.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Info;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2024-06-27
 */
public interface InfoService extends IService<Info> {
    IPage pageCC(IPage<Info> page, Wrapper wrapper);
}
