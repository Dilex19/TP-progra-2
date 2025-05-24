package trabajoPractico;

import java.util.HashMap;

public abstract class SedeConSectores extends Sede {
	private HashMap<String, Sector> sectores;
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
			throw new RuntimeException("Error: la cantidad de asientos por fila no puede ser negativa o cero");
		}
		
		this.sectores = new HashMap<String , Sector>();
		this.cantidadDeAsientosPorFila = asientosPorFila;
		for(int i = 0 ; i<sectores.length; i++) {
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
		return 1 + asiento% cantidadDeAsientosPorFila;
	}
	
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString() + " - ");
        for (Sector sector : sectores.values()) {
            sb.append(sector.toString()).append(" | ");
        }
        return sb.toString().replaceAll(" \\| $", ""); 
    }
	
	public String toString(int[] cantidadVendidas) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString() + " - ");
        int cantSector = 0;
        for (Sector sector : sectores.values()) {
            sb.append(sector.toString(cantidadVendidas[cantSector])).append(" | ");
            cantSector++;
        }
        return sb.toString().replaceAll(" \\| $", "");
    }
}
