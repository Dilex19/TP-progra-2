package trabajoPractico;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SedeConSectores extends Sede {
	private Map<String, Sector> sectores;
	private int cantidadDeAsientosPorFila;
	
	SedeConSectores(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMaxima);
		int total = 0;
		for(int i : capacidad) {
			total+=i;
		}
		if(total != capacidadMaxima) {
			throw new RuntimeException("La suma de la capacidad de los sectores no concuerda con la capacidad maxima de la Sede");
		}
		if(asientosPorFila<=0) {
			throw new RuntimeException("Error: la cantidad de asientos por fila no puede ser negativa o cero.");
		}
		if(sectores == null || sectores.length<1)
			throw new RuntimeException("Error: Los sectores son invalidos");
		
		if(porcentajeAdicional == null) {
	        throw new RuntimeException("Error: El porcentaje adicional no puede ser nulo");
	    }
		
		if(porcentajeAdicional.length != sectores.length) {
			throw new RuntimeException("Error: El porcentaje adicional por sector y la cantidad de sectores no corresponden a la misma cantidad.");
		}
		
		this.sectores = new LinkedHashMap<String , Sector>();
		this.cantidadDeAsientosPorFila = asientosPorFila;
		for(int i = 0 ; i<sectores.length; i++) {
			if(sectores[i].length()<2) {
				throw new RuntimeException("Error: Los sectores deben tener al menos 2 caracteres.");
			}
			Sector sector = new Sector(sectores[i], porcentajeAdicional[i], capacidad[i]);
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
		return precioBase * (1+ (sec.porcentajeAdicional() / 100.0)) ;
	}
	
	public Sector obtenerSector(String nombreSector) {
		return sectores.get(nombreSector);
	}
	
	public int filaDeUnAsiento(int asiento) {
		return 1 + (asiento/cantidadDeAsientosPorFila);
	}
	
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.nombre());
        sb.append(" - ");
        for (Sector sector : sectores.values()) {
            sb.append(sector.toString()).append(" | ");
        }
        return sb.toString().replaceAll(" \\| $", ""); 
    }
	
	public String toString(int[] cantidadDeEntradasVendidas) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.nombre());
        sb.append(" - ");
        int cantSector = 0;
        for (Sector sector : sectores.values()) {
            sb.append(sector.toString(cantidadDeEntradasVendidas[cantSector])).append(" | ");
            cantSector++;
        }
        return sb.toString().replaceAll(" \\| $", "");
    }
}
