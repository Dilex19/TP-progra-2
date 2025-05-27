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
	
	public double porcentajeAdicional() {
		return incremento;
	}
	
	public int capacidad() {
		return capacidad;
	}
	
	public String toString( ) {
        return String.format("%s - Capacidad: %d", nombre, capacidad);
    }
	
	public String toString(int cantidadVendidas) {
		return String.format("%s: %d/%d", nombre, cantidadVendidas, capacidad);

	}
}
