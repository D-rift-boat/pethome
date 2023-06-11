package com.hjtang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//启动类也可以作为配置
//@SpringBootConfiguration
//启动Springboot自动装配Springboot自动加载 start配置类，从而不需要xmL配置
//@EnableAutoConfiguration
//自动扫描@Controller @Service @Repository @Component加入Spring IOC容器中
//只会扫描当前包及其子包
@SpringBootApplication
@MapperScan("com.hjtang.*.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
