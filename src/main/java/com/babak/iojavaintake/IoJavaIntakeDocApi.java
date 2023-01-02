package com.babak.iojavaintake;

import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IoJavaIntakeDocApi {

    @Bean
    public OperationCustomizer swaggerOperation() {
        return (operation, handlerMethod) -> operation.addParametersItem(
                new Parameter()
                        .in("header")
                        .required(false)
                        .description("Authorization")
                        .name("Authorization"));
    }
}
