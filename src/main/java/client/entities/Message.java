package client.entities;

import client.GlobalConstantsAndValidation;

import java.io.Serializable;

public class Message implements Serializable {

	private String Message;
	private String reciefeUserName;
	private String senderUserName;
	private String raumname;
	private long timeStamp;

	public Message(String message, String reciefeUserName, String senderUserName, String raumname, long timeStamp) throws GivenObjectNotValidException {

		super();
		if(!GlobalConstantsAndValidation.isMessageValid(message)||!GlobalConstantsAndValidation.isValidName(raumname)||!GlobalConstantsAndValidation.isValidName(senderUserName)||!GlobalConstantsAndValidation.isValidName(reciefeUserName)||!GlobalConstantsAndValidation.isValidTimeStamp(timeStamp)){
			throw new GivenObjectNotValidException("Die Felder der Message sind nicht vernünftig belegt.");
		}
		Message = message;
		this.reciefeUserName = reciefeUserName;
		this.senderUserName = senderUserName;
		this.raumname = raumname;
		this.timeStamp = timeStamp;
	}
	public Message(){

	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) throws GivenObjectNotValidException {
		if(!GlobalConstantsAndValidation.isMessageValid(message)){
			throw new GivenObjectNotValidException("Die Message ist entweder null oder es leer.");
		}
		Message = message;
	}

	public String getReciefeUserName() {
		return reciefeUserName;
	}

	public void setReciefeUserName(String recieferUserName) throws GivenObjectNotValidException {
		if(!GlobalConstantsAndValidation.isValidName(recieferUserName)){
			throw new GivenObjectNotValidException("Der recieferName "+recieferUserName+" ist nicht gültig.");
		}
		this.reciefeUserName = recieferUserName;
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) throws GivenObjectNotValidException {
		if(!GlobalConstantsAndValidation.isValidName(senderUserName)){
			throw new GivenObjectNotValidException("Der senderName "+senderUserName+" ist nicht gültig.");
		}
		this.senderUserName = senderUserName;
	}

	public String getRaumname() {
		return raumname;
	}

	public void setRaumname(String raumname) throws GivenObjectNotValidException {
		if(!GlobalConstantsAndValidation.isValidName(raumname)){
			throw new GivenObjectNotValidException("Der raumname "+raumname+" ist nicht gültig.");
		}
		this.raumname = raumname;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) throws GivenObjectNotValidException {
		if(timeStamp<0){
			throw new GivenObjectNotValidException("Der timeStamp darf nicht negativ sein.");
		}
		this.timeStamp = timeStamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((raumname == null) ? 0 : raumname.hashCode());
		result = prime * result + ((reciefeUserName == null) ? 0 : reciefeUserName.hashCode());
		result = prime * result + ((senderUserName == null) ? 0 : senderUserName.hashCode());
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (raumname == null) {
			if (other.raumname != null)
				return false;
		} else if (!raumname.equals(other.raumname))
			return false;
		if (reciefeUserName == null) {
			if (other.reciefeUserName != null)
				return false;
		} else if (!reciefeUserName.equals(other.reciefeUserName))
			return false;
		if (senderUserName == null) {
			if (other.senderUserName != null)
				return false;
		} else if (!senderUserName.equals(other.senderUserName))
			return false;
		if (timeStamp != other.timeStamp)
			return false;
		return true;
	}


}
