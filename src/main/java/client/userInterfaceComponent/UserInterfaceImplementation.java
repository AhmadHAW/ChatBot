package client.userInterfaceComponent;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

import client.entities.*;
import client.roomManager.RoomServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import client.GlobalVariables;
import client.senderComponent.UDPSenderInterface;

@Service
public class UserInterfaceImplementation implements UserInterface {

	RestTemplate rt = new RestTemplate();

	@Autowired
	UDPSenderInterface sender;

	@Autowired
	RoomServiceInterface roomService;

	@Override
	public void sendMessage(String roomName, String message)
			throws GivenObjectNotValidException, RoomNotFoundException, NameNotValidException, IOException {
		sender.sendMessage(roomName, message);
	}

	@Override
	public void treteRaumBei(String roomName) throws RoomNotFoundException, GivenObjectNotValidException {

			roomService.createRoom(roomName);


	}

	@Override
	public void verlasseRaum(String roomName) throws RoomNotFoundException, GivenObjectNotValidException {
			roomService.deleteRoom(roomName);
	}

	@Override
	public void erstelleRaum(String roomName) throws GivenObjectNotValidException {
		Room newRoom = new Room(roomName);
		HttpEntity<Room> request = new HttpEntity<>(newRoom);
		/**
		 * Setzt ein HTTPRequest an dem Server ab. Der Server sendet bei Erfolg
		 * ein Created HTTPStatuscode zurück. Wenn der Username bereits vergeben
		 * ist oder Felder nicht korrekt belegt sind, wird eine CONFLICT
		 * Statuscode zurück gegeben.
		 */
		ResponseEntity<User> response = rt.exchange(GlobalVariables.SERVER_USER_RESOURCES, HttpMethod.POST, request,
				User.class);
		if (!response.getStatusCode().equals(HttpStatus.CREATED)) {

			throw new GivenObjectNotValidException(
					"Der Raum konnte nicht angelegt werden, da der Name schon vergeben ist oder nicht der Namensregex: "
							+ GlobalVariables.NAME_REGEX + " entspricht.");

		}

	}

	@Override
	public void loggeAus() throws UserNotExistException {
		User thisUser = GlobalVariables.USER;
		if(thisUser==null){
			throw new UserNotExistException("Der User ist auf local Seiten nicht eingeloggt");
		}
		HttpEntity<String> request = new HttpEntity<>(thisUser.getUserName());
		/**
		 * Setzt ein HTTPRequest an dem Server ab. Der Server sendet bei Erfolg
		 * ein Accepted HTTPStatuscode zurück. Wenn der Username nicht gefunden wird,
		 * wird eine Not_Found Statuscode zurück gegeben.
		 */
		ResponseEntity<String> response = rt.exchange(GlobalVariables.SERVER_USER_RESOURCES+"/"+thisUser.getUserName(), HttpMethod.DELETE, request,
				String.class);
		if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {

			throw new UserNotExistException("Der User ist auf Serverseiten nicht eingeloggt");

		}

	}

	@Override
	public void loggeEin(String userName, int port, String ipAdress) throws GivenObjectNotValidException {
		HttpEntity<User> request = new HttpEntity<>(GlobalVariables.USER);
		/**
		 * Setzt ein HTTPRequest an dem Server ab. Der Server sendet bei Erfolg
		 * ein Created HTTPStatuscode zurück. Wenn der Username bereits vergeben
		 * ist oder Felder nicht korrekt belegt sind, wird eine CONFLICT
		 * Statuscode zurück gegeben.
		 */
		ResponseEntity<User> response = rt.exchange(GlobalVariables.SERVER_USER_RESOURCES, HttpMethod.POST, request,
				User.class);
		if (!response.getStatusCode().equals(HttpStatus.CREATED)) {

			throw new GivenObjectNotValidException(
					"Der User konnte nicht angelegt werden, da der Name schon vergeben ist oder die Ip-Adresse nicht erreichbar ist");

		}

	}

	@Override
	public void setSenderPort(int newPort) throws GivenObjectNotValidException, SocketException {
		if(newPort<GlobalVariables.PORT_MIN||newPort>GlobalVariables.PORT_MAX){
			throw new GivenObjectNotValidException("Der übergebene Port liegt nicht zwichen "+GlobalVariables.PORT_MIN+" und "+GlobalVariables.PORT_MAX+".");
		}
		sender.setSenderPort(newPort);
	}

	@Override
	public Set<String> getRooms() {
		Set<String> result = new HashSet<String>();
		for(Room room: roomService.getRooms()){
			result.add(room.getRoomName());
		}
		return result;
	}

}
