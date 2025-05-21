package trabajoPractico;

import java.util.HashMap;

public abstract class SedeConSectores extends Sede {
	private HashMap<String, Sector> sectores;
	
	SedeConSectores(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMaxima);
		this.sectores = new HashMap<String , Sector>();
		
		for(int i = 0 ; i<sectores.length; i++) {
			Sector sector = new Sector(sectores[i], porcentajeAdicional[i], capacidad[i], asientosPorFila);
			this.sectores.put(sectores[i], sector);
		}
	}
	
	
	public String[] sectores() {
		String[] sectores = new String[this.sectores.size()];
		int i = 0;
		for(HashMap.Entry<String, Sector> entry : this.sectores.entrySet()) {
			sectores[i] = entry.getKey();
			i++;
		}
		
		return sectores;
	}
	
	public int[] capacidadPorSector() {
		int[] capacidad = new int[this.sectores.size()];
		int i = 0;
		for(HashMap.Entry<String, Sector> entry : this.sectores.entrySet()) {
			capacidad[i] = entry.getValue().capacidad();
			i++;
		}
		return capacidad;
	}
	
	public double costoEntrada(String sector, double precioBase) {
		Sector sec = this.sectores.get(sector);
		return precioBase * ((1+ sec.porcentajeAdicional()) / 100.0) ;
	}
	
	public Sector obtenerSector(String nombreSector) {
		return sectores.get(nombreSector);
	}
}
