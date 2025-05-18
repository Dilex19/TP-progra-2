package trabajoPractico;

import java.util.HashMap;

public class Usuario {
	private String email;
	private String nombre;
	private String apellido;
	private String contraseña;
	private HashMap<String, IEntrada> entradasCompradas;
	
	Usuario(String email, String nombre, String apellido, String contraseña){
		this.email = email;
		this.nombre = nombre;
		this.apellido =  apellido;
		this.contraseña = contraseña;
		entradasCompradas = new HashMap<String, IEntrada>();
	}
}
