package client.userInterfaceComponent;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import client.entities.*;
import client.roomManagerComponent.RoomServiceUserInterface;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.Property;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import client.GlobalConstantsAndValidation;

import javax.xml.ws.Response;

@Service
public class UserInterfaceImplementation implements UserInterface {

	RestTemplate rt = new RestTemplate();

	@Autowired
	UDPSenderInterface sender;

	@Autowired
	Environment environment;

	@Autowired
	RoomServiceUserInterface roomService;

	@Autowired
			StreamListenerUserInterface streamListener;

    ObjectMapper mapper = new ObjectMapper();

	@Override
	public void sendMessage(String roomName, String message)
			throws GivenObjectNotValidException, RoomNotFoundException, NameNotValidException, IOException, InterruptedException {
		sender.sendMessage(roomName, message);
	}

	@Override
	public void treteRaumBei(String roomName) throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, InterruptedException, IOException {
		HttpEntity<User> request = new HttpEntity<>(GlobalConstantsAndValidation.USER);
		String url = GlobalConstantsAndValidation.BASE_URL+GlobalConstantsAndValidation.SERVER_IP_ADRESS+":"+GlobalConstantsAndValidation.SERVER_HTTP_PORT+GlobalConstantsAndValidation.SERVER_ROOM_RESOURCES+"/"+roomName;
		try{
			User[] users = rt.postForObject(url, request, User[].class);
			Room room = roomService.createRoom(roomName);
			for(User user: users){
				System.out.println("Hier wir der User "+user +" Der Userliste hinzugefügt.");
					room.addUser(user);
			}
		}catch (HttpClientErrorException e) {
			System.out.println("SHIT");
			throw new GivenObjectNotValidException(e.getStatusCode() + ": " + e.getResponseBodyAsString());
		}

	}

	@Override
	public void verlasseRaum(String roomName) throws RoomNotFoundException, GivenObjectNotValidException, NameNotValidException, InterruptedException {
			roomService.deleteRoom(roomName);
	}

	@Override
	public void erstelleRaum(String roomName) throws GivenObjectNotValidException, NameNotValidException, InterruptedException {
		Room newRoom = new Room(roomName);
		HttpEntity<Room> request = new HttpEntity<>(newRoom);
		/**
		 * Setzt ein HTTPRequest an dem Server ab. Der Server sendet bei Erfolg
		 * ein Created HTTPStatuscode zurück. Wenn der Username bereits vergeben
		 * ist oder Felder nicht korrekt belegt sind, wird eine CONFLICT
		 * Statuscode zurück gegeben.
		 */
		String url = GlobalConstantsAndValidation.BASE_URL+GlobalConstantsAndValidation.SERVER_IP_ADRESS+":"+GlobalConstantsAndValidation.SERVER_HTTP_PORT+GlobalConstantsAndValidation.SERVER_ROOM_RESOURCES;
		try {ResponseEntity<?> response = rt.exchange(url, HttpMethod.POST, request,Room.class);}
		catch (HttpClientErrorException e) {
			throw new GivenObjectNotValidException(e.getStatusCode()+": "+ e.getResponseBodyAsString());
		}
	}

	@Override
	public void loggeAus() throws UserNotExistException {
		try {
			User thisUser = GlobalConstantsAndValidation.USER;
			if (thisUser == null) {
				throw new UserNotExistException("Der User ist auf local Seiten nicht eingeloggt");
			}
			HttpEntity<String> request = new HttpEntity<>(thisUser.getUserName());
			/**
			 * Setzt ein HTTPRequest an dem Server ab. Der Server sendet bei Erfolg
			 * ein Accepted HTTPStatuscode zurück. Wenn der Username nicht gefunden wird,
			 * wird eine Not_Found Statuscode zurück gegeben.
			 */
			try{
			ResponseEntity<String> response = rt.exchange(GlobalConstantsAndValidation.BASE_URL + GlobalConstantsAndValidation.SERVER_IP_ADRESS + ":" + GlobalConstantsAndValidation.SERVER_HTTP_PORT + GlobalConstantsAndValidation.SERVER_USER_RESOURCES + "/" + thisUser.getUserName(), HttpMethod.DELETE, request,
					String.class);}
			catch (HttpClientErrorException e) {
				throw new UserNotExistException(e.getStatusCode()+": "+ e.getResponseBodyAsString());
			}

		}finally {
			streamListener.stopIt();
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
		try {
			ResponseEntity<?> response = rt.exchange(GlobalConstantsAndValidation.BASE_URL + GlobalConstantsAndValidation.SERVER_IP_ADRESS + ":" + GlobalConstantsAndValidation.SERVER_HTTP_PORT + GlobalConstantsAndValidation.SERVER_USER_RESOURCES, HttpMethod.POST,request, String.class);
			response= rt.postForEntity(GlobalConstantsAndValidation.BASE_URL+GlobalConstantsAndValidation.SERVER_IP_ADRESS+":"+GlobalConstantsAndValidation.SERVER_HTTP_PORT+GlobalConstantsAndValidation.SERVER_USER_RESOURCE+userName,Integer.parseInt(environment.getProperty("local.server.port")), String.class);
		}
		catch (HttpClientErrorException e) {
			throw new GivenObjectNotValidException(e.getStatusCode()+": "+ e.getResponseBodyAsString());
		}
		System.out.println("Der Port "+environment.getProperty("local.server.port"));
		HttpEntity<String> request2 = new HttpEntity<>(environment.getProperty("local.server.port"));
		String url = GlobalConstantsAndValidation.BASE_URL + GlobalConstantsAndValidation.SERVER_IP_ADRESS + ":" + GlobalConstantsAndValidation.SERVER_HTTP_PORT + GlobalConstantsAndValidation.SERVER_USER_RESOURCE+userName;
		try {
			rt.postForObject(url, request2, String.class);
		}
		catch (HttpClientErrorException e) {
			System.out.println("sda");
			throw new GivenObjectNotValidException(e.getStatusCode()+": "+ e.getResponseBodyAsString());
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
    public Set<String> getRoomsServer() throws Exception {
		try {
			ResponseEntity<?> response = rt.getForEntity(GlobalConstantsAndValidation.BASE_URL + GlobalConstantsAndValidation.SERVER_IP_ADRESS + ":" + GlobalConstantsAndValidation.SERVER_HTTP_PORT + GlobalConstantsAndValidation.SERVER_ROOM_RESOURCES, Set.class);
			return (Set<String>) response.getBody();}
		catch (HttpClientErrorException e) {
			throw new Exception(e.getStatusCode()+": "+ e.getResponseBodyAsString());
		}

    }

    @Override
	public Set<String> getRoomsLocal() throws InterruptedException {
		Set<String> result = new HashSet<String>();
		for(Room room: roomService.getRooms()){
			result.add(room.getRoomName());
		}
		return result;
	}

}
