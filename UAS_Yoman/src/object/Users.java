package object;

public class Users {
	private String username, password;
	private boolean isAdmin;

	public Users(String username, String password, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public Users(Users usr) {
		this.username = usr.getUsername();
		this.password = usr.getPassword();
		this.isAdmin = usr.isAdmin();
	}

	public String getUsername() {
		return username;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public String getPassword() {
		return password;
	}
}
