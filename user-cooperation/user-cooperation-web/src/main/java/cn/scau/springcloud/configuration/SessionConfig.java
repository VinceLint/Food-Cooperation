package cn.scau.springcloud.configuration;

import cn.scau.springcloud.interceptor.UserSessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SessionConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean httpSessionFilterRegistration() {
        FilterRegistrationBean<UserSessionFilter> registration = new FilterRegistrationBean<>();
        registration.addUrlPatterns("/*");
        registration.setFilter(new UserSessionFilter());
        return registration;
    }

    @Bean
    public UserSessionFilter userSessionFilter(){
        return new UserSessionFilter();
    }
}
