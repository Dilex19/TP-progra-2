package trabajoPractico;

public class MiniEstadio extends SedeConSectores{
	private int cantidadPuestos;
	private double precioConsumision;
	
	MiniEstadio(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
		this.cantidadPuestos = cantidadPuestos;
		this.precioConsumision = precioConsumicion;
	}
	
	public double costoEntrada(String nombreSector, double precioBase) {
		Sector sec = obtenerSector(nombreSector); 
		return precioConsumision + (precioBase * (1+ (sec.porcentajeAdicional() / 100.0)));
	}

}
