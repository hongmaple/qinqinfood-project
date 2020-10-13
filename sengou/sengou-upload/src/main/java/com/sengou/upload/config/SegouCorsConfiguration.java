package com.sengou.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域请求
 */
@Configuration
public class SegouCorsConfiguration {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        //1)允许请求的域，不要写*，否则cookie就无法使用了
        config.addAllowedOrigin("http://manage.leyou.com");
        //2) 允许的请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("POST");
        //3)允许的头信息
        config.addAllowedHeader("*");
        //2添加映射路径我们拦截一切请求
        CorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        ((UrlBasedCorsConfigurationSource) configurationSource).registerCorsConfiguration("/**",config);
        //返回新的CorsFilter
        return new CorsFilter(configurationSource);
    }
}
