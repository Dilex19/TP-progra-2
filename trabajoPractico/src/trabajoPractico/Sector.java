package trabajoPractico;

public class Sector {
	private String nombre;
	private int incremento;
	private	int capacidad;
	private int cantidadDeAsientosPorFila;

	Sector(String nombre, int incremento, int capacidad, int asientosPorFila){
		this.nombre = nombre;
		this.incremento = incremento;
		this.capacidad = capacidad;
		this.cantidadDeAsientosPorFila = cantidadDeAsientosPorFila;
	}
	
	public int porcentajeAdicional() {
		return incremento;
	}
	
	public int cantidadDeAsientosPorFila() {
		return cantidadDeAsientosPorFila;
	}
	
	public int capacidad() {
		return capacidad;
	}
	
	public String toString() {
		return "";
	}
}
