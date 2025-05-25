package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Ticketek implements ITicketek {
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Espectaculo> espectaculos;
	private HashMap<String, Sede> sedes;
	private HashMap<String, Usuario> usuariosDeEntrada;
	
	Ticketek(){
		this.usuarios = new HashMap<String, Usuario>();
		this.espectaculos = new HashMap<String, Espectaculo>();
		this.sedes = new HashMap<String, Sede>();
		this.usuariosDeEntrada = new HashMap<String, Usuario>();
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		if(sedes.containsKey(nombre)) {
			throw new RuntimeException("Error: El nombre de la sede ya esta registrado");
		}
		if(capacidadMaxima <=0) {
			throw new RuntimeException("Error: la capacidad maxima no puede ser negativa o 0.");
		}
		if(direccion == null) {
			throw new RuntimeException("Error: la dirección no puede estar vacía");
		}
		Sede sede = new Estadio(nombre,direccion,capacidadMaxima);
		sedes.put(nombre, sede);
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		if(sedes.containsKey(nombre)) {
			throw new RuntimeException("Error: El nombre de la sede ya esta registrado");
		}
		if(capacidadMaxima <=0) {
			throw new RuntimeException("Error: la capacidad maxima no puede ser negativa o 0.");
		}
		if(direccion == null) {
			throw new RuntimeException("Error: la dirección no puede estar vacía");
		}
	    if(asientosPorFila <= 0) {
	        throw new RuntimeException("Error: Los asientos por fila deben ser mayor a 0");
	    }
	    if(porcentajeAdicional == null) {
	        throw new RuntimeException("Error: El porcentaje adicional no puede ser nulo");
	    }
		Sede sede = new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, sede);
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		if(sedes.containsKey(nombre)) {
			throw new RuntimeException("Error: El nombre de la sede ya esta registrado");
		}
		if(capacidadMaxima <=0) {
			throw new RuntimeException("Error: la capacidad maxima no puede ser negativa o 0.");
		}
		if(direccion == null) {
			throw new RuntimeException("Error: la dirección no puede estar vacía");
		}
	    if(cantidadPuestos < 0) {
	        throw new RuntimeException("Error: La cantidad de puestos no puede ser negativa");
	    }
	    if(asientosPorFila <= 0) {
	        throw new RuntimeException("Error: Los asientos por fila deben ser mayor a 0");
	    }
	    if(porcentajeAdicional == null) {
	        throw new RuntimeException("Error: El porcentaje adicional no puede ser nulo");
	    }
		Sede sede = new MiniEstadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, sede);
	}

	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		if (email == null) {
			throw new RuntimeException("Error: El email no puede estar vacío");
		}
		if(usuarios.containsKey(email)) {
	        throw new RuntimeException("Error: Ya existe un usuario registrado con ese email");
	    }
		if(nombre == null) {
	        throw new RuntimeException("Error: El nombre no puede estar vacío");
	    }
		if(apellido == null) {
		    throw new RuntimeException("Error: El apellido no puede estar vacío");
	    }
		if(contrasenia == null) {
	        throw new RuntimeException("Error: La contraseña no puede estar vacía");
	    }
		if(!email.contains("@") || !email.contains(".")) {
		    throw new RuntimeException("Error: El formato del email no es válido");
		}
	    Usuario usuario = new Usuario(email, nombre, apellido, contrasenia);
	    usuarios.put(email, usuario);
		
	}

	@Override
	public void registrarEspectaculo(String nombre) {
		if(espectaculos.containsKey(nombre)) {
			throw new RuntimeException("Error: El nombre del espectaculo ya esta registrado");
		}
		Espectaculo espectaculo = new Espectaculo(nombre);
		espectaculos.put(nombre, espectaculo);
	}

	@Override
	public void agregarFuncion(String nombreEspectaculo, String fechaString, String sedeString, double precioBase) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");
		
		if(!sedes.containsKey(sedeString)) 
			throw new RuntimeException("Error: El nombre de la sede no esta registrado.");
		Fecha fechaObjeto = new Fecha(fechaString);
		LocalDate fecha = fechaObjeto.obtenerFecha();
		Sede sede = sedes.get(sedeString);
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		espectaculo.agregarFuncion(fecha, sede, precioBase);
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fechaString, String email, String contrasenia,
			int cantidadEntradas) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");
		
		if(!usuarios.containsKey(email)) 
			throw new RuntimeException("Error: El email del usuario no esta registrado.");
		
		Usuario usuario = usuarios.get(email);
		if(!usuario.autenticar(contrasenia))
			throw new RuntimeException("Error: La contraseña es incorrecta.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		Fecha fechaObjeto = new Fecha(fechaString);
		LocalDate fecha = fechaObjeto.obtenerFecha();
		LinkedList<IEntrada> entradas = espectaculo.venderEntrada(nombreEspectaculo, fecha, cantidadEntradas);
		for(IEntrada entrada : entradas) {
			usuario.agregarEntrada(entrada);
			usuariosDeEntrada.put(entrada.getCodigo(), usuario);
		}
		
		
		return entradas;
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fechaString, String email, String contrasenia,
			String sector, int[] asientos) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");

		if(!usuarios.containsKey(email)) 
			throw new RuntimeException("Error: El email del usuario no esta registrado.");
		
		Usuario usuario = usuarios.get(email);
		
		if(!usuario.autenticar(contrasenia))
			throw new RuntimeException("Error: La contraseña es incorrecta.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		Fecha fechaObjeto = new Fecha(fechaString);
		LocalDate fecha = fechaObjeto.obtenerFecha();
		LinkedList<IEntrada> entradas = espectaculo.venderEntrada(nombreEspectaculo, fecha, sector, asientos);
		for(IEntrada entrada : entradas) {
			usuario.agregarEntrada(entrada);
			usuariosDeEntrada.put(entrada.getCodigo(), usuario);
		}
		
		return entradas;
	}

	@Override
	public String listarFunciones(String nombreEspectaculo) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.listarFunciones();
	}

	@Override
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.listarEntradas();
	}

	@Override
	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		if(email == null) 
	        throw new RuntimeException("Error: El email no puede estar vacío");
	    
	    
	    // Validación de contraseña
	    if(contrasenia == null) 
	        throw new RuntimeException("Error: La contraseña no puede estar vacía");
	    
	    
	    Usuario usuario = usuarios.get(email);
	    
	    if(usuario == null) 
	        throw new RuntimeException("Error: No existe un usuario registrado con ese email");
	    
	    
	    if(!usuario.autenticar(contrasenia)) 
	        throw new RuntimeException("Error: Contraseña incorrecta");
	    
	    return usuario.listarEntradasFuturas();
	}

	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
	    if(email == null) {
	        throw new RuntimeException("Error: El email no puede estar vacío");
	    }
	    
	    // Validación de contraseña
	    if(contrasenia == null) {
	        throw new RuntimeException("Error: La contraseña no puede estar vacía");
	    }
	    
	    Usuario usuario = usuarios.get(email);
	    
	    if(usuario == null) {
	        throw new RuntimeException("Error: No existe un usuario registrado con ese email");
	    }
	    
	    if(!usuario.autenticar(contrasenia)) {
	        throw new RuntimeException("Error: Contraseña incorrecta");
	    }
	    
	    return usuario.listarEntradas();
	}

	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		if(entrada == null) {
	        throw new RuntimeException("Error: La entrada no puede ser nula");
	    }
	    if(contrasenia == null) {
	        throw new RuntimeException("Error: La contraseña no puede estar vacía");
	    }
	    
	    Usuario usuario = usuariosDeEntrada.get(entrada.getCodigo());
	    if(usuario == null) 
	        throw new RuntimeException("Error: No se encontró un usuario asociado a esta entrada");
	    
	    if(!usuario.autenticar(contrasenia))
			throw new RuntimeException("Error: La contraseña es incorrecta.");
	    
	    
	    boolean anulacionExitosa = usuario.anularEntrada(entrada);
	    
	    if(anulacionExitosa) {
	        usuariosDeEntrada.remove(entrada.getCodigo());
	        Espectaculo espectaculo = espectaculos.get(entrada.nombreEspectaculo());
	        if(espectaculo != null) {
	            espectaculo.anularEntrada(entrada.obtenerFecha(), entrada.getCodigo(), entrada.obtenerSector(), entrada.obtenerAsiento());
	        }
	    }
	    
	    return anulacionExitosa;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento) {
		if(entrada == null) {
	        throw new RuntimeException("Error: La entrada no puede ser nula");
	    }
	    if(contrasenia == null || contrasenia.trim().isEmpty()) {
	        throw new RuntimeException("Error: La contraseña no puede estar vacía");
	    }
	    if(fecha == null || fecha.trim().isEmpty()) {
	        throw new RuntimeException("Error: La fecha no puede estar vacía");
	    }
	    if(sector == null || sector.trim().isEmpty()) {
	        throw new RuntimeException("Error: El sector no puede estar vacío");
	    }
	    if(asiento <= 0) {
	        throw new RuntimeException("Error: El número de asiento debe ser mayor a 0");
	    }

	    Usuario usuario = usuariosDeEntrada.get(entrada.getCodigo());
	    
	    if(usuario == null) {
	        throw new RuntimeException("Error: No se encontró un usuario asociado a esta entrada");
	    }
	    
	    if(!usuario.autenticar(contrasenia)) {
	        throw new RuntimeException("Error: Contraseña incorrecta");
	    }
	    
	    // Obtener el espectáculo
	    Espectaculo espectaculo = espectaculos.get(entrada.nombreEspectaculo());
	    if(espectaculo == null) {
	        throw new RuntimeException("Error: No se encontró el espectáculo asociado a la entrada");
	    }
	    
	    // Anular la entrada actual
	    boolean anulacionExitosa = anularEntrada(entrada, contrasenia);
	    if(!anulacionExitosa) {
	        throw new RuntimeException("Error: No se pudo anular la entrada original");
	    }
	    try {
	        // Crear nueva entrada con el nuevo sector y asiento
	        Fecha fechaObjeto = new Fecha(fecha);
	        LocalDate fechaNueva = fechaObjeto.obtenerFecha();
	        
	        List<IEntrada> nuevasEntradas = espectaculo.venderEntrada(entrada.nombreEspectaculo(), fechaNueva, sector, new int[]{asiento});
	        
	        if(nuevasEntradas.isEmpty()) {
	            throw new RuntimeException("Error: No se pudo crear la nueva entrada");
	        }
	        
	        IEntrada nuevaEntrada = nuevasEntradas.get(0);
	        usuario.agregarEntrada(nuevaEntrada);
	        usuariosDeEntrada.put(nuevaEntrada.getCodigo(), usuario);
	        
	        return nuevaEntrada;
	        
	    } catch(RuntimeException e) {
	        // Si falla la creación de la nueva entrada, intentar restaurar la original
	        try {
	            usuario.agregarEntrada(entrada);
	            usuariosDeEntrada.put(entrada.getCodigo(), usuario);
	        } catch(Exception restoreException) {
	            // Si no se puede restaurar, lanzar el error original
	        }
	        throw e;
	    }
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha) {
		if(entrada == null) {
	        throw new RuntimeException("Error: La entrada no puede ser nula");
	    }
	    if(contrasenia == null) {
	        throw new RuntimeException("Error: La contraseña no puede estar vacía");
	    }
	    if(fecha == null) {
	        throw new RuntimeException("Error: La fecha no puede estar vacía");
	    }

	    Usuario usuario = usuariosDeEntrada.get(entrada.getCodigo());
	    
	    if(usuario == null) {
	        throw new RuntimeException("Error: No se encontró un usuario asociado a esta entrada");
	    }
	    
	    if(!usuario.autenticar(contrasenia)) {
	        throw new RuntimeException("Error: Contraseña incorrecta");
	    }
	    
	    // Obtener el espectáculo
	    Espectaculo espectaculo = espectaculos.get(entrada.nombreEspectaculo());
	    if(espectaculo == null) {
	        throw new RuntimeException("Error: No se encontró el espectáculo asociado a la entrada");
	    }
	    
	    // Anular la entrada actual
	    boolean anulacionExitosa = anularEntrada(entrada, contrasenia);
	    if(!anulacionExitosa) {
	        throw new RuntimeException("Error: No se pudo anular la entrada original");
	    }
	    try {
	        // Crear nueva entrada para estadio (solo cambio de fecha)
	        Fecha fechaObjeto = new Fecha(fecha);
	        LocalDate fechaNueva = fechaObjeto.obtenerFecha();
	        
	        List<IEntrada> nuevasEntradas = espectaculo.venderEntrada(entrada.nombreEspectaculo(), fechaNueva, 1);
	        
	        if(nuevasEntradas.isEmpty()) {
	            throw new RuntimeException("Error: No se pudo crear la nueva entrada");
	        }
	        
	        IEntrada nuevaEntrada = nuevasEntradas.get(0);
	        usuario.agregarEntrada(nuevaEntrada);
	        usuariosDeEntrada.put(nuevaEntrada.getCodigo(), usuario);
	        
	        return nuevaEntrada;
	        
	    } catch(RuntimeException e) {
	        // Si falla la creación de la nueva entrada, intentar restaurar la original
	        try {
	            usuario.agregarEntrada(entrada);
	            usuariosDeEntrada.put(entrada.getCodigo(), usuario);
	        } catch(Exception restoreException) {
	            // Si no se puede restaurar, lanzar el error original
	        }
	        throw e;
	    }
	    
	}


	@Override
	public double costoEntrada(String nombreEspectaculo, String fechaString ) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");
		
		
		Fecha fechaObjeto = new Fecha(fechaString);
		LocalDate fecha = fechaObjeto.obtenerFecha();
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.costoEntrada(fecha);
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fechaString, String sector) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");
		
		
		Fecha fechaObjeto = new Fecha(fechaString);
		LocalDate fecha = fechaObjeto.obtenerFecha();
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.costoEntrada(fecha,sector);
	}

	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.totalRecaudado();
	}

	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");
		
		if(!sedes.containsKey(nombreSede))
			throw new RuntimeException("Error: El nombre de la sede no esta registrado.");
		
		Sede sede = sedes.get(nombreSede);
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.totalRecaudado(sede);
	}
}
