package com.atguigu.cloud.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger3Config {
    @Bean
    public GroupedOpenApi payApi() {
        return GroupedOpenApi.builder().group("pay").pathsToMatch("/pay/**").build();
    }
    @Bean
    public GroupedOpenApi otherApi() {
        return GroupedOpenApi.builder().group("other").pathsToMatch("/other/**").build();
    }
    @Bean
    public OpenAPI docsOpenApi(){
        return new OpenAPI()
                .info(new Info(){
                    {
                        title("支付服务接口文档");
                        version("1.0");
                        description("通用设计rest");
                    }
                })
                .externalDocs(new ExternalDocumentation()
                        .description("www.atguigu.com")
                        .url("https://yiyan.baidu.com"));
    }
}
