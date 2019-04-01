package com.codvision.terminal.common;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntity<T> {
    private int code ;
    private String message ;
    private int total;
    private T data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ResponseEntity() {
    }

    public ResponseEntity(T date) {
        this.data = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
        if (ERROR_CODE.containsKey(code)) {
            setMessage(ERROR_CODE.get(code));
        } else {
            setMessage(ERROR_CODE.get(990000));
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private Map<Integer, String> ERROR_CODE = new HashMap<Integer, String>() {
        {
            put(100, "暂无数据");
            put(200, "成功");
            put(10000, "通用错误");
            ///用户类
            put(10001, "用户名或密码错误");
            put(10002, "登录状态已过期");
            put(10003, "注册用户已存在");
            put(10004, "钉钉授权登录失败，请稍后再试");
            ///操作权限类
            put(20001, "无操作权限");
            ///参数类
            put(30001, "非法参数");
            ////数据操作类
            put(40001, "添加数据失败");
            put(40002, "更新数据失败");
            put(40003, "删除数据失败");
            put(50001, "不存在的对象");

            put(990000, "系统错误");
        }
    };
}
