package tech.code.codetech.api.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsController implements WebMvcConfigurer  {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir todas as rotas
                .allowedOrigins("http://3.217.128.85")
                .allowedOriginPatterns("http://3.217.128.85:80")
                .allowedOriginPatterns("http://localhost:3000")
                .allowedOriginPatterns("http://10.0.0.*")
                .allowedOriginPatterns("http://10.0.1.*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*"); // Permitir todos os cabeçalhos
    }
}

