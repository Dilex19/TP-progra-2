package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class Funcion {
	private Sede sede;
	private Fecha fecha;
	private double precioBase;
	private HashMap<String, boolean[]> asientos;
	private HashMap<String,Entrada> entradasVendidas;
	
	
	Funcion(Sede sede, String fecha, double precioBase){
		this.sede = sede;
		this.precioBase = precioBase;
		this.asientos = new HashMap<String, boolean[]>();
		this.entradasVendidas = new HashMap<String, Entrada>();
		this.fecha = new Fecha(fecha);
		
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
	
	public LinkedList<Entrada> venderEntrada(String nombreEspectaculo, int cantAsientos){
		if(sede instanceof Estadio) {
			
			if(cantidadAsientosDisponibles() - cantAsientos >=0) {
				LinkedList<Entrada> nuevasEntradas = new LinkedList<Entrada>();
				for(int i = 0; i < cantAsientos; i++) {
					String codigoEntrada = codigoRandomParaEntrada();
					Entrada nuevaEntrada = new Entrada(codigoEntrada, nombreEspectaculo, this.fecha, costoEntrada());
					entradasVendidas.put(codigoEntrada, nuevaEntrada);
					nuevasEntradas.add(nuevaEntrada);
				}
				return nuevasEntradas;
			}
			else {
				throw new RuntimeException("Error: Se quiere comprar más entradas de las disponibles. La cantidad de entradas dispobibles es: " + cantidadAsientosDisponibles());
			}
		}
		else {
			throw new RuntimeException("Error: Los parametros no concuerdan con el tipo de Sede.");
		}
	}
	
	public LinkedList<Entrada> venderEntrada(String nombreEspectaculo, String sector, int[] asientos){
		if(sede instanceof SedeConSectores) {
			SedeConSectores sedeConS = (SedeConSectores) sede;
			boolean[] arrayAsientos = this.asientos.get(sector);
			LinkedList<Entrada> nuevasEntradas = new LinkedList<Entrada>();
			if(estanDisponibles(sector, asientos)) {
				for(int a : asientos) {
					arrayAsientos[a] = false;
					int fila = sedeConS.filaDeUnAsiento(a);
					String codigoEntrada = codigoRandomParaEntrada();
					Entrada nuevaEntrada = new Entrada(codigoEntrada, nombreEspectaculo, fecha, sector, a, fila, costoEntrada(sector));
					entradasVendidas.put(codigoEntrada, nuevaEntrada);
				}
			}else {
				throw new RuntimeException("Error: Hay asientos no disponibles para su venta");
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
	
	public LinkedList<Entrada> listarEntradas(){
		LinkedList<Entrada> entradas = new LinkedList<Entrada>();
		for(Entrada entrada : entradasVendidas.values()) {
			entradas.add(entrada);
		}
		return entradas;
	}
	
	public double totalRecaudado() {
		double totalRecaudado = 0;
		for(Entrada entrada : entradasVendidas.values()) {
			totalRecaudado = entrada.precio();
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
}
