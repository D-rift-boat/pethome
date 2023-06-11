package com.hjtang.org.mapper;

import com.hjtang.basic.query.BaseQuery;
import com.hjtang.org.domain.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    //查询所有
    List<Department> findAll();
    //条件 分页查询
    List<Department> queryDepartment(BaseQuery query);
    //id单个查询
    Department findOne(Long id);
    //根据id删除
    void delete(Long id);
    //批量删除
    void batchDelete(List<Long> ids);
    //新增
    void save(Department department);
    //修改
    void update(Department department);
}
