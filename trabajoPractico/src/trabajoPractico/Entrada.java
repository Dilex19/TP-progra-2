package trabajoPractico;

import java.time.LocalDate;

public class Entrada implements IEntrada{
	private String codigo;
	private String nombreEspectaculo;
	private Fecha fecha;
	private String sector;
	private int ubicacion;
	private double precioEntrada;
	
	Entrada(String codigo, String nombreEspectaculo, Fecha fecha, String sector, int ubicacion, double precioEntrada){
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.sector = sector;
		this.ubicacion = ubicacion;
		this.precioEntrada = precioEntrada;
		this.fecha =fecha;
	}
	
	Entrada(String codigo, String nombreEspectaculo, Fecha fecha){
		this.codigo = codigo;
		this.nombreEspectaculo = nombreEspectaculo;
		this.fecha = fecha;
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

}
