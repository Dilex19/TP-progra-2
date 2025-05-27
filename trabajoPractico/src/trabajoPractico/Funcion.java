package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
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
	
	//Constructor
	Funcion(LocalDate fecha, Sede sede, double precioBase){
		if(precioBase<=0) 
			throw new RuntimeException("Error: El precio base no puede ser menor o igual cero.");
		if(fecha == null)
			throw new RuntimeException("Error: La fecha no puede ser null.");
		if(sede == null)
			throw new RuntimeException("Error: La sede no puede ser null.");
		this.sede = sede;
		this.precioBase = precioBase;
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
	
	//Dado una cantidad de asientos, verifica si la cantidad de asientos necesarias estan disponibles, luego crea Entradas con sus
	//datos necesarios, guarda las entradas en un Map y devuelve una lista con las Entradas.
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
	//Dado unos asientos y un sector, verifica si los asientos estan disponibles.
	//Si estan disponibles los pone como ocupado y crea Entradas, las guarda y devuelve una lista con las Entradas.
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
	
	//Calcula y devuelve la cantidad de asientos disponibles segun la capacidad y la cantidad ya vendida
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
	
	//Calcula el costo de la entrada
	public double costoEntrada() {
		if(sede instanceof Estadio) {
			return sede.costoEntrada(precioBase);
		}else {
			throw new RuntimeException("Error: Los parametros no concuerdan con el tipo de Sede.");
		}
	}
	
	//Calcula el costo de la entrada segun un sector dado.
	//Si la sede es un teatro devuelve un numero diferente a si es un MiniEstadio.
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
	
	//Devuelve una lista de todas las entradas vendidas en la funcion.
	public LinkedList<IEntrada> listarEntradas(){
		LinkedList<IEntrada> entradas = new LinkedList<IEntrada>();
		for(IEntrada entrada : entradasVendidas.values()) {
			entradas.add(entrada);
		}
		return entradas;
	}
	
	//Devuelve el total recaudado por la funcion.
	public double totalRecaudado() {
		double totalRecaudado = 0;
		for(IEntrada entrada : entradasVendidas.values()) {
			totalRecaudado += entrada.precio();
		}
		return totalRecaudado;
	}
	
	//Genera un codigo universal unico el cual es muy poco probable que se repita. Es poco probable, pero verifica que no se repita.
	public String codigoRandomParaEntrada() {
		String codigo = UUID.randomUUID().toString(); 
		//Verifica que no se repita.
		while(entradasVendidas.containsKey(codigo)) {
			codigo = UUID.randomUUID().toString(); 
		}
		return codigo;
	}
	
	//Dado el codigo de una entrada, su sector y su asiento, si se encuentra registrada la elimina.
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
	
	//Devuelve la cantidad de entradas vendidas en un sector especifico
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
	
	//Devuelve un array con la cantidad de entradas vendidas por Sector.
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
	
	//Imprime Funcion y sus datos.
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
