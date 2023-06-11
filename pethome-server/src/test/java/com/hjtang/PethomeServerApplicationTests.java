package com.hjtang;

import com.hjtang.basic.query.DepartmentQuery;
import com.hjtang.org.mapper.DepartmentMapper;
import com.hjtang.org.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class PethomeServerApplicationTests {

    @Autowired
    DepartmentMapper dptmapper;

    @Resource
    DepartmentServiceImpl service;

    @Test
    void contextLoads() {
//        dptmapper.findAll().forEach(System.out::println);

        DepartmentQuery query = new DepartmentQuery();
        System.out.println(service.queryDepartment(query));
    }
    @Test
    void tes(){

    }

}
