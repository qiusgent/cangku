package com.wms.common;

import lombok.Data;

@Data
public class Result {
    private int code; //编码200/400
    private String msg; //成功、失败
    private Long total; //总记录数
    private Object data; //数据

    public static Result fail(){
        return result(400,"失败",0l,null);
    }

    public static Result suc(){
        return result(200,"成功",0l,null);
    }

    public static Result suc(Object data){
        return result(200,"成功",0l,data);
    }

    public static Result suc(Object data,Long total){
        return result(200,"成功",total,data);
    }
    private static Result result(int code,String msg,Long total,Object data){
        Result res = new Result();
        res.setData(data);
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        return res;
    }
}
