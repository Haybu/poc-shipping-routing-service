package io.agilehandy.qry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PocBookingServiceQryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocBookingServiceQryApplication.class, args);
	}

}
