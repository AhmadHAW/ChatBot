package client.entities;

public class User {
	private String userName;
	private int port;
	private String ipAdress;

	public User(String userName, int port, String ipAdress) {
		this.userName = userName;
		this.port = port;
		this.ipAdress = ipAdress;
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

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		if (ipAdress != null)
			this.ipAdress = ipAdress;
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
