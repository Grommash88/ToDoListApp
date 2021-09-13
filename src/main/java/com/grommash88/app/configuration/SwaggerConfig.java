package com.grommash88.app.configuration;

import com.fasterxml.classmate.TypeResolver;
import com.grommash88.app.model.Task;
import com.grommash88.app.model.TaskRequestDTO;
import com.grommash88.app.model.User;
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

  private final TypeResolver typeResolver;

  public SwaggerConfig(TypeResolver typeResolver) {
    this.typeResolver = typeResolver;
  }

  @Bean
  public Docket SwaggerConfig() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.grommash88.app"))
        .paths(PathSelectors.any())
        .build()
        .additionalModels(typeResolver.resolve(Task.class), typeResolver.resolve(User.class),
            typeResolver.resolve(TaskRequestDTO.class))
        .apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo() {
    return new ApiInfoBuilder()
        .title("ToDoList application API")
        .contact(new Contact("Lakeev Nikolay", "https://github.com/Grommash88",
            "lakeev.nikolay2016@yandex.ru"))
        .version("1.0.0")
        .build();
  }
}