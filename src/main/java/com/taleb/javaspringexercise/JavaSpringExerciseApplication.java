package com.taleb.javaspringexercise;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "My API",
                version = "1.0",
                description = "API documentation for My Application"
        )
)
public class JavaSpringExerciseApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringExerciseApplication.class, args);
    }

}
