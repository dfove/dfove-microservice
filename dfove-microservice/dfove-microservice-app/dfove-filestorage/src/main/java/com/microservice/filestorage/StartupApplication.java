package com.microservice.filestorage;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = "com.microservice.filestorage.mapper")
public class StartupApplication {

	@Autowired
	private Microservices microservices;

	@Bean
	public SimpleClientHttpRequestFactory requestFactory() {
		int _timeout = microservices.getRestTimeout();
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(_timeout);
		factory.setReadTimeout(_timeout);
		return factory;
	}

	@Bean(name = "RestTemplate")
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(requestFactory());
		return restTemplate;
	}

	@Bean(name = "RestTemplateEureka")
	@LoadBalanced
	public RestTemplate restTemplateEureka() {
		RestTemplate restTemplate = new RestTemplate(requestFactory());
		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(StartupApplication.class, args);
	}
	
	/**
     * it's for set http url auto change to https
     */
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer(){
//        TomcatEmbeddedServletContainerFactory tomcat=new TomcatEmbeddedServletContainerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint=new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");//confidential
//                SecurityCollection collection=new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }

    @Bean
    public Connector httpConnector(){
        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(10101);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
	
	
}
