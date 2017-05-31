package client.terminalComponent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import client.GlobalConstantsAndValidation;
import client.userInterfaceComponent.UserInterface;

@Controller
public class TerminalController implements Runnable {

	private Scanner scanner = new Scanner(System.in);
	private static final String GETROOMSCOMMAND = "getRooms";
	private static final String CREATEROOMCOMMAND = "createRoom";
	private static final String JOINROOMCOMMAND = "joinRoom";
	private static final String LEAVEROOMCOMMAND = "leaveRoom";
	private static final String SENDMESSAGECOMMAND = "sendMessage";
	private static final String LOGOUTCOMMAND = "logOut";
	private static final String HELPCOMMAND = "help";
	private static final String COMMANDREGEX = "(" + GETROOMSCOMMAND + "|" + CREATEROOMCOMMAND + "|" + JOINROOMCOMMAND
			+ "|" + LEAVEROOMCOMMAND + "|" + LOGOUTCOMMAND + "|" + SENDMESSAGECOMMAND + "|" + HELPCOMMAND + ")( ("
			+ GlobalConstantsAndValidation.NAME_REGEX + ")( (.+))?)?";
	private static final Pattern COMMANDPATTERN = Pattern.compile(COMMANDREGEX);

	@Autowired
	UserInterface userInterface;

	@Override
	public void run() {
		System.out.println("\n\n");
		System.out.println("Willkommen im Chatbot");
		System.out.println("Melden Sie sich beim Server an:");
		meldeUserAn();
		System.out.println("Sie wurden mit dem User " + GlobalConstantsAndValidation.USER.getUserName() + " angemeldet");
		System.out.println("Sie können nun: ");
		printHelpCommands();
		String command;
		Matcher matcher;
		while (!Thread.currentThread().isInterrupted()) {
			command = scanner.next();
			matcher = COMMANDPATTERN.matcher(command);
			if (matcher.find()) {
				String argument1 = matcher.group(3);
				switch (matcher.group(1)) {
				case (GETROOMSCOMMAND):
					System.out.println("Eine Liste aller Räume:");
					try {
						System.out.println(userInterface.getRooms());
					} catch (InterruptedException e) {
						System.out.println("Irgendwas lief mit den Threads schief.");
						System.out.println(e.getMessage());
					}
					break;
				case (CREATEROOMCOMMAND):
					if (argument1 == null) {
						System.out.println("Der Befehl: " + CREATEROOMCOMMAND
								+ " benötigt als Argument einen gültigen Raumnamen. \nNutzen sie den Command: "
								+ HELPCOMMAND + " um gültige Befehle zu finden");
					} else {
						System.out.println("Ein Raum wird erstellt: ");
						try {
							userInterface.erstelleRaum(argument1);
							System.out.println("Der Raum: " + argument1 + " wurde erstellt.");
						} catch (NameNotValidException|GivenObjectNotValidException e) {
							System.out.println("Der Raum konnte nicht angelegt werden.");
							System.out.println(e.getMessage());
						}
					}
					break;

				case (JOINROOMCOMMAND):
					if (argument1 == null) {
						System.out.println("Der Befehl: " + JOINROOMCOMMAND
								+ " benötigt als Argument einen gültigen Raumnamen. \nNutzen sie den Command: "
								+ HELPCOMMAND + " um gültige Befehle zu finden");
					} else {
						System.out.println("Der User tritt dem Raum bei: ");
						try {
							userInterface.treteRaumBei(argument1);
							System.out.println("Der User ist dem Raum: " + argument1 + " beigetreten.");
						} catch (RoomNotFoundException e) {
							System.out.println("Der Raum konnte nicht gefunden werden.");
							System.out.println(e.getMessage());
						} catch (GivenObjectNotValidException e) {
							System.out.println("Der User wurde nicht korrekt angelegt. Starten sie die Application neu.");
							System.out.println(e.getMessage());
						} catch (InterruptedException e) {
							System.out.println("Irgendwas lief mit den Threads schief.");
							System.out.println(e.getMessage());
						} catch (NameNotValidException e) {
							System.out.println("Der User konnte dem Raum nicht beitreten.");
							System.out.println(e.getMessage());
						}
					}
					break;

				case (LEAVEROOMCOMMAND):
					if (argument1 == null) {
						System.out.println("Der Befehl: " + LEAVEROOMCOMMAND
								+ " benötigt als Argument einen gültigen Raumnamen. \nNutzen sie den Command: "
								+ HELPCOMMAND + " um gültige Befehle zu finden");
					} else {
						System.out.println("Der User verlässt den Raum");
						try {
							userInterface.verlasseRaum(argument1);
							System.out.println("Der User hat den Raum: " + argument1 + " verlassen.");
						} catch (RoomNotFoundException e) {
							System.out.println("Der Raum konnte nicht gefunden werden.");
							System.out.println(e.getMessage());
						} catch (GivenObjectNotValidException e) {
							System.out.println("übergebene Raumname "+ argument1+" ist ungültig.");
							System.out.println(e.getMessage());
						}
						catch (InterruptedException e) {
							System.out.println("Irgendwas lief mit den Threads schief.");
							System.out.println(e.getMessage());
						} catch (NameNotValidException e) {
							System.out.println("Der User konnte dem Raum nicht verlassen.");
							System.out.println(e.getMessage());
						}
					}
					break;

				case (SENDMESSAGECOMMAND):
					String message = matcher.group(5);
					if (argument1 == null) {
						System.out.println("Der Befehl: " + SENDMESSAGECOMMAND
								+ " benötigt als Argument einen gültigen Raumnamen. \nNutzen sie den Command: "
								+ HELPCOMMAND + " um gültige Befehle zu finden");
					} else if (message == null) {
						System.out.println("Der Befehl: " + SENDMESSAGECOMMAND
								+ " benötigt als Argument eine gültige Message. \nNutzen sie den Command: "
								+ HELPCOMMAND + " um gültige Befehle zu finden");
					} else {

						System.out.println("Die Message wird gesendet");
						try {
							userInterface.sendMessage(argument1, message);
							System.out.println("Die Message: " + message + " wurde an alle User in dem Raum "
									+ argument1 + " gesendet.");
						} catch (RoomNotFoundException e) {
							System.out.println("Der Raum konnte nicht gefunden werden.");
							System.out.println(e.getMessage());
						} catch (GivenObjectNotValidException e) {
							System.out.println("Die übergebene Message war nicht gültig, weil sie entweder kein Zeichen enthält oder größer als "+ GlobalConstantsAndValidation.MAXMESSAGESIZE+" byte ist.");
							System.out.println(e.getMessage());
						} catch (NameNotValidException e) {
							System.out.println("Der Raumname " + argument1 + " ist nicht gültig.");
							System.out.println(e.getMessage());
						} catch (IOException e) {
							System.out.println(e.getMessage());
						} catch (InterruptedException e) {
							System.out.println("Irgendwas lief mit den Threads schief.");
							System.out.println(e.getMessage());
						}
					}
					break;

				case (LOGOUTCOMMAND):

					System.out.println("Der User loogt sich aus");

					try {
						userInterface.loggeAus();
					System.out.println("Der User hat sich ausgeloggt");
					} catch (UserNotExistException e) {
						System.out.println(e.getMessage());
					}
					finally {
						Thread.currentThread().interrupt();
					}
					break;

				case (HELPCOMMAND):

					System.out.println("Gültige Befehle sind: ");
					printHelpCommands();
					break;
				}

			}
		}

	}

