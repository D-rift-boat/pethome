package com.hjtang.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

//缓存的配置
@Configuration
public class RedisConfig {
    @Resource
    private RedisConnectionFactory factory;

    //使用JSON进行序列化
    //用于 将数据以自定的格式存入 redis
    //通常 key 以String存储   value 以Json存储
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //JSON格式序列化对象   给value使用
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //字符串序列化对象    给key 用
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
         //序列化为string格式   key会以 String 字符串形式 存入redis
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //value的序列化    json格式        value会存为Json格式数据
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        //hash结构key的虚拟化   string格式    以 String 字符串形式 存入redis
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash结构value的虚拟化   json格式    存为Json格式数据
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }
}