package cn.wstom.common.base;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data

public class R {


    private Boolean success;


    private Integer code;

    private String message;


    private Map<String,Object> data = new HashMap<String,Object>();

    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(20000);
        r.setMessage("操作成功");
        return r;
    }
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(20001);
        r.setMessage("操作失败");
        return r;
    }

    //链式编程
    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

}