	private void printHelpCommands() {
		System.out.println("	-eine Liste aller Räume abrufen: " + GETROOMSCOMMAND);
		System.out.println("	-einen Raum erstellen: " + CREATEROOMCOMMAND + " <roomName>");
		System.out.println("	-einen Raum beitreten: " + JOINROOMCOMMAND + " <roomName>");
		System.out.println("	-einen Raum verlassen: " + LEAVEROOMCOMMAND + " <roomName>");
		System.out
				.println("	-eine Nachricht in einem Raum zu senden: " + SENDMESSAGECOMMAND + " <roomName> <message>");
		System.out.println("	-den User ausloggen: " + LOGOUTCOMMAND);
		System.out.println("	-alle erlaubten Befehle ausgeben: " + HELPCOMMAND);
	}

	private void meldeUserAn() {
		while (GlobalConstantsAndValidation.USER == null) {
			String userName = null;
			/**
			 * Solange der Username nicht korrekt eingegeben ist frage nach
			 * einem Usernamen
			 */
			while (userName == null) {
				System.out.println("Geben Sie ihren Usernamen ein: ");
				userName = scanner.next();
				if (userName != null) {
					if (!userName.matches(GlobalConstantsAndValidation.NAME_REGEX)) {
						System.out.println("Der Username: " + userName + " entspricht nicht der Regex: "
								+ GlobalConstantsAndValidation.NAME_REGEX);
						userName = null;
					}
				}
			}
			int reciefePort = -1;
			/**
			 * Solange kein gültiger reciefePort angegegben ist, frage nach einem Port
			 */
			while (reciefePort == -1) {
				System.out.println("Geben Sie einen Port an, auf dem sie über UDP erreichbar sind: ");
				reciefePort = scanner.nextInt();
				if(reciefePort< GlobalConstantsAndValidation.PORT_MIN||reciefePort> GlobalConstantsAndValidation.PORT_MAX){
					reciefePort = -1;
					System.out.println("Der Port "+reciefePort +" liegt nicht zwichen "+ GlobalConstantsAndValidation.PORT_MIN +" und "+ GlobalConstantsAndValidation.PORT_MAX+".");
				}

			}
			/**
			 * senderPort erstellen
			 */
			int senderPort = -1;
			while (senderPort == -1) {
				System.out.println("Geben Sie einen Port an, auf dem sie über UDP erreichbar sind: ");
				senderPort = scanner.nextInt();
				/**
				 * senderPort erstellen
				 */
				try {
					userInterface.setSenderPort(senderPort);
				} catch (GivenObjectNotValidException |SocketException e) {
					System.out.println(e.getMessage());
					senderPort=-1;
				}

			}

			/**
			 * Versuche die aktuelle IpAdresse herauszufinden. Wenn diese nicht verfügbar ist, lasse den User eine aktuelle Adresse eingeben.
			 */
			String ipAdresse = null;
			try {
				ipAdresse = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				while (ipAdresse == null) {
					System.out.println("Geben Sie ihre aktuelle IpAdresse an: ");
					ipAdresse = scanner.next();
				}
			}
			/**
			 * Versuche beim Server anzumelden.
			 */
			try {
				GlobalConstantsAndValidation.USER = new User(userName, reciefePort, ipAdresse);
				userInterface.loggeEin(userName, reciefePort, ipAdresse);

			} catch (NameNotValidException|GivenObjectNotValidException | UnknownHostException e) {
				System.out.println(e.getMessage());
				GlobalConstantsAndValidation.USER = null;
			}

		}
	}
}
