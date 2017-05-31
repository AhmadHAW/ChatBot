package client.userInterfaceComponent;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import client.entities.*;
import client.roomManagerComponent.RoomServiceUserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.Property;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import client.GlobalConstantsAndValidation;

@Service
public class UserInterfaceImplementation implements UserInterface {

	RestTemplate rt = new RestTemplate();

	@Autowired
	UDPSenderInterface sender;

	@Autowired
	Environment environment;

	@Autowired
	RoomServiceUserInterface roomService;

	@Override
	public void sendMessage(String roomName, String message)
			throws GivenObjectNotValidException, RoomNotFoundException, NameNotValidException, IOException, InterruptedException {
		sender.sendMessage(roomName, message);
	}

	@Override
	public void treteRaumBei(String roomName) throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, InterruptedException {

			roomService.createRoom(roomName);


	}

	@Override
	public void verlasseRaum(String roomName) throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, InterruptedException {
			roomService.deleteRoom(roomName);
	}

	@Override
	public void erstelleRaum(String roomName) throws GivenObjectNotValidException, NameNotValidException {
		Room newRoom = new Room(roomName);
		HttpEntity<Room> request = new HttpEntity<>(newRoom);
		/**
		 * Setzt ein HTTPRequest an dem Server ab. Der Server sendet bei Erfolg
		 * ein Created HTTPStatuscode zurück. Wenn der Username bereits vergeben
		 * ist oder Felder nicht korrekt belegt sind, wird eine CONFLICT
		 * Statuscode zurück gegeben.
		 */
		ResponseEntity<User> response = rt.exchange(GlobalConstantsAndValidation.SERVER_USER_RESOURCES, HttpMethod.POST, request,
				User.class);
		if (!response.getStatusCode().equals(HttpStatus.CREATED)) {

			throw new GivenObjectNotValidException(
					"Der Raum konnte nicht angelegt werden, da der Name schon vergeben ist oder nicht der Namensregex: "
							+ GlobalConstantsAndValidation.NAME_REGEX + " entspricht.");

		}

	}

	@Override
	public void loggeAus() throws UserNotExistException {
		User thisUser = GlobalConstantsAndValidation.USER;
		if(thisUser==null){
			throw new UserNotExistException("Der User ist auf local Seiten nicht eingeloggt");
		}
		HttpEntity<String> request = new HttpEntity<>(thisUser.getUserName());
		/**
		 * Setzt ein HTTPRequest an dem Server ab. Der Server sendet bei Erfolg
		 * ein Accepted HTTPStatuscode zurück. Wenn der Username nicht gefunden wird,
		 * wird eine Not_Found Statuscode zurück gegeben.
		 */
		ResponseEntity<String> response = rt.exchange(GlobalConstantsAndValidation.SERVER_USER_RESOURCES+"/"+thisUser.getUserName(), HttpMethod.DELETE, request,
				String.class);
		if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {

			throw new UserNotExistException("Der User ist auf Serverseiten nicht eingeloggt");

		}

	}

	@Override
	public void loggeEin(String userName, int port, String ipAdress) throws GivenObjectNotValidException, IOException {
		HttpEntity<User> request = new HttpEntity<>(GlobalConstantsAndValidation.USER);
		/**
		 * Setzt ein HTTPRequest an dem Server ab. Der Server sendet bei Erfolg
		 * ein Created HTTPStatuscode zurück. Wenn der Username bereits vergeben
		 * ist oder Felder nicht korrekt belegt sind, wird eine CONFLICT
		 * Statuscode zurück gegeben.
		 */
		System.out.println((GlobalConstantsAndValidation.BASE_URL+GlobalConstantsAndValidation.SERVER_IP_ADRESS+GlobalConstantsAndValidation.SERVER_USER_RESOURCES));
		ResponseEntity<User> response = rt.exchange(GlobalConstantsAndValidation.BASE_URL+GlobalConstantsAndValidation.SERVER_IP_ADRESS+":"+GlobalConstantsAndValidation.SERVER_HTTP_PORT+GlobalConstantsAndValidation.SERVER_USER_RESOURCES, HttpMethod.POST, request,
				User.class);
		if (!response.getStatusCode().equals(HttpStatus.CREATED)) {

			throw new GivenObjectNotValidException(
					"Der User konnte nicht angelegt werden, da der Name schon vergeben ist oder die Ip-Adresse nicht erreichbar ist");

		}
		HttpEntity<Integer> request2 = new HttpEntity<Integer>(Integer.parseInt(environment.getProperty("local.server.port")));
		ResponseEntity<Integer> res= rt.exchange(GlobalConstantsAndValidation.BASE_URL+GlobalConstantsAndValidation.SERVER_IP_ADRESS+":"+GlobalConstantsAndValidation.SERVER_HTTP_PORT+GlobalConstantsAndValidation.SERVER_USER_RESOURCE+userName, HttpMethod.POST, request2,
				Integer.class);
		if(!res.getStatusCode().equals(HttpStatus.CREATED)){
			throw new GivenObjectNotValidException(
					"Der Port "+ environment.getProperty("local.server.port")+" ist nicht erlaubt.");


		}

	}

	@Override
	public void setSenderPort(int newPort) throws GivenObjectNotValidException, SocketException {
		if(newPort< GlobalConstantsAndValidation.PORT_MIN||newPort> GlobalConstantsAndValidation.PORT_MAX){
			throw new GivenObjectNotValidException("Der übergebene Port liegt nicht zwichen "+ GlobalConstantsAndValidation.PORT_MIN+" und "+ GlobalConstantsAndValidation.PORT_MAX+".");
		}
		sender.setSenderPort(newPort);
	}

	@Override
	public Set<String> getRooms() throws InterruptedException {
		Set<String> result = new HashSet<String>();
		for(Room room: roomService.getRooms()){
			result.add(room.getRoomName());
		}
		return result;
	}

}
