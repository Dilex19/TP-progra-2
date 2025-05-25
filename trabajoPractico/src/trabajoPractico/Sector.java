package trabajoPractico;

public class Sector {
	private String nombre;
	private double incremento;
	private	int capacidad;

	Sector(String nombre, int incremento, int capacidad){
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
