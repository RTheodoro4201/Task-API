package io.github.rtheodoro4201.task_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI taskApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task API")
                        .version("1.0.0")
                        .description("API REST para gerenciamento de tarefas desenvolvida com Java 21 e Spring Boot.")
                        .contact(new Contact().name("RTheodoro4201").url("https://github.com/RTheodoro4201"))
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}

