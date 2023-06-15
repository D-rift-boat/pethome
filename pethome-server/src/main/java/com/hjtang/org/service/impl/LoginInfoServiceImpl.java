package com.hjtang.org.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.LoginInfoQuery;
import com.hjtang.org.domain.LoginInfo;
import com.hjtang.org.mapper.LoginInfoMapper;
import com.hjtang.org.service.ILoginInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginInfoServiceImpl implements ILoginInfoService {

    @Resource
    private LoginInfoMapper mapper;

    @Override
    public List<LoginInfo> findAll() {
        return mapper.findAll();
    }

    @Override
    public PageInfo<LoginInfo> queryLoginInfo(LoginInfoQuery query) {

        return null;
    }

    @Override
    public LoginInfo findOne(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
//        mapper.delete(id);
    }

    @Override
    public void batchDelete(List<Long> ids) {
//        mapper.batchDelete(ids);
    }

    @Override
    public void save(LoginInfo employee) {
//        mapper.save(employee);
    }

    @Override
    public void update(LoginInfo employee) {
//        mapper.update(employee);
    }
}
