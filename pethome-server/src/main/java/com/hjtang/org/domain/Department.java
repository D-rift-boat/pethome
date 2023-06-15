package com.hjtang.org.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Department implements Serializable {
    //部门ID
    private Long id;
    //部门编号
    @NotBlank(message="sn不能为空")
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
    //管理员对象
    private Employee manager;
    //父级部门对象
    private Department parentDepartment;
    //子级部门集合  EL ui前端级联  需要children字段数据  故此变量名必须为children
    //此注解下   字段为空  则不返回此字段
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Department> children = new ArrayList<>();
}
