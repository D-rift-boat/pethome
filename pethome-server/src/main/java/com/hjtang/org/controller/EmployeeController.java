package com.hjtang.org.controller;

import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.EmployeeQuery;
import com.hjtang.basic.util.AjaxResult;
import com.hjtang.org.domain.Employee;
import com.hjtang.org.service.impl.EmployeeServiceImpl;
import com.hjtang.org.service.impl.LoginInfoServiceImpl;
import com.hjtang.org.service.impl.ShopServiceImpl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.simpleframework.xml.core.Validate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeServiceImpl employeeService;

    @Resource
    private ShopServiceImpl shopService;

    @Resource
    private LoginInfoServiceImpl loginInfoService;



    @ApiOperation("查询所有员工")
    @GetMapping
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    @ApiOperation("根据id查询单个员工")
    @GetMapping("/{id}")
    public Employee findOne(@ApiParam("员工id") @PathVariable Long id){
        return employeeService.findOne(id);
    }

    @ApiOperation("根据id删除单个员工")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@ApiParam("员工id")@PathVariable Long id){
            employeeService.delete(id);
            return AjaxResult.me();
    }

    @ApiOperation("批量删除")
    @PatchMapping
    public AjaxResult delete(@RequestBody List<Long> ids){

            employeeService.batchDelete(ids);
            return AjaxResult.me();
    }

    @ApiOperation("新增/修改")
    @PutMapping
    public AjaxResult save( @RequestBody @Validated Employee employee){
            if (employee.getId() != null){
                employeeService.update(employee);
            }else {
                employeeService.save(employee);
            }
            return AjaxResult.me();
    }

    @ApiOperation("条件分页查询")
    @PostMapping
    public PageInfo<Employee> queryEmployee(@RequestBody EmployeeQuery query){
        System.out.println("-------------------------------------");
        System.out.println(query.getCurrentPage());
        System.out.println(query.getPageSize());

        return employeeService.queryEmployee(query);
    }

}
