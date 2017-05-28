package client.entities;

import client.GlobalVariables;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User implements Serializable {
	private String userName;
	private int port;
	private InetAddress ipAdress;

	public User(String userName, int port, String ipAdress) throws UnknownHostException {
		this.userName = userName;
		this.port = port;
		this.ipAdress = InetAddress.getByName(ipAdress);
	}

	public User() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		if (userName != null)
			this.userName = userName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public InetAddress getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) throws UnknownHostException {
		if (ipAdress != null)
			this.ipAdress = InetAddress.getByName(ipAdress);
	}

	public boolean isCorrect() {
		return userName != null && userName.matches(GlobalVariables.NAME_REGEX) && port >= GlobalVariables.PORT_MIN
				&& port <= GlobalVariables.PORT_MAX && ipAdress != null;
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

	public boolean isValid() {
		return userName.matches(GlobalVariables.NAME_REGEX) && port >= GlobalVariables.PORT_MIN
				&& port <= GlobalVariables.PORT_MAX && ipAdress != null;
	}

}
