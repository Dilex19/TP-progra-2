package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;

public class Funcion {
	private Sede sede;
	private LocalDate fecha;
	private double precioBase;
	private HashMap<String, boolean[]> asientos;
	private HashMap<String,Entrada> entradasVendidas;
	
	Funcion(Sede sede, String fecha, double precioBase){
		this.sede = sede;
		
		this.precioBase = precioBase;
		this.asientos = new HashMap<String, boolean[]>();
		this.entradasVendidas = new HashMap<String, Entrada>();
		
		
	}
}
