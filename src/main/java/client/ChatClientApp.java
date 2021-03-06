package client;

import client.terminalComponent.TerminalController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class ChatClientApp extends AsyncConfigurerSupport {
	private static ConfigurableApplicationContext ap;

	public static void main(String[] args) {
		ap = SpringApplication.run(ChatClientApp.class, args);
	}


	public static void closeApplication(){
		ap.close();
		Runtime.getRuntime().exit(0);
	}
}
