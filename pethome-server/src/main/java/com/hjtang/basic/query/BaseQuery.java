package com.hjtang.basic.query;

import lombok.Data;

@Data
public class BaseQuery {
    //当前页
    private Integer currentPage = 1;
    //每页显示几条数据
    private Integer pageSize = 5;
    //关键字
    private String keyword;
}
