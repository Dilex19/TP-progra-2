package trabajoPractico;

public abstract class Sede {
	private int capacidadMaxima;
	private String direccion;
	private String nombre;
	
	Sede(String nombre, String direccion, int capacidadMaxima){
		if(nombre.isEmpty() || direccion.isEmpty())
			throw new RuntimeException("Error: El nombre y la direccion no pueden estan vacios.");
		
		if(capacidadMaxima <=0) 
			throw new RuntimeException("Error: La capacidad maxima no puede ser negativa o 0.");
	
		if(direccion == null || nombre ==  null)
			throw new RuntimeException("Error: La dirección y el nombre no pueden ser nulos.");
		
		this.nombre = nombre;
		this.direccion = direccion;
		this.capacidadMaxima = capacidadMaxima;
	}
	
	public double costoEntrada(double precioBase) {
		return precioBase;
	}
	
	public int capacidadMaxima(){
		return this.capacidadMaxima;
	}
	
	public String nombre() {
		return nombre;
	}
	
	public String toString() {
		return String.format("%s - Capacidad: %d", nombre, capacidadMaxima);
	}
	
	public String toString(int cantidadVendidas) {
		return String.format("%s: %d/%d", nombre, cantidadVendidas, capacidadMaxima);
	}

}
