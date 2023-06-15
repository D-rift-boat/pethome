package com.hjtang.org.service;

import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.BaseQuery;
import com.hjtang.basic.query.EmployeeQuery;
import com.hjtang.org.domain.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> findAll();
    //条件 分页查询
    PageInfo<Employee> queryEmployee(EmployeeQuery query);
    //id单个查询
    Employee findOne(Long id);
    //根据id删除
    void delete(Long id);
    //批量删除
    void batchDelete(List<Long> ids);
    //新增
    void save(Employee employee);
    //修改
    void update(Employee employee);
}
