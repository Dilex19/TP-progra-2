package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class Espectaculo {
	private String nombre;
	private HashMap<LocalDate,Funcion> funciones;
	
	Espectaculo(String nombre){
		this.nombre =  nombre;
		this.funciones = new  HashMap<LocalDate,Funcion>();
	}
	
	public LinkedList<IEntrada> venderEntrada(String nombreEspectaculo, LocalDate fecha, int cantAsientos){
		if(!funciones.containsKey(fecha)) 
			throw new RuntimeException("Error: El espectaculo no continene una funcion en esa fecha.");
		
		if(nombreEspectaculo.isEmpty()) 
			throw new RuntimeException("Error: El nombre no puede estar vacio.");
		
		if(cantAsientos <1) 
			throw new RuntimeException("Error: La cantidad de asientos no puede ser menor a 1");
		
		Funcion funcion = funciones.get(fecha);
		return funcion.venderEntrada(nombreEspectaculo, cantAsientos);
	}
	
	
	public LinkedList<IEntrada> venderEntrada(String nombreEspectaculo, LocalDate fecha, String sector, int[] asientos){
		if(!funciones.containsKey(fecha)) 
			throw new RuntimeException("Error: El espectaculo no contiene una función en esa fecha.");
		
		if(nombreEspectaculo.isEmpty() || sector.isEmpty()) 
			throw new RuntimeException("Error: El nombre o el sector no puede estar vacio.");
		
		if(asientos.length == 0) 
			throw new RuntimeException("Error: La longitud de asientos no puede ser igual a 0");
		
		Funcion funcion = funciones.get(fecha);
		return funcion.venderEntrada(nombreEspectaculo,sector, asientos);
	}
	
	public void agregarFuncion(LocalDate fecha, Sede sede, double precioBase) {
		if(funciones.containsKey(fecha)) 
			throw new RuntimeException("Error: El espectaculo ya tiene una funcion registrada en esa fecha.");
		
		Funcion nuevaFuncion = new Funcion(fecha, sede, precioBase);
		funciones.put(fecha, nuevaFuncion);
	}
	
	public String listarFunciones() {
		StringBuilder listaFunciones = new StringBuilder();
		for(Funcion funcion : funciones.values()) {
			listaFunciones.append(funcion.toString()+ "\n");
		}
		String listaFuncionesString = "" + listaFunciones;
		
		return listaFuncionesString;
	}
	
	public LinkedList<IEntrada> listarEntradas(){
		LinkedList<IEntrada> entradasTotales = new LinkedList<IEntrada>();
		for(Funcion funcion : funciones.values()) {
			LinkedList<IEntrada> entradasDeFuncion = funcion.listarEntradas();
			entradasTotales.addAll(entradasDeFuncion);
		}
		return entradasTotales;
	}
	
	public void anularEntrada(Entrada entrada) {
		if(!funciones.containsKey(entrada.obtenerFecha())){
			throw new RuntimeException("Error: La fecha de la entrada no concuerda con las fechas registradas del espectaculo.");
		}
		Funcion funcion = funciones.get(entrada.obtenerFecha());
		funcion.anularEntrada(entrada);
	}
	
	public double costoEntrada(LocalDate fecha) {
		if(!funciones.containsKey(fecha)){
			throw new RuntimeException("Error: La fecha no concuerda con las fechas registradas del espectaculo.");
		}
		Funcion funcion = funciones.get(fecha);
		return funcion.costoEntrada();
	}
	
	public double costoEntrada(LocalDate fecha, String sector) {
		if(!funciones.containsKey(fecha)){
			throw new RuntimeException("Error: La fecha no concuerda con las fechas registradas del espectaculo.");
		}
		Funcion funcion = funciones.get(fecha);
		return funcion.costoEntrada(sector);
	}
	
	public double totalRecaudado() {
		double totalRecaudado = 0;
		for(Funcion funcion : funciones.values()) {
			totalRecaudado+= funcion.totalRecaudado();
		}
		return totalRecaudado;
	}
	
	public double totalRecaudado(Sede sede) {
		double totalRecaudadoPorSede = 0;
		for(Funcion funcion : funciones.values()) {
			totalRecaudadoPorSede+= funcion.totalRecaudadoPorSede(sede);
		}
		return totalRecaudadoPorSede;
	}
}
