package client;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

import client.facade.TerminalController;

@SpringBootApplication
@EnableAsync
public class ChatClientApp extends AsyncConfigurerSupport {

	public static void main(String[] args) {
		SpringApplication.run(ChatClientApp.class, args);

	}

}
