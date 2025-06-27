package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Entrada implements IEntrada{
	private String codigo;
	private String nombreEspectaculo;
	private Fecha fecha;
	private String nombreSede;
	private String sector;
	private int fila;
	private int asiento;
	private double precioEntrada;
	
	
	
	Entrada(String codigo, String nombreEspectaculo, Fecha fecha, String nombreSede,String sector, int asiento, int fila, double precioEntrada){
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
		if(asiento <= 0)
			throw new RuntimeException("Error: El numero del asiento no puede ser negativo o cero.");
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

	
	Entrada(String codigo, String nombreEspectaculo, Fecha fecha, String nombreSede,double precioEntrada){
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
	

	public String getCodigo() {
		return codigo;
	}
	

	@Override
	public double precio() {
		return precioEntrada;
	}

	@Override
	public String ubicacion() {
		if(sector == "CAMPO") {
			return "CAMPO";
	    } else {
	        return String.format("%s f:%s a:%d", sector, fila, asiento);
	    }
	}
	
	

	public int obtenerAsiento() {
		return asiento;
	}

	

	public String obtenerSector() {
		return sector;
	}

	public String obtenerSede() {
		return nombreSede;
	}
	

	public String nombreEspectaculo() {
		return nombreEspectaculo;
	}

	public Fecha obtenerFecha() {
		return fecha;
	}
	@Override
	public String toString() {
		String formato = "%s - %s - %s - %s - %s";
		if(this.fecha.esAntes(LocalDate.now())) {
			formato = "%s - %s - %s - P - %s - %s";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
	    return String.format(formato,
		            codigo,
		            nombreEspectaculo,
		            fecha.toString(),
		            nombreSede,
		            ubicacion());
	}
}
