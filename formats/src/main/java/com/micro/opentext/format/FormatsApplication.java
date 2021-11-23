package com.micro.opentext.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
public class FormatsApplication {
	private static final Logger LOG = Logger.getLogger(FormatsApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(FormatsApplication.class, args);
		LOG.log(Level.INFO, "Index API is calling");
	}

	// Documentation by using swagger
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.micropay.users.controller")).build();
	}

	
	

}


@RestController
class MessageRestController {
	@Autowired
	private RestTemplate restTemplate1;
	private static final Logger LOG = Logger.getLogger(MessageRestController.class.getName());

	@Value("${msg:Hello world - Config Server is not working..pelase check}")
	private String msg;
	@RefreshScope
	@RequestMapping("/msg")
	String getMsg() {
		LOG.log(Level.INFO, "getMsg request calling");
		LOG.log(Level.INFO, "Completed request"); 
		return this.msg;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping("/") 
	public String home() { 
	  LOG.log(Level.INFO, "you called home"); 
	  
	  return "Hello World"; 
	}
	@RequestMapping("/home")
	public String callHome() {
		LOG.log(Level.INFO, "calling msg");
		return restTemplate1.getForObject("http://localhost:8091/msg", String.class);
	}
}
