package com.example.demo.config;

import com.example.demo.security.AuthInterceptor;
import com.example.demo.web.RequestTimingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new RequestTimingInterceptor())
            .addPathPatterns("/**");

    registry.addInterceptor(new AuthInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/login/**", "/403",
                "/css/**", "/js/**", "/images/**", "/webjars/**"
            );
  }

  // để khỏi 404 khi vào "/"
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/login");
  }
}
