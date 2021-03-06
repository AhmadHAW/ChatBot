package client.entities;

import client.GlobalConstantsAndValidation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User implements Serializable {
	private String userName;
	private int port;

	@Override
	public String toString() {
		return 	"userName='" + userName + '\'' +
				"\nport=" + port +
				"\nipAdress=" + ipAdress.getHostAddress();
	}

	private InetAddress ipAdress;

	public User(String userName, int port, String ipAdress) throws UnknownHostException, NameNotValidException, GivenObjectNotValidException {
		if(!GlobalConstantsAndValidation.isValidName(userName)){
			throw new NameNotValidException("Der Username "+userName+" ist nicht gültig");
		}
		if(!GlobalConstantsAndValidation.isValidPort(port)){
			throw new GivenObjectNotValidException("Der Port "+port+" muss zwichen "+GlobalConstantsAndValidation.PORT_MIN+" und "+GlobalConstantsAndValidation.PORT_MAX+" liegen.");
		}
		if(!GlobalConstantsAndValidation.isValidIpAdress(ipAdress)){
			throw new NameNotValidException("Die IpAdresse "+ipAdress+" ist nicht gültig.");
		}
		this.userName = userName;
		this.port = port;
		this.ipAdress = InetAddress.getByName(ipAdress);
	}

	public User(){

	}

	public void setIpAdress(InetAddress ipAdress) {
		this.ipAdress = ipAdress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) throws NameNotValidException {
		if(!GlobalConstantsAndValidation.isValidName(userName)){
			throw new NameNotValidException("Der Username "+userName+" ist nicht gültig");
		}
			this.userName = userName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) throws GivenObjectNotValidException {
		if(!GlobalConstantsAndValidation.isValidPort(port)){
			throw new GivenObjectNotValidException("Der Port "+port+" muss zwichen "+GlobalConstantsAndValidation.PORT_MIN+" und "+GlobalConstantsAndValidation.PORT_MAX+" liegen.");
		}this.port = port;
	}

	public InetAddress getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) throws UnknownHostException, NameNotValidException {
		if(!GlobalConstantsAndValidation.isValidIpAdress(ipAdress)){
			throw new NameNotValidException("Die IpAdresse "+ipAdress+" ist nicht gültig.");
		}
			this.ipAdress = InetAddress.getByName(ipAdress);
	}

	@JsonIgnore
	public boolean isCorrect() {
		return GlobalConstantsAndValidation.isValidName(userName)&&GlobalConstantsAndValidation.isValidPort(port);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}


}
