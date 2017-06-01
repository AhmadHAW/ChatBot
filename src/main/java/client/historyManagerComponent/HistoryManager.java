package client.historyManagerComponent;

import client.entities.*;
import client.roomManagerComponent.RoomService;
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
        if(!room.getUsers().contains(message.getSenderUserName())){
            throw new UserNotExistException("Der User "+ message.getSenderUserName()+" hat unerlaubt versucht eine Nachricht an dich zu senden.");
        }
        System.out.println(message.getRaumname()+" "+message.getSenderUserName()+": "+message.getMessage());
    }
}
