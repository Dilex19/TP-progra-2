package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class Funcion {
	private Sede sede;
	private SedeConSectores sedeConSectores;
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
					Entrada nuevaEntrada = new Entrada(codigoEntrada, nombreEspectaculo, this.fecha);
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
			boolean[] arrayAsientos = this.asientos.get(sector);
			LinkedList<Entrada> nuevasEntradas = new LinkedList<Entrada>();
			if(estanDisponibles(sector, asientos)) {
				for(int a : asientos) {
					arrayAsientos[a] = false;
					String codigoEntrada = codigoRandomParaEntrada();
					Entrada nuevaEntrada = new Entrada(codigoEntrada, nombreEspectaculo, fecha, sector, a, precioBase);
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
	
	public String codigoRandomParaEntrada() {
		int randomNum = (int)(Math.random() * (100000 - 10000)) + 10000;
		String randomNumString = randomNum + "";
		while(entradasVendidas.containsKey(randomNumString)) {
			randomNum = (int)(Math.random() * (100000 - 10000)) + 10000;
			randomNumString = "" + randomNum;
		}
		return randomNumString;
	}
}
