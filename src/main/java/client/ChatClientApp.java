package client;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import client.clientcomponent.ClientService;
import client.facade.TerminalController;

@SpringBootApplication
public class ChatClientApp {

	public static void main(String[] args) {
		SpringApplication.run(ChatClientApp.class, args);

	}

}
