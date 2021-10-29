package com.microservice.websocket.config;

import com.microservice.core.LogComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jimmy on 2020/2/24.
 */
@Configuration
public class BeanConfig {

    @Bean
    public LogComponent logComponent() {
        return new LogComponent();
    }

}
