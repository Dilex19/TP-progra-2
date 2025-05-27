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
	
	//Calcula el costo de entrada. 
	public double costoEntrada(double precioBase) {
		return precioBase;
	}
	
    //Getter para obtener la capacidad máxima de la sede.
	public int capacidadMaxima(){
		return this.capacidadMaxima;
	}
	
	//Getter para obtener el nombre de la sede.
	public String nombre() {
		return nombre;
	}
	
	//Representación en String de la sede (formato básico).
	public String toString() {
		return String.format("%s - Capacidad: %d", nombre, capacidadMaxima);
	}
	
	//Representación extendida con información de ventas.
	public String toString(int cantidadVendidas) {
		return String.format("%s: %d/%d", nombre, cantidadVendidas, capacidadMaxima);
	}

}
