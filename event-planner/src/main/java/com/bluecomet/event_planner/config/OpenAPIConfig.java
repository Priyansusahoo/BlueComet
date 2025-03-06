package com.bluecomet.event_planner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author Priyansu
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Event Planner API")
                        .description("API documentation for Event Planner application")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Bluecomet Support")
                                .email("bluecomet.org@gmail.com")
                                .url("https://github.com/Priyansusahoo/BlueComet/issues"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
