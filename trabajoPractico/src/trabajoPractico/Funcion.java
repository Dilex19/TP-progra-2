package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class Funcion {
	private Sede sede;
	private LocalDate fecha;
	private double precioBase;
	private HashMap<String, boolean[]> asientos;
	private HashMap<String,IEntrada> entradasVendidas;
	
	
	Funcion(LocalDate fecha, Sede sede, double precioBaseS){
		if(precioBase<0) 
			throw new RuntimeException("Error: El precio base no puede ser menor a cero");
		
		this.sede = sede;
		this.precioBase = precioBaseS;
		this.asientos = new HashMap<String, boolean[]>();
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
				IEntrada nuevaEntrada = new Entrada(codigoEntrada, nombreEspectaculo, this.fecha, costo);
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
				IEntrada nuevaEntrada = new Entrada(codigoEntrada, nombreEspectaculo, fecha, sector, a, fila, costo);
				entradasVendidas.put(codigoEntrada, nuevaEntrada);
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
	
	public double totalRecaudado() {
		double totalRecaudado = 0;
		for(IEntrada entrada : entradasVendidas.values()) {
			totalRecaudado += entrada.precio();
		}
		return totalRecaudado;
	}
	
	public double totalRecaudadoPorSede(Sede sede) {
		if(this.sede.equals(sede)) {
			return totalRecaudado();
		} 
		return 0;
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
	
	public void anularEntrada(Entrada entra) {
		if(entradasVendidas.containsKey(entra.obtenerCodigo())) {
			entradasVendidas.remove(entra.obtenerCodigo());
			boolean[] asientosDelSector = asientos.get(entra.obtenerSector());
			asientosDelSector[entra.asiento()] = true;
		} else {
			throw new RuntimeException("Error: La entrada no esta registrada en la Funcion.");
		}
	}
	
	public String toString() {
		return "" + this.fecha;
	}
}
