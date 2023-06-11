package com.hjtang.org.domain;

import lombok.Data;

@Data
public class Department {
    //部门ID
    private Long id;
    //部门编号
    private String sn;
    //部门名称
    private String name;
    //部门路径
    private String dirPath;
    //部门状态：1启用,0禁用
    private Integer state = 1;
    //部门经理ID
    private Long managerId;
    //上级部门ID
    private Long parentId;
}
