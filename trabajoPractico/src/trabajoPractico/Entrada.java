package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	public int asiento() {
		return asiento;
	}
<<<<<<< HEAD
	public String getNombreEspectaculo() {
		return nombreEspectaculo;
	}
	
=======
	@Override
>>>>>>> df217516433bc5b3000524f5feea94e157f953f8
	public String obtenerCodigo() {
		return codigo;
	}
	@Override
	public String obtenerSector() {
		// TODO Auto-generated method stub
		return sector;
	}
	@Override
	public LocalDate obtenerFecha() {
		return fecha;
	}
	@Override
	public String nombreEspectaculo() {
		return nombreEspectaculo;
	}
}
