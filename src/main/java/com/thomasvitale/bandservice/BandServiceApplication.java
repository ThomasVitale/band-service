package com.thomasvitale.bandservice;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@SpringBootApplication
public class BandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BandServiceApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions.route()
			.GET("/", request -> ServerResponse.ok().body("Welcome to the band service!"))
			.GET("/instruments", request -> ServerResponse.ok().body(List.of(
				"piano", "guitar", "drums", "violin", "bass", "trumpet"
			)))
			.build();
	}

}
