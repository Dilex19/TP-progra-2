package trabajoPractico;

public class MiniEstadio extends SedeConSectores{
	private int cantidadPuestos;
	private double precioConsumision;
	
	//Constructor
	MiniEstadio(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
		if(cantidadPuestos < 0) {
			throw new RuntimeException("Error: La cantidad de puestos no puede ser negativa");
		}
		if(precioConsumicion < 0) {
			throw new RuntimeException("Error: El precio por consumision no puede ser negativo");
		}
		
		this.cantidadPuestos = cantidadPuestos;
		this.precioConsumision = precioConsumicion;
	}
	
	//Dado el nombre del sector y el precio base, devuelve el costo correspondiente a la entrada.
	public double costoEntrada(String nombreSector, double precioBase) {
		Sector sec = obtenerSector(nombreSector); 
		return precioConsumision + (precioBase * (1+ (sec.porcentajeAdicional() / 100.0)));
	}

}
