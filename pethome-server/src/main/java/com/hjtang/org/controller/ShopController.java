package com.hjtang.org.controller;

import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.EmployeeQuery;
import com.hjtang.basic.util.AjaxResult;
import com.hjtang.org.domain.Employee;
import com.hjtang.org.domain.Shop;
import com.hjtang.org.service.impl.EmployeeServiceImpl;
import com.hjtang.org.service.impl.LoginInfoServiceImpl;
import com.hjtang.org.service.impl.ShopServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    private EmployeeServiceImpl employeeService;

    @Resource
    private ShopServiceImpl shopService;

    @Resource
    private LoginInfoServiceImpl loginInfoService;

    @ApiOperation("查询所有店铺")
    @GetMapping
    public List<Shop> findAll(){
        return shopService.findAll();
    }

}
