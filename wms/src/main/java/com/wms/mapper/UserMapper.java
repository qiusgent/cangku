package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    IPage<User> pageC(IPage<User> page);

    /*@Param(Constants.WRAPPER) Wrapper wrapper 是一个参数，表示包含查询条件的包装器对象。*/
    IPage<User> pageCC(IPage<User> page,@Param(Constants.WRAPPER) Wrapper wrapper);
}

