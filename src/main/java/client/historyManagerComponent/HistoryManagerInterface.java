package client.historyManagerComponent;

import client.entities.*;

public interface HistoryManagerInterface {

   public void addMessage(Message message) throws GivenObjectNotValidException, InterruptedException, RoomNotFoundException, NameNotValidException, UserNotExistException;
}
