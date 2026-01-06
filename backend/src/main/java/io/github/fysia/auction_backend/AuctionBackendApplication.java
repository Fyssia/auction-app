package io.github.fysia.auction_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class AuctionBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionBackendApplication.class, args);
	}

}
