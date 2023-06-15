package com.hjtang.org.controller;

import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.EmployeeQuery;
import com.hjtang.basic.util.AjaxResult;
import com.hjtang.org.domain.Employee;
import com.hjtang.org.domain.LoginInfo;
import com.hjtang.org.service.impl.EmployeeServiceImpl;
import com.hjtang.org.service.impl.LoginInfoServiceImpl;
import com.hjtang.org.service.impl.ShopServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/logininfo")
public class LoginInfoController {

    @Resource
    private EmployeeServiceImpl employeeService;

    @Resource
    private ShopServiceImpl shopService;

    @Resource
    private LoginInfoServiceImpl loginInfoService;

    @ApiOperation("查询所有登录信息")
    @GetMapping
    public List<LoginInfo> findAll(){
        return loginInfoService.findAll();
    }

}
