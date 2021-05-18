
public class User {
		private String dni;
		private String password;
		
		public User(String dni, String password) {
			this.dni = dni;
			this.password = password;
		}
		public void setDni(String dni) {
			this.dni = dni;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPassword() {
			return this.password;
		}
		public String getDni() {
			return this.dni;
		}
	}

