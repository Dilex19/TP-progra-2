package trabajoPractico;

public abstract class Sede {
	private int capacidadMaxima;
	private String direccion;
	private String nombre;
	
	Sede(String nombre, String direccion, int capacidadMaxima){
		if(nombre.isEmpty() || direccion.isEmpty() || capacidadMaxima<= 0) {
			throw new RuntimeException("Error: El nombre y la direccion no pueden estan vacios, y la capacidad maxima no puede ser menor a 1");
		}
		this.nombre = nombre;
		this.direccion = direccion;
		this.capacidadMaxima = capacidadMaxima;
	}
	
	public int capacidadMaxima(){
		return this.capacidadMaxima;
	}
	
	public double costoEntrada(double precioBase) {
		return precioBase;
	}
}
