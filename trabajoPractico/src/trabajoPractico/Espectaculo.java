package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Espectaculo {
	private String nombre;
	private Map<LocalDate,Funcion> funciones;
	private Map<String, Double> RecaudadoPorSede; 
	
	//Constructor
	Espectaculo(String nombre){
		if(nombre == null || nombre.length()<2)
			throw new RuntimeException("Error: El nombre del espectaculo no puede tener menos de 2 caracteres.");
		this.nombre =  nombre;
		this.funciones = new  TreeMap<LocalDate,Funcion>();
		this.RecaudadoPorSede = new TreeMap<String, Double>();
	}
	
	//Dada una fecha y una cantiada de asientos, si los datos son correctos, devuelve una lista con las entradas compradas.
	public LinkedList<IEntrada> venderEntrada(String nombreEspectaculo, LocalDate fecha, int cantAsientos){
		if(!funciones.containsKey(fecha)) 
			throw new RuntimeException("Error: El espectaculo no continene una funcion en esa fecha.");
		
		if(nombreEspectaculo.isEmpty()) 
			throw new RuntimeException("Error: El nombre no puede estar vacio.");
		
		if(cantAsientos <1) 
			throw new RuntimeException("Error: La cantidad de asientos no puede ser menor a 1");
		
		Funcion funcion = funciones.get(fecha);
		LinkedList<IEntrada> entradas= funcion.venderEntrada(nombreEspectaculo, cantAsientos);
		agregarValorDeEntradasALoRecaudado(entradas,entradas.get(0).obtenerSede());
		return entradas;
	}
	
	//Dada una fecha, el sector y un array de asientos, si los datos son correctos, devuelve una lista con las entradas compradas.
	public LinkedList<IEntrada> venderEntrada(String nombreEspectaculo, LocalDate fecha, String sector, int[] asientos){
		if(!funciones.containsKey(fecha)) 
			throw new RuntimeException("Error: El espectaculo no contiene una función en esa fecha.");
		
		if(nombreEspectaculo.isEmpty() || sector.isEmpty()) 
			throw new RuntimeException("Error: El nombre o el sector no puede estar vacio.");
		
		if(asientos.length == 0) 
			throw new RuntimeException("Error: La longitud de asientos no puede ser igual a 0");
		
		Funcion funcion = funciones.get(fecha);
		LinkedList<IEntrada> entradas= funcion.venderEntrada(nombreEspectaculo,sector, asientos);
		agregarValorDeEntradasALoRecaudado(entradas,entradas.get(0).obtenerSede());
		return entradas;
	}
	
	
	//Agrega el valor de la entrada comprada al Map.
	private	 void agregarValorDeEntradasALoRecaudado(LinkedList<IEntrada> entradas,String sede) {
		for(IEntrada entrada : entradas) {
			RecaudadoPorSede.merge(sede, entrada.precio(), Double::sum);
		}
	}
	
	//Crea y agrega una nueva funcion al Map de funciones.
	public void agregarFuncion(LocalDate fecha, Sede sede, double precioBase) {
		if(funciones.containsKey(fecha)) 
			throw new RuntimeException("Error: El espectaculo ya tiene una funcion registrada en esa fecha.");
		
		Funcion nuevaFuncion = new Funcion(fecha, sede, precioBase);
		funciones.put(fecha, nuevaFuncion);
		if(!RecaudadoPorSede.containsKey(sede.nombre())) {
			RecaudadoPorSede.put(sede.nombre(), 0.0);
		}
	}
	
	
	//Lista todas las funciones del espectaculo
	public String listarFunciones() {
		StringBuilder listaFunciones = new StringBuilder();
		for(Funcion funcion : funciones.values()) {
			listaFunciones.append(funcion.toString());
			listaFunciones.append("\n");
		}
		String listaFuncionesString = "" + listaFunciones;
		
		return listaFuncionesString;
	}
	
	//Lista todas las entradas de todas las funciones del espectaculo.
	public LinkedList<IEntrada> listarEntradas(){
		LinkedList<IEntrada> entradasTotales = new LinkedList<IEntrada>();
		for(Funcion funcion : funciones.values()) {
			LinkedList<IEntrada> entradasDeFuncion = funcion.listarEntradas();
			entradasTotales.addAll(entradasDeFuncion);
		}
		return entradasTotales;
	}
	
	//Dada una fecha, el codigo de la entrada, el sector y el numero del asiento, elimina la entrada comprada.
	public void anularEntrada(LocalDate fechaEntrada, String codigoEntrada, String sectorEntrada, int asientoEntrada) {
		if(!funciones.containsKey(fechaEntrada)){
			throw new RuntimeException("Error: La fecha de la entrada no concuerda con las fechas registradas del espectaculo.");
		}
		Funcion funcion = funciones.get(fechaEntrada);
		funcion.anularEntrada(codigoEntrada, sectorEntrada, asientoEntrada);
	}
	
	//Dada una fecha, devuelve el valor de la entrada correspondiente a la funcion en esa fecha
	public double costoEntrada(LocalDate fecha) {
		if(!funciones.containsKey(fecha)){
			throw new RuntimeException("Error: La fecha no concuerda con las fechas registradas del espectaculo.");
		}
		Funcion funcion = funciones.get(fecha);
		return funcion.costoEntrada();
	}
	
	//Dada una fecha y un sector, devuelve el valor de la entrada correspondiente a la funcion en esa fecha y al sector elegido.
	public double costoEntrada(LocalDate fecha, String sector) {
		if(!funciones.containsKey(fecha)){
			throw new RuntimeException("Error: La fecha no concuerda con las fechas registradas del espectaculo.");
		}
		Funcion funcion = funciones.get(fecha);
		return funcion.costoEntrada(sector);
	}
	
	//Devuelve el total recaudado por el Espectaculo.
	public double totalRecaudado() {
		double totalRecaudado = 0;
		for(Double reucaudado : RecaudadoPorSede.values()) {
			totalRecaudado+= reucaudado;
		}
		return totalRecaudado;
	}
	
	//Devuelve el total recaudado por el Espectaculo en un sector especifico
	public double totalRecaudado(String sede) {
		return RecaudadoPorSede.get(sede);
	}
	
	//Imprime la informacion del Espectaculo.
	public String toString() {
		return String.format("%s - Cantidad de Funciones: %d - Total Recaudado %.2f", nombre, funciones.size(), totalRecaudado());
	}
}
