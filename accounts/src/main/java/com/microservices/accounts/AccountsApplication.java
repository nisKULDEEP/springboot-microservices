package com.microservices.accounts;

import com.microservices.accounts.dtos.AccountContactInfo;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
//@ComponentScans({ @ComponentScan("com.microservices.accounts.controllers") })
//@EnableJpaRepositories("com.microservices.accounts.repositories")
@EnableConfigurationProperties(value= {AccountContactInfo.class})
@EntityScan("com.microservices.accounts.models")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "Bank Accounts microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Kuldeep Singh",
						email = "kuldeepnishad.singh@gmail.com",
						url = "https://www.localhost:8080.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.localhost:8080.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Accounts microservice REST API Documentation",
				url = "https://www.localhost:8080.com/swagger-ui.html"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
