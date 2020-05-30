package robotData;
public  class User {
	private String userName , email , password;

	public User(String name, String email2, String password2) {
		// TODO Auto-generated constructor stub
		this.userName = name ;
		this.email = email2 ;
		this.password = password2;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return  "		Données sur l'utilisateur \n" + "\nNom : " + userName + "\nemail :" + email + "\npassword" + password ; 
	}
}
