package trabajoPractico;

import java.time.LocalDate;

public class Entrada implements IEntrada{
	private String codigo;
	private String nombreEspectaculo;
	private Fecha fecha;
	private String sector;
	private String fila;
	private int asiento;
	private double precioEntrada;
	
	Entrada(String codigo, String nombreEspectaculo, Fecha fecha, String sector, int ubicacion, int fila, double precioEntrada){
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.sector = sector;
		this.asiento = ubicacion;
		this.precioEntrada = precioEntrada;
		this.fecha =fecha;
	}
	
	Entrada(String codigo, String nombreEspectaculo, Fecha fecha, double precioEntrada){
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.fecha = fecha;
		this.precioEntrada = precioEntrada;
	}
	
	@Override
	public double precio() {
		// TODO Auto-generated method stub
		return 0;
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
}
