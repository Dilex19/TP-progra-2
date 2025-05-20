package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;

public class Funcion {
	private Sede sede;
	private SedeConSectores sedeConSectores;
	private LocalDate fecha;
	private double precioBase;
	private HashMap<String, boolean[]> asientos;
	private HashMap<String,Entrada> entradasVendidas;
	
	Funcion(Sede sede, String fecha, double precioBase){
		this.sede = sede;
		this.precioBase = precioBase;
		this.asientos = new HashMap<String, boolean[]>();
		this.entradasVendidas = new HashMap<String, Entrada>();
		
		if(sede instanceof SedeConSectores) {
			SedeConSectores sedeConSec = (SedeConSectores) sede; 
			
			String[] sectoresDeSede = sedeConSec.sectores();
			for(int i = 0; i < sectoresDeSede.length; i++) {
				
				int capacidadPorSector = sedeConSec.capacidadPorSector().length;
				boolean[] asientosBoolean = new boolean[capacidadPorSector];
				
				for(int x = 0; x<capacidadPorSector; x++) {
					asientosBoolean[x] = true;
				}
				
				asientos.put(sectoresDeSede[i], asientosBoolean);
			}
		}
	}
}
