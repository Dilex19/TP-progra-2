package trabajoPractico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDate;

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
	public void agregarEntrada(IEntrada entrada) {
		if(entrada != null) {
			entradasCompradas.put(entrada.getCodigo(), entrada);
		}
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
	public LinkedList<IEntrada> listarEntradas() {
		LinkedList<IEntrada> entradasUsuario = new LinkedList<IEntrada>();
		for(IEntrada entrada : entradasCompradas.values()) {
			entradasUsuario.add(entrada);
		}
		return entradasUsuario;
	}
	public List<IEntrada> listarEntradasFuturas() {
		LinkedList<IEntrada> entradasFuturasUsuario = new LinkedList<IEntrada>();
		for(IEntrada entrada : entradasCompradas.values()) {
			LocalDate fechaActual = LocalDate.now();
			if(entrada.obtenerFecha().isAfter(fechaActual)) {
				entradasFuturasUsuario.add(entrada);
			}
		}
		return entradasFuturasUsuario;
	}
	
}
