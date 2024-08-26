package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Info;
import com.wms.mapper.InfoMapper;
import com.wms.service.InfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wms
 * @since 2024-06-27
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {
    @Resource
    private InfoMapper infoMapper;

    @Override
    public IPage pageCC(IPage<Info> page, Wrapper wrapper) {
        return infoMapper.pageCC(page, wrapper);
    }
}
