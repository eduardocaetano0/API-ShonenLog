package br.com.shonenlog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        Contact contact = new Contact();
        contact.name("Eduardo Caetano");
        contact.email("eduardocaetano110@gmail.com");
        contact.setUrl("https://github.com/eduardocaetano0/API-ShonenLog");
        Info info = new Info();

        info.title("ShonenLog");
        info.version("V1.0");
        info.description("Aplicação para genrênciamento de catálogos de anime.");
        info.contact(contact);
        return new OpenAPI().info(info);
    }
}
