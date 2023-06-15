package com.hjtang.org.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.EmployeeQuery;
import com.hjtang.org.domain.Employee;
import com.hjtang.org.mapper.EmployeeMapper;
import com.hjtang.org.service.IEmployeeService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Resource
    private EmployeeMapper mapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<Employee> findAll() {
        return mapper.findAll();
    }

    @Override
    public PageInfo<Employee> queryEmployee(EmployeeQuery query) {

        PageHelper.startPage(query.getCurrentPage(),query.getPageSize());

        PageInfo<Employee> pageInfo = new PageInfo<>(mapper.queryEmployee(query));
        return pageInfo;
    }

    @Override
    public Employee findOne(Long id) {
        return mapper.findOne(id);
    }

    @Override
    public void delete(Long id) {
        mapper.delete(id);

        //更新数据库后  清空缓存无极部门树  下次重查 同步数据
        redisTemplate.delete("deptTree");
    }

    @Override
    public void batchDelete(List<Long> ids) {
        mapper.batchDelete(ids);

        //更新数据库后  清空缓存无极部门树  下次重查 同步数据
        redisTemplate.delete("deptTree");
    }

    @Override
    public void save(Employee employee) {
        mapper.save(employee);

    }

    @Override
    public void update(Employee employee) {
        mapper.update(employee);

        //更新数据库后  清空缓存无极部门树  下次重查 同步数据
        redisTemplate.delete("deptTree");
    }
}
