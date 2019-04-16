package com.kk.securityhttp.domain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zhangkai on 16/9/19.
 */

public class ResultInfo<T> {
    public int code;
	
    @JSONField(name = "msg")
    public String message;

    public T data;

    @Override
    public String toString() {
        return "ResultInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
