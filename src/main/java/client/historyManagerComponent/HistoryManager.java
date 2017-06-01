package client.historyManagerComponent;

import client.entities.*;
import client.roomManagerComponent.RoomService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryManager implements HistoryManagerInterface {

    @Autowired
    RoomService roomService;

    @Override
    public void addMessage(Message message) throws GivenObjectNotValidException, InterruptedException, RoomNotFoundException, NameNotValidException, UserNotExistException {
        if(!Message.isValidMessage(message)){
            throw new GivenObjectNotValidException("Die Message ist null oder nicht korrekt belegt");
        }
        if(message.isMessage()){
            doItWithMessage(message);
        }
        else{
            doItWithAcknowledge(message);
        }
    }

    private void doItWithAcknowledge(Message message) {
        //TODO
    }

    private void doItWithMessage(Message message) throws InterruptedException, RoomNotFoundException, NameNotValidException, UserNotExistException {
        Room room = roomService.getRoom(message.getRaumname());
        System.out.println("leseDieMessage.");
        Optional<User> opt = room.getUsers().stream().filter(t -> t.getUserName().equals(message.getSenderUserName())).findFirst();
		if(!opt.isPresent())
		{
			throw new UserNotExistException("Der User "+ message.getSenderUserName()+" hat unberechtigt versucht iene Nachricht in dem Raum zu senden.");
		}
        System.out.println(message.getRaumname()+" "+message.getSenderUserName()+": "+message.getMessage());
    }
}
