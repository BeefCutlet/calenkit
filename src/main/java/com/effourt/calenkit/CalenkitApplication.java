package com.effourt.calenkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
		exclude = {
				ContextInstanceDataAutoConfiguration.class,
				ContextStackAutoConfiguration.class,
				ContextRegionProviderAutoConfiguration.class
		}
)
@EnableFeignClients(basePackages = {"com.effourt.calenkit.client"})
public class CalenkitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalenkitApplication.class, args);
	}

}
