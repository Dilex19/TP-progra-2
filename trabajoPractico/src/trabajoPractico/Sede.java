package trabajoPractico;

public abstract class Sede {
	private int capacidadMaxima;
	private String direccion;
	private String nombre;
	
	Sede(String nombre, String direccion, int capacidadMaxima){
		this.nombre = nombre;
		this.direccion = direccion;
		this.capacidadMaxima = capacidadMaxima;
	}
	
	public int capacidadMaxima(){
		return this.capacidadMaxima;
	}
}
