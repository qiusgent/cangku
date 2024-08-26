package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Info;
import com.wms.service.InfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2024-06-27
 */
@RestController
@RequestMapping("/info")
public class InfoController {
    @Resource
    private InfoService iInfoService;

    // 查询记录条数
    @GetMapping("/list")
    public int list() {
        return iInfoService.list().size();
    }

    //新增
    @PostMapping("/save")
    public boolean save(@RequestBody Info info) {
        return iInfoService.save(info);
    }

    //删除
    @GetMapping("/delete")
    public Result delete(Integer id) {
        return iInfoService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 前端用这个查询
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String info = (String) param.get("info");


        Page<Info> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Info> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(info) && !"null".equals(info)) lambdaQueryWrapper.like(Info::getInfo, info);


        IPage result = iInfoService.pageCC(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }
}
