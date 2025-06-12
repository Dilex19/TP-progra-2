package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Fecha {
	private LocalDate fecha;
	
	public Fecha(String fechaString) {
		if (fechaString == null || fechaString.trim().isEmpty()) {
	        throw new RuntimeException("La fecha no puede ser nula o vacía");
	    }
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
		try {
			this.fecha = LocalDate.parse(fechaString, formatter);
		}catch (Exception e) {
			throw new IllegalArgumentException("Formato de fecha inválido. Use dd/MM/yy.");
		}
	}
}

