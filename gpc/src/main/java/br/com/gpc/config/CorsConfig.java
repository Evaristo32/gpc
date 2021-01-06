package br.com.gpc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),  HttpMethod.DELETE.name(), HttpMethod.PATCH.name())
                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.ORIGIN, HttpHeaders.ACCEPT,HttpHeaders.CONTENT_TYPE)
                .allowCredentials(false).maxAge(3600);

        
    }

}
