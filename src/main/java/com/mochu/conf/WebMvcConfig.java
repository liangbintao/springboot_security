package com.mochu.conf;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {

                ErrorPage errorPage403 = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404");

                ErrorPage errorPage401 = new ErrorPage(HttpStatus.UNAUTHORIZED, "/login.html");

                factory.addErrorPages(errorPage403, errorPage404, errorPage401);
            }
        };
    }
}
