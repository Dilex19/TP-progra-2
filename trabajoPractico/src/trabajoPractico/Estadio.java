package trabajoPractico;

public class Estadio extends Sede{

	Estadio(String nombre, String direccion, int capacidadMaxima) {
		super(nombre, direccion, capacidadMaxima);
	}

	public double costoEntrada(double precioBase, String sector) {
		// En estadios solo hay campo, no hay incrementos por sector
		if(sector != null && !sector.equals("campo")) {
			throw new RuntimeException("Error: En estadios solo existe el sector campo");
		}
		return precioBase;
	}
	public boolean esEstadio() {
		return true;
	}

}
