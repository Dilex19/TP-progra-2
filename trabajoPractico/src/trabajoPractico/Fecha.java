package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Fecha {
	private LocalDate fecha;
	
	Fecha(String fecha){
		// Definir el formato de entrada (el String tiene día/mes/año)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Convertir String a LocalDate
        this.fecha = LocalDate.parse(fecha, formatter);
	}
	
	public boolean estaAntes(Fecha f) {
		return this.fecha.isBefore(f.fecha);
	}
	
	public boolean estaDespues(Fecha f) {
		return this.fecha.isAfter(f.fecha);
	}
	
	public boolean esLaMisma(Fecha f) {
		return this.fecha.isEqual(f.fecha);
	}
}
