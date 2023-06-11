package com.hjtang.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;

//就算有注解   也需要启动类扫描到 才会运行
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //Bean注解下的  方法返回值 被Spring容器管理
    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<>();
        pars.add(new ParameterBuilder()
                .name("token")
                .description("token")
                .required(false)
                .parameterType("header")
                .modelRef(new ModelRef("string"))
                .build()
        );
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //关闭 Swagger2
//                .enable(false)
                .select()
                //对外暴露服务的包,以controller的方式暴露,所以就是controller的包.
                .apis(RequestHandlerSelectors.basePackage("cn.itsource"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("宠物家园")
                .description("宠物家园接口文档说明")
                .contact(new Contact("tom", "", "tom@hjtang.com"))
                .version("1.0")
                .build();
    }
}