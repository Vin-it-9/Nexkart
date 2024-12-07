package org.nexus.nexkartbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NexkartBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexkartBackendApplication.class, args);

		System.out.println("NexkartBackendApplication started");
		System.out.println("http://localhost:8080/");

	}

}
