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
                .allowedOrigins("http://localhost:3000") // Permitir requisições apenas dessa origem (desenvolvimento)
                .allowedOrigins("http://44.217.25.5")    // Permitir requisições do IP elástico da EC2 pública
                // Remova ou corrija os allowedOriginPatterns incorretos:
                // .allowedOriginPatterns("44.217.25.5:80/*") // INCORRETO
                // .allowedOriginPatterns("http://44.217.25.5/*") // NÃO IDEAL PARA IP ESPECÍFICO
                //.allowedOrigins("http://localhost:3000") // Permitir requisições apenas dessa origem
                //.allowedOrigins("http://44.217.25.5:80")
                //.allowedOriginPatterns("44.217.25.5:80/*")
                //.allowedOriginPatterns("http://44.217.25.5/*")
                .allowedOriginPatterns("http://10.0.0.*")
                .allowedOriginPatterns("http://10.0.1.*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*"); // Permitir todos os cabeçalhos
    }
}

