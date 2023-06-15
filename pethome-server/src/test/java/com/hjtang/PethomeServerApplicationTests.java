package com.hjtang;

import com.hjtang.basic.query.DepartmentQuery;
import com.hjtang.org.mapper.DepartmentMapper;
import com.hjtang.org.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
//
//        DepartmentQuery query = new DepartmentQuery();
//        System.out.println(service.queryDepartment(query));
    }
    @Test
    void testJedis(){
            //获取Jedis对象 = 连接对象，需要提供主机地址，端口号，超时时间
            //其中port和timeout可以不写，默认就是6379和2s钟
            Jedis jedis = new Jedis("localhost", 6379, 2000);
            //设置密码
            jedis.auth("123456");
            //添加键和值
            jedis.set("name", "akmai");
            //根据键获取值
            System.out.println(jedis.get("name"));
            //释放连接
            jedis.close();
    }

    @Test
    public void testSetAndGetByJedisPool(){
        //创建Jedis连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大空闲连接
        config.setMaxIdle(8);
        //设置最大连接数
        config.setMaxTotal(10);
        //设置最大等待时间
        config.setMaxWaitMillis(2000);
        //设置获取连接时做有效性校验
        config.setTestOnBorrow(true);//Borrow - 借用
        //获取连接池对象
        JedisPool pool = new JedisPool(config, "localhost", 6379, 2000, "123456");
        //获取连接
        Jedis connection = pool.getResource();
        //设置数据
        connection.set("id", "1");
        System.out.println(connection.get("id"));
        //释放或关闭连接
        connection.close();//如果检测到有连接池就是释放回连接池中，否则就是关闭连接
        pool.destroy();//摧毁连接池
    }

    //注入的名字  不可乱取
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testTemplate(){
//        redisTemplate.opsForValue().set("name","玄德");
//        System.out.println(redisTemplate.opsForValue().get("name"));
        System.out.println(redisTemplate.opsForValue().get("age"));
        System.out.println(redisTemplate.opsForValue().get("username"));
    }

}
