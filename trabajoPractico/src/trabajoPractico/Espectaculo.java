package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;

public class Espectaculo {
	private String nombre;
	private HashMap<Fecha,Funcion> funciones;
	
	Espectaculo(String nombre){
		this.nombre =  nombre;
		this.funciones = new  HashMap<Fecha,Funcion>();
	}
}
