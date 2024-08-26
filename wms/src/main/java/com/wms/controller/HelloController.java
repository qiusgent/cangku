package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Menu;
import com.wms.entity.User;
import com.wms.service.MenuService;
import com.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
// @RestController 结合了@Controller和@ResponseBody两个注解的功能，并提供了更简洁的方式来编写处理HTTP请求并返回响应的控制器
public class HelloController {
    @GetMapping
    public String hello(){
        return "hello wms";
    }

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    /*新增*/
    @PostMapping("/save")
    public Result save(@RequestBody User user){

        return userService.save(user)?Result.suc():Result.fail();
    }

    /*更新*/
    @PostMapping("/updata")
    public Result updata(@RequestBody User user){
        return userService.updateById(user)?Result.suc():Result.fail();
    }

    /*删除*/
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
        return userService.removeById(id)?Result.suc():Result.fail();
    }

    /*登录*/
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list = userService.lambdaQuery()
                .eq(User::getNo,user.getNo())
                .eq(User::getPassword,user.getPassword()).list();


        if(list.size()>0){
            User user1 = (User)list.get(0);
            List menulist = menuService.lambdaQuery().like(Menu::getMenuright,user1.getRoleId()).list();
            /*HashMap key-value键值对的哈希表*/
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menulist);
            return Result.suc(res);
        }
        else {
            return Result.fail();
        }

    }

    /*修改*/
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }

    /*新增或修改*/
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    /*删除*/
    @PostMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }

    /*查询（模糊、匹配）*/
    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }
        return Result.suc(userService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query){
        /*System.out.println(query);*/
        /*System.out.println("num==="+query.getPageNum());
        System.out.println("size==="+query.getPageSize());*/
        HashMap param = query.getParam();
        System.out.println("name==="+(String)param.get("pageSize"));
        String name=(String)param.get("pageSize");
        /*System.out.println("no==="+(String)param.get("no"));*/

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,name);

        IPage result = userService.page(page, lambdaQueryWrapper);
        System.out.println("total==="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query){

        HashMap param = query.getParam();
        System.out.println("name==="+(String)param.get("pageSize"));
        String name=(String)param.get("pageSize");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,name);

        /*IPage result = userService.pageC(page);*/
        IPage result = userService.pageCC(page,lambdaQueryWrapper);
        System.out.println("total==="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){

        HashMap param = query.getParam();
        String name=(String)param.get("name");
        /*System.out.println("name==="+(String)param.get("name"));*/
        String sex=(String)param.get("sex");
        String roleId=(String)param.get("roleId");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        if(StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex,sex);
        }
        if(StringUtils.isNotBlank(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId,roleId);
        }


        /*IPage result = userService.pageC(page);*/
        IPage result = userService.pageCC(page,lambdaQueryWrapper);
        System.out.println("total==="+result.getTotal());

        return Result.suc(result.getRecords(),result.getTotal());
    }


    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }


}
