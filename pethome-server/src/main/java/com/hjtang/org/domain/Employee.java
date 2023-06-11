package com.hjtang.org.domain;

import lombok.Data;

@Data
public class Employee {
    //员工ID
    private Long id;
    //员工名称
    private String username;
    //员工电话
    private String phone;
    //员工邮箱
    private String email;
    //员工盐值
    private String salt;
    //员工密码
    private String password;
    //员工年龄
    private Integer age;
    //员工状态：1启用 0禁用
    private Integer state = 1;
    //员工所属部门ID
    private Long departmentId;
    //登录信息ID
    private Long logininfoId;
    //店铺ID：为空=平台系统管理员，不为空=店铺管理员
    private Long shopId;
}
