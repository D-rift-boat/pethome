package com.hjtang.org.service;

import com.github.pagehelper.PageInfo;
import com.hjtang.basic.query.ShopQuery;
import com.hjtang.org.domain.Shop;

import java.util.List;

public interface IShopService {
    List<Shop> findAll();
    //条件 分页查询
    PageInfo<Shop> queryShop(ShopQuery query);
    //id单个查询
    Shop findOne(Long id);
    //根据id删除
    void delete(Long id);
    //批量删除
    void batchDelete(List<Long> ids);
    //新增
    void save(Shop shop);
    //修改
    void update(Shop shop);
}
