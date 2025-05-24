package trabajoPractico;

import java.time.LocalDate;

public class Entrada implements IEntrada{
	private String codigo;
	private String nombreEspectaculo;
	private LocalDate fecha;
	private String sector;
	private String fila;
	private int asiento;
	private double precioEntrada;
	
	Entrada(String codigo, String nombreEspectaculo, LocalDate fecha, String sector, int ubicacion, int fila, double precioEntrada){
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.sector = sector;
		this.asiento = ubicacion;
		this.precioEntrada = precioEntrada;
		this.fecha =fecha;
	}
	
	Entrada(String codigo, String nombreEspectaculo, LocalDate fecha2, double precioEntrada){
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.fecha = fecha2;
		this.precioEntrada = precioEntrada;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public int asiento() {
		return asiento;
	}
	
	public String obtenerCodigo() {
		return codigo;
	}

	public String obtenerSector() {
		// TODO Auto-generated method stub
		return sector;
	}
	
	public LocalDate obtenerFecha() {
		return fecha;
	}
}
