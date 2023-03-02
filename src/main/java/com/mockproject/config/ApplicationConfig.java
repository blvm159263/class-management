package com.mockproject.config;

import com.mockproject.dto.mapper.ClassScheduleDTOMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ClassScheduleDTOMapper classScheduleDTOMapper(){
        return new ClassScheduleDTOMapper();
    }
}
