package client.userInterfaceComponent;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import client.GlobalConstantsAndValidation;
import client.entities.*;
import client.historyManagerComponent.HistoryManagerInterface;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import client.timeOutComponent.TimeOutComponentInterface;

@Controller
public class StreamListener implements StreamListenerUserInterface, CommandLineRunner {
	@Autowired
	TimeOutComponentInterface toci;

	@Autowired
	HistoryManagerInterface historyManager;

	private DatagramSocket socket;
	StreamListener() {

	}

	@Async
	public void run(String[] args){
		muh(args);

	}

	private void muh(String[] args) {
		try {

			ObjectMapper mapper = new ObjectMapper();
			while(GlobalConstantsAndValidation.USER==null){
				Thread.sleep(20);
			}
			socket = new DatagramSocket(GlobalConstantsAndValidation.USER.getPort());
			while(!Thread.currentThread().isInterrupted()){
				DatagramPacket packet = new DatagramPacket( new byte[512], 512 );
				socket.receive( packet );
				InetAddress address = packet.getAddress();
				int         port    = packet.getPort();
				int         len     = packet.getLength();
				byte[]      data    = packet.getData();
				String jsonMessage = new String(data);
				Message message = mapper.readValue(jsonMessage, Message.class);
				historyManager.addMessage(message);
			}
		} catch (NameNotValidException e) {
			System.out.println("\n\n"+1+"Hier\n\n");
		} catch (UserNotExistException e) {
			System.out.println("\n\n"+2+"Hier\n\n");		}
			catch (JsonParseException e) {
				System.out.println("\n\n"+3+"Hier\n\n");
		} catch (JsonMappingException e) {
			System.out.println("\n\n"+4+"Hier\n\n");
		} catch (SocketException e) {
			System.out.println("\n\n"+5+"Hier\n\n");
		} catch (IOException e) {
			System.out.println("\n\n"+6+"Hier\n\n");
		} catch (GivenObjectNotValidException e) {
			System.out.println("\n\n"+7+"Hier\n\n");
		} catch (RoomNotFoundException e) {
			System.out.println("\n\n"+8+"Hier\n\n");
		} catch (InterruptedException e) {
			System.out.println("\n\n"+9+"Hier\n\n");
		}
	}

	public void stopIt() {
		Thread.currentThread().interrupt();
	}


}
