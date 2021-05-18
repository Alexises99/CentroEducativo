
public class UsuarioFull {
	private String dni;
	private String nombre;
	
	public UsuarioFull(String dni, String nombre) {
		this.dni = dni;
		this.nombre = nombre;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getDni() {
		return this.dni;
	}
}
