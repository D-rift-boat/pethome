package com.hjtang.org.controller;

import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.DepartmentQuery;
import com.hjtang.basic.util.AjaxResult;
import com.hjtang.org.domain.Department;
import com.hjtang.org.service.impl.DepartmentServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    DepartmentServiceImpl departmentService;

    @GetMapping
    public List<Department> findAll(){
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    public Department findOne(@PathVariable Long id){
        return departmentService.findOne(id);
    }

    @PostMapping
    public PageInfo<Department> queryDepartment(@RequestBody DepartmentQuery query){
        return departmentService.queryDepartment(query);
    }

    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            departmentService.delete(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("系统繁忙，请稍后重试");
        }
    }

    @PatchMapping
    public AjaxResult batchDelete(@RequestBody List<Long> ids){
        try {
            departmentService.batchDelete(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("系统繁忙，请稍后重试");
        }
    }

    @PutMapping
    public AjaxResult save(@RequestBody Department department){
        try {
            if (department.getId() != null){
                departmentService.update(department);
            }else {
                departmentService.save(department);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("系统繁忙，请稍后重试");
        }
    }


}
