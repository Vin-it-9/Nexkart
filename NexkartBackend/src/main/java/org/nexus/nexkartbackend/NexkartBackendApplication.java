package org.nexus.nexkartbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.nexus.nexkartbackend.Repository")
public class NexkartBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(NexkartBackendApplication.class, args);

		System.out.println("NexkartBackendApplication started");
		System.out.println("http://localhost:8080/NexkartAdmin");

	}

}
