package com.sangto.stpos_c10.bean;

import java.io.Serializable;

/**
 * 服务器返回结果
 */
public class Result<T> implements Serializable {

    public int code;
    public T data;
    public String msg;

    public String total;  //  条目总数
    public String pagesize;  //  一共几页
}
