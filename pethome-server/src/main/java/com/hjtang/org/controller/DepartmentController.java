package com.hjtang.org.controller;

import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.DepartmentQuery;
import com.hjtang.basic.util.AjaxResult;
import com.hjtang.org.domain.Department;
import com.hjtang.org.service.impl.DepartmentServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    DepartmentServiceImpl departmentService;

    @GetMapping
    public List<Department> findAll() {
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    public Department findOne(@PathVariable Long id) {
        return departmentService.findOne(id);
    }

    @PostMapping
    public PageInfo<Department> queryDepartment(@RequestBody DepartmentQuery query) {
        return departmentService.queryDepartment(query);
    }

    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            departmentService.delete(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("系统繁忙，请稍后重试");
        }
    }

    @PatchMapping
    public AjaxResult batchDelete(@RequestBody List<Long> ids) {
        try {
            departmentService.batchDelete(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("系统繁忙，请稍后重试");
        }
    }

    @PutMapping
    public AjaxResult save(@Validated @RequestBody Department department) {
        try {
            if (department.getId() != null) {
                departmentService.update(department);
            } else {
                departmentService.save(department);

                //返回自增的主键id后  拼接好dirPath  更新此id部门信息（dirPath存入数据库）
                String dirPath = department.getDirPath()+"/"+department.getId();
                department.setDirPath(dirPath);
                departmentService.update(department);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("系统繁忙，请稍后重试");
        }
    }

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping({"/dptTree/{id}","/dptTree"})
    public List<Department> dptTree(@PathVariable(value = "id", required = false) Long id) {

        return departmentService.deptTree(id);
    }
//    @GetMapping({"/dptTree/{id}","/dptTree"})
//    public List<Department> dptTree(@PathVariable(value = "id", required = false) Long id) {
//
//        List<Department> deptTree = (List<Department>)redisTemplate.opsForValue().get("deptTree");
//
//        if (deptTree != null){
//            return deptTree;
//        }else{
//            deptTree = departmentService.deptTree(id);
//
//            //若为配置序列化  会报错 序列化失败
//            redisTemplate.opsForValue().set("deptTree",deptTree);
//            return deptTree;
//        }
//    }
//

}
