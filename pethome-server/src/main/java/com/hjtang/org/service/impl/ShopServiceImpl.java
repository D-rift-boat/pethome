package com.hjtang.org.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.ShopQuery;
import com.hjtang.org.domain.Shop;
import com.hjtang.org.mapper.ShopMapper;
import com.hjtang.org.service.IShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShopServiceImpl implements IShopService {

    @Resource
    private ShopMapper mapper;

    @Override
    public List<Shop> findAll() {
        return mapper.findAll();
    }

    @Override
    public PageInfo<Shop> queryShop(ShopQuery query) {
        return null;
    }

    @Override
    public Shop findOne(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public void batchDelete(List<Long> ids) {
    }

    @Override
    public void save(Shop shop) {
    }

    @Override
    public void update(Shop shop) {
    }
}
