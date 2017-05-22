package client.timeOutComponent;

import client.entities.Message;

public interface TimeOutComponentInterface {

	public void startTimeOut(Message message);

	public void stopTimeOut(Message message);
}
