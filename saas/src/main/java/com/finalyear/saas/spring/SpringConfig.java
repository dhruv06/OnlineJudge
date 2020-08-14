package com.finalyear.saas.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan("com.finalyear.saas")
@PropertySources({ //
		@PropertySource(value = "file:./saas.properties", ignoreResourceNotFound = true) //
})
public class SpringConfig {
	

}