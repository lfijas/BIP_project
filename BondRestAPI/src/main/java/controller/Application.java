package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@ComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

	private static Class<Application> entryPointClass = Application.class;
	
    public static void main(String[] args) {
    	
        SpringApplication.run(Application.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(entryPointClass);
    }

}