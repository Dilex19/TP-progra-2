package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Fecha implements Comparable<Fecha>{
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
	
	public boolean esAntes(LocalDate nuevaFecha) {
		return this.fecha.isBefore(nuevaFecha);
	}
	
	@Override
    public int compareTo(Fecha otra) {
        return this.fecha.compareTo(otra.fecha);
    }
	
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        String fechaStr = fecha.format(formatter);
        return fechaStr;
	}
}

