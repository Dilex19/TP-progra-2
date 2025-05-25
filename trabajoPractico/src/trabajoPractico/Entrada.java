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
	
	Entrada(String codigo, String nombreEspectaculo, LocalDate fecha, String nombreSede,String sector, int ubicacion, int fila, double precioEntrada){
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.sector = sector;
		this.asiento = ubicacion;
		this.precioEntrada = precioEntrada;
		this.fecha =fecha;
		this.fila = fila;
		this.nombreSede = nombreSede;
	}
	
	Entrada(String codigo, String nombreEspectaculo, LocalDate fecha2, String nombreSede,double precioEntrada){
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.fecha = fecha2;
		this.precioEntrada = precioEntrada;
		this.nombreSede = nombreSede;
	}
	@Override
	public String getCodigo() {
		return codigo;
	}
	
	@Override
	public double precio() {
		return precioEntrada;
	}
	public String getFecha() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
		return fecha.format(formatter);
	}

	@Override
	public String ubicacion() {
		if(sector == null) {
			return "CAMPO";
	    } else {
	        return String.format("%s f:%s a:%d", sector, fila, asiento);
	    }
	}
	
	@Override
	public int obtenerAsiento() {
		return asiento;
	}

	@Override
	public String obtenerSector() {
		// TODO Auto-generated method stub
		return sector;
	}

	@Override
	public String nombreEspectaculo() {
		return nombreEspectaculo;
	}

	@Override
	public LocalDate obtenerFecha() {
		return fecha;
	}
	@Override
	public String toString() {
		String formato = "%s - %s - %s - %s - %s";
		if(sector == null) {
			formato = "%s - %s - %s - P - %s - %s";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    return String.format(formato,
		            codigo,
		            nombreEspectaculo,
		            fecha.format(formatter),
		            nombreSede,
		            ubicacion());
	}
}
