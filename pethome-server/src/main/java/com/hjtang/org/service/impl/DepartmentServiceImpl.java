package com.hjtang.org.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.DepartmentQuery;
import com.hjtang.org.domain.Department;
import com.hjtang.org.mapper.DepartmentMapper;
import com.hjtang.org.service.IDepartmentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Resource
    DepartmentMapper mapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<Department> findAll() {
        return mapper.findAll();
    }

    @Override
    public PageInfo<Department> queryDepartment(DepartmentQuery query) {
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
        //更新数据库后  清空缓存无极部门树  下次重查 同步数据
        redisTemplate.delete("deptTree");
    }

    @Override
    public void batchDelete(List<Long> ids) {
        mapper.batchDelete(ids);
        //更新数据库后  清空缓存无极部门树  下次重查 同步数据
        redisTemplate.delete("deptTree");
    }

//    @Override
//    public void save(Department department) {
//        mapper.save(department);
//        //更新数据库后  清空缓存无极部门树  下次重查 同步数据
//        redisTemplate.delete("deptTree");
//    }

    @Override
    //SpringCahe 版   @CacheEvict注解  删除缓存中的对应元素
    @CacheEvict(cacheNames = "dept", key = "'tree'")
    public void save(Department department) {
        mapper.save(department);

    }

//    @Override
//    public void update(Department department) {
//        mapper.update(department);
//        //更新数据库后  清空缓存无极部门树  下次重查 同步数据
//        redisTemplate.delete("deptTree");
//    }

    @Override
    public void update(Department department) {
        mapper.update(department);
        //更新数据库后  清空缓存无极部门树  下次重查 同步数据
        //结合SpringCache版
        redisTemplate.delete("dept::tree");
    }

    @Override
    //存入redis名称key为 dept::tree 只  能存返回值，不够灵活，比如你想秒杀商品数量-1
    @Cacheable(cacheNames = "dept", key = "'tree'")
    public List<Department> deptTree(Long id) {
        return getdepartment(id);
    }

//    redisTemplate处理 缓存查询
//    @Override
//    public List<Department> deptTree(Long id) {
//        List<Department> deptTree = (List<Department>)redisTemplate.opsForValue().get("deptTree");
//
//        if (deptTree != null){
//            return deptTree;
//        }else{
//            deptTree = getdepartment(id);
//
//            //若为配置序列化  会报错 序列化失败
//            redisTemplate.opsForValue().set("deptTree",deptTree);
//            return deptTree;
//        }
//
//    }

    @Cacheable(cacheNames = "dept", key = "tree")
    public List<Department> getdepartment(Long id) {
        //存储返回值
        List<Department> result = new ArrayList<>();

        List<Department> departments;
        if (id != null) {
            //查询 上级部门层级id路径   中部包含本部门的  部门信息  （查出 非本部门及本部门子级部门 的信息）
            departments = mapper.findOther(id);
        } else {
            departments = mapper.findAll();
        }

        Map<Long, Department> map = new HashMap<>();

        //将查询结果缓存到  map中   减少sql执行  提高效率
        for (Department department : departments) {
            map.put(department.getId(), department);
        }

        for (Department department : departments) {
            if (department.getParentId() != null) {
                //有父级部门   则存入父级的 children中
                map.get(department.getParentId()).getChildren().add(department);
            } else {
                //无父级  则为顶级部门  存入返回集合中
                result.add(department);
            }
        }

        //最终返回几个顶级 无父级部门（其children部门  无限嵌套子级）
        // 故顶级部门中 嵌套包含了其所有子级  返回父级即可
        return result;
    }


}
