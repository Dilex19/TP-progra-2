package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Funcion {
	private Sede sede;
	private Fecha fecha;
	private double precioBase;
	private Map<String, ArrayList<Boolean>> asientos;
	private Map<String,IEntrada> entradasVendidas;
	
	//Constructor
	Funcion(Fecha fecha, Sede sede, double precioBase){
		if(precioBase<=0) 
			throw new RuntimeException("Error: El precio base no puede ser menor o igual cero.");
		if(fecha == null)
			throw new RuntimeException("Error: La fecha no puede ser null.");
		if(sede == null)
			throw new RuntimeException("Error: La sede no puede ser null.");
		this.sede = sede;
		this.precioBase = precioBase;
		this.asientos = new LinkedHashMap<String, ArrayList<Boolean>>();
		this.entradasVendidas = new HashMap<String, IEntrada>();
		this.fecha = fecha;
		
		if(sede instanceof SedeConSectores) {
			SedeConSectores sedeConSec = (SedeConSectores) sede; 
			
			String[] sectoresDeSede = sedeConSec.sectores();
			for(int i = 0; i < sectoresDeSede.length; i++) {
				
				int capacidadPorSector = sedeConSec.capacidadPorSector()[i];
				
				ArrayList<Boolean> asientosLista = new ArrayList<Boolean>();
				
				for(int x = 0; x<capacidadPorSector; x++) {
					asientosLista.add(true);
				}
				
				this.asientos.put(sectoresDeSede[i], asientosLista);
			}
		}
	}
	

	public LinkedList<IEntrada> venderEntrada(String nombreEspectaculo, int cantEspacios){
		if(sede instanceof Estadio) {
			
			if(cantidadPuestosDisponibles() - cantEspacios <0) {
				throw new RuntimeException("Error: Se quiere comprar más entradas de las disponibles. La cantidad de entradas dispobibles es: " + cantidadPuestosDisponibles());
			}
			
			LinkedList<IEntrada> nuevasEntradas = new LinkedList<IEntrada>();
			for(int i = 0; i < cantEspacios; i++) {
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
			ArrayList<Boolean> asientosLista = this.asientos.get(sector);
			LinkedList<IEntrada> nuevasEntradas = new LinkedList<IEntrada>();
			
			
			for(int a : asientos) {
				asientosLista.set(a, false);
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
	
	
	public int cantidadPuestosDisponibles() {
		return sede.capacidadMaxima() - entradasVendidas.size();
	}
	
	public boolean estanDisponibles(String sector,int[] asientos) {
		ArrayList<Boolean> arrayAsientos = this.asientos.get(sector);
		for(int a : asientos) {
			if(arrayAsientos.get(a) == false) {
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
	
	
	public double totalRecaudado() {
		double totalRecaudado = 0;
		for(IEntrada entrada : entradasVendidas.values()) {
			totalRecaudado += entrada.precio();
		}
		return totalRecaudado;
	}
	
	public String codigoRandomParaEntrada() {
		String codigo = UUID.randomUUID().toString(); 
		while(entradasVendidas.containsKey(codigo)) {
			codigo = UUID.randomUUID().toString(); 
		}
		return codigo;
	}
	
	
	public void anularEntrada(String codigoEntrada, String sectorEntrada, int asientoEntrada) {
		if(entradasVendidas.containsKey(codigoEntrada)) {
			if(sede instanceof SedeConSectores) {
				entradasVendidas.remove(codigoEntrada);
				ArrayList<Boolean> asientosDelSector = asientos.get(sectorEntrada);
				asientosDelSector.set(asientoEntrada, true);
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
		ArrayList<Boolean> asientosLista = this.asientos.get(sector);
		int compradas = 0;
		for(boolean asiento : asientosLista) {
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
    public boolean asientoDisponible(String sector, int asiento) {
        if(!asientos.containsKey(sector)) {
            return false;
        }
        
        ArrayList<Boolean> arrayAsientos = asientos.get(sector);
        
       
        if(asiento < 0 || asiento >= arrayAsientos.size()) {
            return false;
        }
        
        
        return arrayAsientos.get(asiento);
    }


	
	public String toString() {
        String fechaStr = fecha.toString();
        
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