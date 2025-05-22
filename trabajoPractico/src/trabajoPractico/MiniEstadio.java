package trabajoPractico;

public class MiniEstadio extends SedeConSectores{
	private double valorFijo;
	private int cantidadPuestos;
	private double precioConsumision;
	
	MiniEstadio(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
		this.cantidadPuestos = cantidadPuestos;
		this.precioConsumision = precioConsumicion;
	}
	
	public double costoEntrada(String nombreSector, double precioBase) {
		Sector sec = obtenerSector(nombreSector);
		return valorFijo + precioBase * ((precioBase * sec.porcentajeAdicional()) / 100.0);
	}

}
