package trabajoPractico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Usuario {
	private String email;
	private String nombre;
	private String apellido;
	private String contraseña;
	private HashMap<String, Entrada> entradasCompradas;
	
	Usuario(String email, String nombre, String apellido, String contraseña){
		this.email = email;
		this.nombre = nombre;
		this.apellido =  apellido;
		this.contraseña = contraseña;
		entradasCompradas = new HashMap<String, Entrada>();
	}
	public boolean autenticar(String contrasenia) {
		if(contrasenia == null) {
			return false;
		}
		return this.contraseña.equals(contrasenia);
	}
	public String getEmail() {
		return this.email;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getApellido() {
		return this.apellido;
	}

	public boolean tieneEntrada(IEntrada entrada) {
		if(entrada == null) {
			return false;
		}
		return entradasCompradas.containsKey(entrada.getCodigo());
	}
	public boolean eliminarEntrada(IEntrada entrada) {
		if(entrada == null || !tieneEntrada(entrada)) {
			return false;
		}
		entradasCompradas.remove(entrada.getCodigo());
		return true;
	}
	public boolean anularEntrada(IEntrada entrada) {
		return eliminarEntrada(entrada);
	}

	public String toString() {
		return nombre + " " + apellido + " " + email;
	}
	
}
