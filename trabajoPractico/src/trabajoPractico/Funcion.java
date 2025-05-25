package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Funcion {
	private Sede sede;
	private LocalDate fecha;
	private double precioBase;
	private Map<String, boolean[]> asientos;
	private HashMap<String,IEntrada> entradasVendidas;
	
	
	Funcion(LocalDate fecha, Sede sede, double precioBaseS){
		if(precioBase<0) 
			throw new RuntimeException("Error: El precio base no puede ser menor a cero");
		
		this.sede = sede;
		this.precioBase = precioBaseS;
		this.asientos = new LinkedHashMap<String, boolean[]>();
		this.entradasVendidas = new HashMap<String, IEntrada>();
		this.fecha = fecha;
		
		if(sede instanceof SedeConSectores) {
			SedeConSectores sedeConSec = (SedeConSectores) sede; 
			
			String[] sectoresDeSede = sedeConSec.sectores();
			for(int i = 0; i < sectoresDeSede.length; i++) {
				
				int capacidadPorSector = sedeConSec.capacidadPorSector()[i];
				boolean[] asientosBoolean = new boolean[capacidadPorSector];
				
				for(int x = 0; x<capacidadPorSector; x++) {
					
					asientosBoolean[x] = true;
				}
				
				asientos.put(sectoresDeSede[i], asientosBoolean);
			}
		}
	}
	
	public LinkedList<IEntrada> venderEntrada(String nombreEspectaculo, int cantAsientos){
		if(sede instanceof Estadio) {
			
			if(cantidadAsientosDisponibles() - cantAsientos <0) {
				throw new RuntimeException("Error: Se quiere comprar más entradas de las disponibles. La cantidad de entradas dispobibles es: " + cantidadAsientosDisponibles());
			}
			
			LinkedList<IEntrada> nuevasEntradas = new LinkedList<IEntrada>();
			for(int i = 0; i < cantAsientos; i++) {
				String codigoEntrada = codigoRandomParaEntrada();
				double costo = costoEntrada();
				IEntrada nuevaEntrada = new Entrada(codigoEntrada, nombreEspectaculo, this.fecha, sede.nombre(), costo);
				entradasVendidas.put(codigoEntrada, nuevaEntrada);
				nuevasEntradas.add(nuevaEntrada);
			}
			
			return nuevasEntradas;
			
		}
		else {
			throw new RuntimeException("Error: Los parametros no concuerdan con el tipo de Sede.");
		}
	}
	
	public LinkedList<IEntrada> venderEntrada(String nombreEspectaculo, String sector, int[] asientos){
		if(sede instanceof SedeConSectores) {
			if(!estanDisponibles(sector, asientos)) {
				throw new RuntimeException("Error: Hay asientos no disponibles para su venta");
			}
			
			SedeConSectores sedeConS = (SedeConSectores) sede;
			boolean[] arrayAsientos = this.asientos.get(sector);
			LinkedList<IEntrada> nuevasEntradas = new LinkedList<IEntrada>();
			
			
			for(int a : asientos) {
				arrayAsientos[a] = false;
				int fila = sedeConS.filaDeUnAsiento(a);
				String codigoEntrada = codigoRandomParaEntrada();
				double costo = costoEntrada(sector);
				IEntrada nuevaEntrada = new Entrada(codigoEntrada, nombreEspectaculo, fecha, sede.nombre(),sector, a, fila, costo);
				entradasVendidas.put(codigoEntrada, nuevaEntrada);
				nuevasEntradas.add(nuevaEntrada);
			}
			return nuevasEntradas;
			
		}
		else {
			throw new RuntimeException("Error: Los parametros no concuerdan con el tipo de Sede.");
		}
	}
	
	public int cantidadAsientosDisponibles() {
		return sede.capacidadMaxima() - entradasVendidas.size();
	}
	
	public boolean estanDisponibles(String sector,int[] asientos) {
		boolean[] arrayAsientos = this.asientos.get(sector);
		for(int a : asientos) {
			if(arrayAsientos[a] == false) {
				return false;
			}
		}
		return true;
	}
	
	
	public double costoEntrada() {
		if(sede instanceof Estadio) {
			return sede.costoEntrada(precioBase);
		}else {
			throw new RuntimeException("Error: Los parametros no concuerdan con el tipo de Sede.");
		}
	}
	
	public double costoEntrada(String sector) {
		if(sede instanceof SedeConSectores) {
			if(sede instanceof MiniEstadio) {
				MiniEstadio sedeMiniEstadio = (MiniEstadio) sede;
				return sedeMiniEstadio.costoEntrada(sector, precioBase);
			}
			SedeConSectores sedeConSec = (SedeConSectores) sede;
			return sedeConSec.costoEntrada(sector, precioBase);
		}else {
			throw new RuntimeException("Error: Los parametros no concuerdan con el tipo de Sede.");
		}
	}
	
	public LinkedList<IEntrada> listarEntradas(){
		LinkedList<IEntrada> entradas = new LinkedList<IEntrada>();
		for(IEntrada entrada : entradasVendidas.values()) {
			entradas.add(entrada);
		}
		return entradas;
	}
	
	public String codigoRandomParaEntrada() {
		int randomNum = (int)(Math.random() * (100000 - 10000)) + 10000;
		String randomNumString = randomNum + "";
		while(entradasVendidas.containsKey(randomNumString)) {
			randomNum = (int)(Math.random() * (100000 - 10000)) + 10000;
			randomNumString = "" + randomNum;
		}
		return randomNumString;
	}
	
	public void anularEntrada(String codigoEntrada, String sectorEntrada, int asientoEntrada) {
		if(entradasVendidas.containsKey(codigoEntrada)) {
			if(sede instanceof SedeConSectores) {
				entradasVendidas.remove(codigoEntrada);
				boolean[] asientosDelSector = asientos.get(sectorEntrada);
				asientosDelSector[asientoEntrada] = true;
			} else {
				entradasVendidas.remove(codigoEntrada);
			}
			
		} else {
			throw new RuntimeException("Error: La entrada no esta registrada en la Funcion.");
		}
	}
	
	public int entradasVendidasPorSector(String sector) {
		if(!asientos.containsKey(sector)) 
			throw new RuntimeException("Error: El sector indicado no existe.");
		boolean[] asientos = this.asientos.get(sector);
		int compradas = 0;
		for(boolean asiento : asientos) {
			if(asiento == false)
				compradas+=1;
		}
		return compradas;
	}
	
	public int[] entradasVendidasPorSector() {
		int[] compradasPorSector = new int[asientos.size()];
		int i = 0;
		Iterator<String> iterador = asientos.keySet().iterator();
		while (iterador.hasNext()) {
    	    String clave = iterador.next();
    	    compradasPorSector[i] = entradasVendidasPorSector(clave);
    	    i++;
    	}
		
		return compradasPorSector;
	}
	
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        String fechaStr = fecha.format(formatter);
        
        StringBuilder sb = new StringBuilder();
        sb.append(" - (");
        sb.append(fechaStr);
        sb.append(") ");
        if(sede instanceof SedeConSectores) {
        	SedeConSectores sedeConSectores = (SedeConSectores) sede;
            int[] entradasVendidasCant = entradasVendidasPorSector();
            sb.append(sedeConSectores.toString(entradasVendidasCant) );
        	return sb.toString().replaceAll(" \\| $", "");
        }
        sb.append(sede.toString(entradasVendidas.size()) );
    	return sb.toString();
	}
	
}
