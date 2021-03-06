package com.jcsvall.app.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("learn/principal");
		registry.addViewController("/").setViewName("learn/principal");
		registry.addViewController("").setViewName("learn/principal");
		registry.addViewController("/login").setViewName("login");
	}

}
