package com.grommash88.app.config;

import com.fasterxml.classmate.TypeResolver;
import com.grommash88.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Autowired
  private TypeResolver typeResolver;

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com/grommash88/app"))
        .paths(PathSelectors.ant("/tasks/*"))
        .build()
        .additionalModels(typeResolver.resolve(Task.class))
        .apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo() {
    return new ApiInfoBuilder().title("Task tracker REST API")
        .description("Task Management REST API")
        .contact(new Contact("Lakeev Nikolay", "", "lakeev.nikolay2016@yandex.ru"))
        .license("Apache 2.0")
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .version("1.0.0")
        .build();
  }
}
