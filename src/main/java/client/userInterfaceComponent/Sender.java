package client.userInterfaceComponent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import client.GlobalConstantsAndValidation;
import client.entities.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import client.roomManagerComponent.RoomServiceUserInterface;

@Service
public class Sender implements UDPSenderInterface {

	@Autowired
	RoomServiceUserInterface roomServiceUserInterface;

	private DatagramSocket ds;
	private int senderPort;
	private ObjectMapper objMapper = new ObjectMapper();

	public Sender() throws SocketException {
		ds = new DatagramSocket();
	}

	@Override
	public void sendMessage(String roomName, String message) throws NameNotValidException, RoomNotFoundException, GivenObjectNotValidException, IOException, InterruptedException {
		Room room = roomServiceUserInterface.getRoom(roomName);
		for (User user : room.getUsers()) {
			String reciever = user.getUserName();
			InetAddress receiveIpAdress = user.getIpAdress();
			int destPort = user.getPort();
			Message messageObject = new Message(message,reciever, GlobalConstantsAndValidation.USER.getUserName(), roomName, System.currentTimeMillis());
			try {
				String messageAsJson = objMapper.writeValueAsString(messageObject);
				byte[] data = messageAsJson.getBytes();
				if(data.length> GlobalConstantsAndValidation.MAXMESSAGESIZE){
					throw new GivenObjectNotValidException("Die Message ist zu lang.");
				}

				DatagramPacket packet = new DatagramPacket(data, data.length, receiveIpAdress, destPort);
				ds.send(packet);
			} catch (JsonProcessingException e) {
				//TODO
			}

		}
	}

	public void setSenderPort(int newPort) throws SocketException {
		senderPort = newPort;
		ds = new DatagramSocket(senderPort);
	}

}
