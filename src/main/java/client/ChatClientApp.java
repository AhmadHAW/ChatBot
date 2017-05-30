package client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ChatClientApp extends AsyncConfigurerSupport {

	public static void main(String[] args) {
		SpringApplication.run(ChatClientApp.class, args);

	}

}
