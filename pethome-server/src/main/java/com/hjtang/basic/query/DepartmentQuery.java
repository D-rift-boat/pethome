package com.hjtang.basic.query;

import lombok.Data;

@Data
public class DepartmentQuery extends BaseQuery{
    //级联查询参数
    private Long id;
}
