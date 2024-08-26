package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goods;
import com.wms.entity.Info;
import com.wms.entity.Record;
import com.wms.service.GoodsService;
import com.wms.service.InfoService;
import com.wms.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2024-06-24
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private GoodsService goodsService;

    @Resource
    private InfoService iInfoService;


    /*新增*/
    @PostMapping("/save")
    public Result save(@RequestBody Record record){

        Goods goods = goodsService.getById(record.getGoods());
        int n = record.getCount();
        //出库
        if("2".equals(record.getAction())){
            n=-n;
            record.setCount(n);
        }
        // 入库
        int num = goods.getCount() + n;
        goods.setCount(num);
        goodsService.updateById(goods);

        // 库存数量不足发送通知预警
        List list = iInfoService.lambdaQuery().eq(Info::getGoods, record.getGoods()).list();

        if (goods.getCount() < 50 && list.size() == 0) {
            Info info = new Info();
            info.setInfo("货物：" + goods.getName() + "    库存数量少于50");
            info.setType(0);
            info.setGoods(record.getGoods());
            iInfoService.save(info);
        }
        // 库存足够则删除通知
        if (goods.getCount() >= 50 && list.size() > 0) {
            Info info = (Info) list.get(0);
            iInfoService.removeById(info.getId());
        }

        return recordService.save(record)?Result.suc():Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){

        HashMap param = query.getParam();
        String name=(String)param.get("name");
        /*System.out.println("name==="+(String)param.get("name"));*/
        String goodstype = (String)param.get("goodstype");
        String storage = (String)param.get("storage");
        String roleId = (String)param.get("roleId");
        String userid = (String) param.get("userid");

        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply(" a.goods=b.id AND b.storage=c.id AND b.goodsType=d.id ");
        if(StringUtils.isNotBlank(name)){
           queryWrapper.like("b.name",name);
        }
        if(StringUtils.isNotBlank(goodstype)){
            queryWrapper.eq("d.id",goodstype);
        }
        if(StringUtils.isNotBlank(storage)){
            queryWrapper.like("c.id",storage);
        }
        if("2".equals(roleId)){
            queryWrapper.apply(" a.userId= "+userid);
        }


        IPage result = recordService.pageCC(page,queryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }
}
