package com.example.iplan_data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger config
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Configuration
@EnableSwagger2
public class SwaggerApp {

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .select()
                //Current packet path
                .apis(RequestHandlerSelectors.basePackage("com.example.iplan_data.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //Build the details function for the api documentation
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //page title
                .title("iPlan")
                //creator
                .contact(new Contact("iPlan", "http://localhost:8081/swagger-ui.html#/", "8081"))
                //version number
                .version("1.0")
                //describe
                .description("iPlan interface")
                .build();
    }
}