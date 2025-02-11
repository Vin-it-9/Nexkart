package org.nexus.nexkartfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NexkartFrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexkartFrontendApplication.class, args);

        System.out.println("NexkartBackendApplication started");
        System.out.println("http://localhost:8081/");


    }

}
