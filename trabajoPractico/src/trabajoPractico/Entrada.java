package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Entrada implements IEntrada{
	private String codigo;
	private String nombreEspectaculo;
	private LocalDate fecha;
	private String sector;
	private int fila;
	private int asiento;
	private double precioEntrada;
	
	Entrada(String codigo, String nombreEspectaculo, LocalDate fecha, String sector, int ubicacion, int fila, double precioEntrada){
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.sector = sector;
		this.asiento = ubicacion;
		this.precioEntrada = precioEntrada;
		this.fecha =fecha;
		this.fila = fila;
	}
	
	Entrada(String codigo, String nombreEspectaculo, LocalDate fecha2, double precioEntrada){
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.fecha = fecha2;
		this.precioEntrada = precioEntrada;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int obtenerAsiento() {
		return asiento;
	}

	@Override
	public String obtenerCodigo() {
		return codigo;
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
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    if(sector == null) {
	    	String entradaString = String.format("Entrada [Código: %s, Espectáculo: %s, Fecha: %s, Precio: %.2f€]",
		            codigo,
		            nombreEspectaculo,
		            fecha.format(formatter),
		            precioEntrada);
	    	sb.append(entradaString);
	    } else { 
	    	String entradaString = String.format("Entrada [Código: %s, Espectáculo: %s, Fecha: %s, Sector: %s, Fila: %s, Asiento: %d, Precio: %.2f€]",
	            codigo,
	            nombreEspectaculo,
	            fecha.format(formatter),
	            sector,
	            fila,
	            asiento,
	            precioEntrada);
	    	sb.append(entradaString);
	    }
	    
	    LocalDate fechaActual = LocalDate.now();
		if(this.fecha.isAfter(fechaActual)) {
			String entradaStringFinal = sb.toString();
			return entradaStringFinal;
		}
		sb.insert(0, " P - ");
		String entradaStringFinal = sb.toString();
		return entradaStringFinal;
	}
}
