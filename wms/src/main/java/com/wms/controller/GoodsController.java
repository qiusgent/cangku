package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goods;
import com.wms.service.GoodsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2024-06-23
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /*新增*/
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){

        return goodsService.save(goods)?Result.suc():Result.fail();
    }

    /*更新*/
    @PostMapping("/updata")
    public Result updata(@RequestBody Goods goods){
        return goodsService.updateById(goods)?Result.suc():Result.fail();
    }

    /*删除*/
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
        return goodsService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){

        HashMap param = query.getParam();
        String name=(String)param.get("name");
        /*System.out.println("name==="+(String)param.get("name"));*/
        String goodstype = (String)param.get("goodstype");
        String storage = (String)param.get("storage");

        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            lambdaQueryWrapper.like(Goods::getName,name);
        }
        if(StringUtils.isNotBlank(goodstype)){
            lambdaQueryWrapper.eq(Goods::getGoodstype,goodstype);
        }
        if(StringUtils.isNotBlank(storage)){
            lambdaQueryWrapper.eq(Goods::getStorage,storage);
        }



        IPage result = goodsService.pageCC(page,lambdaQueryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }
}
