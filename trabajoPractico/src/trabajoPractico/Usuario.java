package trabajoPractico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class Usuario {
	private String email;
	private String nombre;
	private String apellido;
	private String contraseña;
	private Map<String, Entrada> entradasCompradas;
	
	Usuario(String email, String nombre, String apellido, String contrasenia){
		if (email == null) {
			throw new RuntimeException("Error: El email no puede estar vacío");
		}
		if(nombre == null) {
	        throw new RuntimeException("Error: El nombre no puede estar vacío");
	    }
		if(apellido == null) {
		    throw new RuntimeException("Error: El apellido no puede estar vacío");
	    }
		if(contrasenia == null || contrasenia.length() < 3) {
	        throw new RuntimeException("Error: La contraseña no puede estar vacía o tener menos de 3 caracteres.");
	    }
		if(!email.contains("@") || !email.contains(".")) {
		    throw new RuntimeException("Error: El formato del email no es válido");
		}
		this.email = email;
		this.nombre = nombre;
		this.apellido =  apellido;
		this.contraseña = contrasenia;
		entradasCompradas = new HashMap<String, Entrada>();
	}
	
	//Verifica si la contraseña ingresada coincide con la del usuario.
	public boolean autenticar(String contrasenia) {
		if(contrasenia == null) {
			return false;
		}
		return this.contraseña.equals(contrasenia); 
	}
	
	//Añade una entrada al mapa de entradas compradas.
	public void agregarEntrada(Entrada entrada) {
		if(entrada != null) {
			entradasCompradas.put(entrada.getCodigo(), entrada);
		}
	}

	//Verifica si el usuario posee una entrada específica.
	public boolean tieneEntrada(Entrada entrada) {
		if(entrada == null) {
			return false;
		}
		return entradasCompradas.containsKey(entrada.getCodigo());
	}
	
	//Elimina una entrada del usuario.
	public boolean eliminarEntrada(Entrada entrada) {
		if(entrada == null || !tieneEntrada(entrada)) {
			return false;
		}
		entradasCompradas.remove(entrada.getCodigo());
		return true;
	}
	
	//Alias de eliminarEntrada(), misma funcionalidad.
	public boolean anularEntrada(Entrada entrada) {
		return eliminarEntrada(entrada);
	}

	//Formato: "<nombre> - <apellido> - <email>."
	public String toString() {
		return String.format("%s - %s - %s", nombre,apellido,email);
	}
	
	//Devuelve todas las entradas del usuario en una lista.
	public LinkedList<IEntrada> listarEntradas() {
		LinkedList<IEntrada> entradasUsuario = new LinkedList<IEntrada>();
		for(IEntrada entrada : entradasCompradas.values()) {
			entradasUsuario.add(entrada);
		}
		return entradasUsuario;
	}
	
	//Filtra entradas con fechas posteriores al día actual.
	public List<IEntrada> listarEntradasFuturas() {
		LinkedList<IEntrada> entradasFuturasUsuario = new LinkedList<IEntrada>();
		for(Entrada entrada : entradasCompradas.values()) {
			LocalDate fechaActual = LocalDate.now();
			if(entrada.obtenerFecha().esDespues(fechaActual)) {
				entradasFuturasUsuario.add(entrada);
			}
		}
		return entradasFuturasUsuario;
	}
	public boolean puedeAnularEntrada(Entrada entrada) {
		if(entrada == null) {
			return false;
		}
		
		// Verificar que la entrada pertenezca a este usuario
		if(!tieneEntrada(entrada)) {
			return false;
		}
		
		LocalDate fechaActual = LocalDate.now();
		if(!entrada.obtenerFecha().esDespues(fechaActual)) {
			return false; 
		}
		
	return true;
	}
	
	
}
