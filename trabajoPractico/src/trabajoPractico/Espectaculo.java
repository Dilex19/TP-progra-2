package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Espectaculo {
	private String nombre;
	private Map<Fecha,Funcion> funciones;
	private Map<String, Double> RecaudadoPorSede; 
	

	Espectaculo(String nombre){
		if(nombre == null || nombre.length()<2)
			throw new RuntimeException("Error: El nombre del espectaculo no puede tener menos de 2 caracteres.");
		this.nombre =  nombre;
		this.funciones = new  TreeMap<Fecha,Funcion>();
		this.RecaudadoPorSede = new TreeMap<String, Double>();
	}
	
	
	public LinkedList<IEntrada> venderEntrada(String nombreEspectaculo, Fecha fecha, int cantEspacios){
		validarFechaDeFuncion(fecha);
		
		validarExistenciaEspectaculo(nombreEspectaculo);
		
		if(cantEspacios <1) 
			throw new RuntimeException("Error: La cantidad de asientos no puede ser menor a 1");
		
		Funcion funcion = funciones.get(fecha);
		LinkedList<IEntrada> entradas= funcion.venderEntrada(nombreEspectaculo, cantEspacios);
		Entrada primeraEntradaObjeto = (Entrada) entradas.get(0);
		
		agregarValorDeEntradasALoRecaudado(entradas,primeraEntradaObjeto.obtenerSede());
		return entradas;
	}
	
	
	public LinkedList<IEntrada> venderEntrada(String nombreEspectaculo, Fecha fecha, String sector, int[] asientos){
		
		validarFechaDeFuncion(fecha);
		
		validarExistenciaEspectaculo(nombreEspectaculo);
		
		if(sector.isEmpty()) 
			throw new RuntimeException("Error: El sector no puede estar vacio.");
		
		if(asientos.length == 0) 
			throw new RuntimeException("Error: La longitud de asientos no puede ser igual a 0");
		
		Funcion funcion = funciones.get(fecha);
		LinkedList<IEntrada> entradas= funcion.venderEntrada(nombreEspectaculo,sector, asientos);
		Entrada primeraEntradaObjeto = (Entrada) entradas.get(0);	
		agregarValorDeEntradasALoRecaudado(entradas,primeraEntradaObjeto.obtenerSede());
		return entradas;
	}
	
	
	
	private	 void agregarValorDeEntradasALoRecaudado(LinkedList<IEntrada> entradas,String sede) {
		for(IEntrada entrada : entradas) {
			RecaudadoPorSede.merge(sede, entrada.precio(), Double::sum);
		}
	}
	
	
	public void agregarFuncion(Fecha fecha, Sede sede, double precioBase) {
		if(funciones.containsKey(fecha)) 
			throw new RuntimeException("Error: El espectaculo ya tiene una funcion registrada en esa fecha.");
		
		Funcion nuevaFuncion = new Funcion(fecha, sede, precioBase);
		funciones.put(fecha, nuevaFuncion);
		if(!RecaudadoPorSede.containsKey(sede.nombre())) {
			RecaudadoPorSede.put(sede.nombre(), 0.0);
		}
	}
	
	
	
	public String listarFunciones() {
		StringBuilder listaFunciones = new StringBuilder();
		for(Funcion funcion : funciones.values()) {
			listaFunciones.append(funcion.toString());
			listaFunciones.append("\n");
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
	
	
	public void anularEntrada(Fecha fechaEntrada, String codigoEntrada, String sectorEntrada, int asientoEntrada) {
		
		validarFechaDeFuncion(fechaEntrada);
		
		Funcion funcion = funciones.get(fechaEntrada);
		funcion.anularEntrada(codigoEntrada, sectorEntrada, asientoEntrada);
	}

	

	public double costoEntrada(Fecha fecha) {
		
		validarFechaDeFuncion(fecha);
		
		Funcion funcion = funciones.get(fecha);
		return funcion.costoEntrada();
	}
	
	
	public double costoEntrada(Fecha fecha, String sector) {
		validarFechaDeFuncion(fecha);
		Funcion funcion = funciones.get(fecha);
		return funcion.costoEntrada(sector);
	}
	

	public double totalRecaudado() {
		double totalRecaudado = 0;
		for(Double reucaudado : RecaudadoPorSede.values()) {
			totalRecaudado+= reucaudado;
		}
		return totalRecaudado;
	}
	

	public double totalRecaudado(String sede) {
		return RecaudadoPorSede.get(sede);
	}
	
	public boolean puedeVenderEntrada(Fecha fecha, String sector, int asiento) {
		if(!funciones.containsKey(fecha)) {
			return false;
		}
		Funcion funcion = funciones.get(fecha);
		return funcion.asientoDisponible(sector, asiento);
	}
	
	public boolean puedeVenderEntrada(Fecha fecha, int cantidad) {
		if(!funciones.containsKey(fecha)) {
			return false;
		}
		
		Funcion funcion = funciones.get(fecha);
		return funcion.cantidadPuestosDisponibles() >= cantidad;
	}
	
	

	public String toString() {
		return String.format("%s - Cantidad de Funciones: %d - Total Recaudado %.2f", nombre, funciones.size(), totalRecaudado());
	}
	
	private void validarExistenciaEspectaculo(String nombreEspectaculo) throws RuntimeException{
		if(nombreEspectaculo == null || nombreEspectaculo.isEmpty())
			throw new RuntimeException("Error: El nombre del espectaculo no puede ser nulo ni estar vacio.");
	}
	
	private void validarFechaDeFuncion(Fecha fecha) {
		if(!funciones.containsKey(fecha)) 
			throw new RuntimeException("Error: La fecha no concuerda con las fechas de las funciones registradas del espectaculo.");
	}
}
