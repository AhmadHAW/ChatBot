package client.facade;

import java.util.Scanner;

import org.springframework.stereotype.Controller;

@Controller
public class TerminalController implements Runnable {

	
	@Override
	public void run() {
		System.out.println("\n\n");
		System.out.println("Willkommen im Chatbot");
		System.out.println("Melden Sie sich beim Server an:");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Geben Sie ihren Usernamen ein: ");
		String userName = scanner.next();
		System.out.println("Geben Sie einen Port an, auf dem sie über UDP erreichbar sind: ");
		int port = scanner.nextInt();
		System.out.println("Geben Sie ihre aktuelle IpAdresse an: ");
		String ipAdresse = scanner.next();

	}
}
