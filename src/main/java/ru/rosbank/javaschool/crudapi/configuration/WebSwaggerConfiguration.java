package ru.rosbank.javaschool.crudapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rosbank.javaschool.crudapi.entity.UserEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class WebSwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(UserEntity.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.rosbank.javaschool.crudapi.rest"))
                .paths(PathSelectors.any())
                .build();
    }
}

