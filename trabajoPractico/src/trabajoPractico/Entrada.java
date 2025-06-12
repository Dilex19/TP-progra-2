package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Entrada implements IEntrada{
	private String codigo;
	private String nombreEspectaculo;
	private LocalDate fecha;
	private String nombreSede;
	private String sector;
	private int fila;
	private int asiento;
	private double precioEntrada;
	
	//Constructor de Entrada que tenga sectores que no sea CAMPO.
	Entrada(String codigo, String nombreEspectaculo, LocalDate fecha, String nombreSede,String sector, int asiento, int fila, double precioEntrada){
		if(codigo == null || codigo.isEmpty()) 
				throw new RuntimeException("Error: El codigo de la entrada no puede ser null o vacio.");
		if(nombreEspectaculo == null || nombreEspectaculo.isEmpty()) 
			throw new RuntimeException("Error: El nombre del Espectaculo no puede ser null o vacio.");
		if(fecha == null) 
			throw new RuntimeException("Error: La fecha no puede ser null.");
		if(nombreSede == null || nombreSede.isEmpty()) 
			throw new RuntimeException("Error: El nombre de la sede no puede ser null o vacio.");
		if(sector == null || sector.isEmpty()) 
			throw new RuntimeException("Error: El nombre del sector no puede ser null o vacio.");
		if(asiento < 0)
			throw new RuntimeException("Error: El numero del asiento no puede ser negativo");
		if(fila <0)
			throw new RuntimeException("Error: El numero de la fila no puede ser negativa");
		if(precioEntrada <1)
			throw new RuntimeException("Error: El precio de la entrada no puede ser negativo o cero");
		
		
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.sector = sector;
		this.asiento = asiento;
		this.precioEntrada = precioEntrada;
		this.fecha =fecha;
		this.fila = fila;
		this.nombreSede = nombreSede;
	}
	//Constructor de entrada con solo sector CAMPO
	Entrada(String codigo, String nombreEspectaculo, LocalDate fecha, String nombreSede,double precioEntrada){
		if(codigo == null || codigo.isEmpty()) 
			throw new RuntimeException("Error: El codigo de la entrada no puede ser null o vacio.");
		if(nombreEspectaculo == null || nombreEspectaculo.isEmpty()) 
			throw new RuntimeException("Error: El nombre del Espectaculo no puede ser null o vacio.");
		if(fecha == null) 
			throw new RuntimeException("Error: La fecha no puede ser null.");
		if(nombreSede == null || nombreSede.isEmpty()) 
			throw new RuntimeException("Error: El nombre de la sede no puede ser null o vacio.");
		if(precioEntrada <1)
			throw new RuntimeException("Error: El precio de la entrada no puede ser negativo o cero");
		
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.fecha = fecha;
		this.precioEntrada = precioEntrada;
		this.nombreSede = nombreSede;
		this.sector = "CAMPO";
	}
	
	//Devuelve el codigo.
	public String getCodigo() {
		return codigo;
	}
	
	//Devuelve el precio.
	@Override
	public double precio() {
		return precioEntrada;
	}

	//Devuelve la ubicacion.
	@Override
	public String ubicacion() {
		if(sector == "CAMPO") {
			return "CAMPO";
	    } else {
	        return String.format("%s f:%s a:%d", sector, fila, asiento);
	    }
	}
	
	
	//Devuelve el numero de Asiento.
	public int obtenerAsiento() {
		return asiento;
	}

	
	//Devuelve el nombre del Sector
	public String obtenerSector() {
		return sector;
	}
	
	//Devuelve el nombre de la sede
	public String obtenerSede() {
		return nombreSede;
	}
	
	//Devuelve el nombre del Espectaculo
	public String nombreEspectaculo() {
		return nombreEspectaculo;
	}

	//devuelve la fecha.
	public LocalDate obtenerFecha() {
		return fecha;
	}
	@Override
	public String toString() {
		String formato = "%s - %s - %s - %s - %s";
		if(this.fecha.isBefore(LocalDate.now())) {
			formato = "%s - %s - %s - P - %s - %s";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
	    return String.format(formato,
		            codigo,
		            nombreEspectaculo,
		            fecha.format(formatter),
		            nombreSede,
		            ubicacion());
	}
}
