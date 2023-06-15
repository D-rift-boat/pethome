package com.hjtang.org.mapper;

import com.hjtang.basic.query.BaseQuery;
import com.hjtang.org.domain.Employee;
import com.hjtang.org.domain.Shop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopMapper {
    //查询所有
    List<Shop> findAll();
//    //条件 分页查询
//    List<Employee> queryEmployee(BaseQuery query);
//    //id单个查询
//    Employee findOne(Long id);
//    //根据id删除
//    void delete(Long id);
//    //批量删除
//    void batchDelete(List<Long> ids);
//    //新增
//    void save(Employee department);
//    //修改
//    void update(Employee department);
}
