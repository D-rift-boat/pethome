package com.hjtang.org.service;

import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.LoginInfoQuery;
import com.hjtang.org.domain.LoginInfo;

import java.util.List;

public interface ILoginInfoService {
    List<LoginInfo> findAll();
    //条件 分页查询
    PageInfo<LoginInfo> queryLoginInfo(LoginInfoQuery query);
    //id单个查询
    LoginInfo findOne(Long id);
    //根据id删除
    void delete(Long id);
    //批量删除
    void batchDelete(List<Long> ids);
    //新增
    void save(LoginInfo loginInfo);
    //修改
    void update(LoginInfo loginInfo);
}
