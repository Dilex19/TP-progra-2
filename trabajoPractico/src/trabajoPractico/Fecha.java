package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Fecha {
	private LocalDate fecha;
	
	public Fecha(String fechaStr) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
	    this.fecha = LocalDate.parse(fechaStr, formatter);
	}
	
	public LocalDate obtenerFecha() {
		return fecha;
	}
}