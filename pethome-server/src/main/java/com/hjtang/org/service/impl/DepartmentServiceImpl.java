package com.hjtang.org.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.BaseQuery;
import com.hjtang.org.domain.Department;
import com.hjtang.org.mapper.DepartmentMapper;
import com.hjtang.org.service.IDepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Resource
    DepartmentMapper mapper;

    @Override
    public List<Department> findAll() {
        return mapper.findAll();
    }

    @Override
    public PageInfo<Department> queryDepartment(BaseQuery query) {
        //分页插件使用    自动在sql语句中 插入limit分页代码
        PageHelper.startPage(query.getCurrentPage(), query.getPageSize());
        //将mapper查询结果存入 pageInfo的 list中
        PageInfo<Department> pageInfo = new PageInfo<>(mapper.queryDepartment(query));

        return pageInfo;
    }

    @Override
    public Department findOne(Long id) {
        return mapper.findOne(id);
    }

    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }

    @Override
    public void batchDelete(List<Long> ids) {
        mapper.batchDelete(ids);
    }

    @Override
    public void save(Department department) {
        mapper.save(department);
    }

    @Override
    public void update(Department department) {
        mapper.update(department);
    }
}
