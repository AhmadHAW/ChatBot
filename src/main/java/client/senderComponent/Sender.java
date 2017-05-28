package client.senderComponent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.net.ssl.SSLSocket;

import client.GlobalVariables;
import client.entities.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import client.roomManager.RoomServiceInterface;

@Service
public class Sender implements UDPSenderInterface {

	@Autowired
	RoomServiceInterface roomServiceInterface;

	private DatagramSocket ds;
	private int senderPort;
	private ObjectMapper objMapper = new ObjectMapper();

	public Sender() throws SocketException {
		ds = new DatagramSocket();
	}

	@Override
	public void sendMessage(String roomName, String message) throws NameNotValidException, RoomNotFoundException, GivenObjectNotValidException, IOException {
		Room room = roomServiceInterface.getRoom(roomName);
		for (User user : room.getUsers()) {
			String reciever = user.getUserName();
			InetAddress receiveIpAdress = user.getIpAdress();
			int destPort = user.getPort();
			Message messageObject = new Message(message,reciever, GlobalVariables.USER.getUserName(), roomName, System.currentTimeMillis());
			try {
				String messageAsJson = objMapper.writeValueAsString(messageObject);
				byte[] data = messageAsJson.getBytes();
				if(data.length>GlobalVariables.MAXMESSAGESIZE){
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
