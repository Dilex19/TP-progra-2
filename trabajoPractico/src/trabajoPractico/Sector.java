package trabajoPractico;

public class Sector {
	private String nombre;
	private double incremento;
	private	int capacidad;

	Sector(String nombre, int incremento, int capacidad){
		if(nombre == null || nombre.length() <2)
			throw new RuntimeException("Error: El nombre del sector no puede contener menos de 2 caracteres.");
		if(incremento<0) 
			throw new RuntimeException("Error: El incremento no puede ser negativo");
		if(capacidad<0)
			throw new RuntimeException("Error: La capacidad no puede ser negativa o cero");
		this.nombre = nombre;
		this.incremento = incremento;
		this.capacidad = capacidad;
	}
	
	//Devuelve un valor int que representa un incremento.
	
	public double porcentajeAdicional() {
		return incremento;
	}
	
	//Devuelve la capacidad del sector.
	public int capacidad() {
		return capacidad;
	}
	
	//Versión básica para representar el objeto como cadena.
	public String toString( ) {
        return String.format("%s - Capacidad: %d", nombre, capacidad);
    }
	
	//Versión extendida que muestra información de ventas.
	public String toString(int cantidadVendidas) {
		return String.format("%s: %d/%d", nombre, cantidadVendidas, capacidad);

	}
}
