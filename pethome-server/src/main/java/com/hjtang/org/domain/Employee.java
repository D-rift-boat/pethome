package com.hjtang.org.domain;

import com.hjtang.basic.annotation.Phone;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class Employee implements Serializable {
    //员工ID
    private Long id;
    //员工名称
    private String username;
    //员工电话
    @Phone
    private String phone;
    //员工邮箱
    @NotEmpty(message = "不能为空")
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
    //部门
    private Department department;
    //登录信息
    private LoginInfo loginInfo;
    //公司
    private Shop shop;

}
