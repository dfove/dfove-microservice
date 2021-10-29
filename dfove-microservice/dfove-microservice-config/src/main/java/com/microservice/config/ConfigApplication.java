package com.microservice.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableConfigServer
@SpringBootApplication
@EnableEurekaClient
public class ConfigApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ConfigApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}