package trabajoPractico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Ticketek implements ITicketek {
	private Map<String, Usuario> usuarios;
	private Map<String, Espectaculo> espectaculos;
	private Map<String, Sede> sedes;
	private Map<String, Usuario> usuariosDeEntrada;
	
	Ticketek(){
		this.usuarios = new HashMap<String, Usuario>();
		this.espectaculos = new HashMap<String, Espectaculo>();
		this.sedes = new HashMap<String, Sede>();
		this.usuariosDeEntrada = new HashMap<String, Usuario>();
	}

	//Registra una sede sin sectores.
	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		
		validarExistenciaSede(nombre);
		
		Sede sede = new Estadio(nombre,direccion,capacidadMaxima);
		sedes.put(nombre, sede);
	}

	//Registra una sede con múltiples sectores.
	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		
		validarExistenciaSede(nombre);
		
		Sede sede = new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, sede);
	}

	//Registra una sede con servicios adicionales.
	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		
		validarExistenciaSede(nombre);
		
		Sede sede = new MiniEstadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, sede);
	}

	//Registra un usuario.
	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		if(usuarios.containsKey(email)) {
	        throw new RuntimeException("Error: Ya existe un usuario registrado con ese email");
	    }
	    Usuario usuario = new Usuario(email, nombre, apellido, contrasenia);
	    usuarios.put(email, usuario);
		
	}

	//Registra un espectáculo.
	@Override
	public void registrarEspectaculo(String nombre) {
		
		validarExistenciaEspectaculo(nombre);
		
		Espectaculo espectaculo = new Espectaculo(nombre);
		espectaculos.put(nombre, espectaculo);
	}

	//Agrega una función.
	@Override
	public void agregarFuncion(String nombreEspectaculo, String fechaString, String sedeString, double precioBase) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		if(!sedes.containsKey(sedeString)) 
			throw new RuntimeException("Error: El nombre de la sede no esta registrado.");
		
		Fecha fecha = new Fecha(fechaString);
		Sede sede = sedes.get(sedeString);
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		espectaculo.agregarFuncion(fecha, sede, precioBase);
	}

	// Asocia entradas al usuario.
	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fechaString, String email, String contrasenia,
			int cantidadEntradas) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		if(!usuarios.containsKey(email)) 
			throw new RuntimeException("Error: El email del usuario no esta registrado.");
		
		Usuario usuario = usuarios.get(email);
		if(!usuario.autenticar(contrasenia))
			throw new RuntimeException("Error: La contraseña es incorrecta.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		Fecha fecha = new Fecha(fechaString);
		LinkedList<IEntrada> entradas = espectaculo.venderEntrada(nombreEspectaculo, fecha, cantidadEntradas);
		for(IEntrada entrada : entradas) {
			Entrada entradaObjeto = (Entrada) entrada;
			usuario.agregarEntrada(entradaObjeto);
			usuariosDeEntrada.put(entradaObjeto.getCodigo(), usuario);
		}
		
		
		return entradas;
	}

	// Similar al anterior pero con selección de asientos.
	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fechaString, String email, String contrasenia,
			String sector, int[] asientos) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);

		if(!usuarios.containsKey(email)) 
			throw new RuntimeException("Error: El email del usuario no esta registrado.");
		
		Usuario usuario = usuarios.get(email);
		
		if(!usuario.autenticar(contrasenia))
			throw new RuntimeException("Error: La contraseña es incorrecta.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		Fecha fecha = new Fecha(fechaString);
		LinkedList<IEntrada> entradas = espectaculo.venderEntrada(nombreEspectaculo, fecha, sector, asientos);
		for(IEntrada entrada : entradas) {
			Entrada entradaObjeto = (Entrada) entrada;
			usuario.agregarEntrada(entradaObjeto);
			usuariosDeEntrada.put(entradaObjeto.getCodigo(), usuario);
		}
		
		return entradas;
	}
	//Lista las funciones de un espectaculo
	@Override
	public String listarFunciones(String nombreEspectaculo) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.listarFunciones();
	}

	//Lista de entradas de un espectáculo.
	@Override
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.listarEntradas();
	}

	//Lista de entradas futuras compradas por un usuario.
	@Override
	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		
		Usuario usuario = autentificarUsuario(email, contrasenia);
	    
	    return usuario.listarEntradasFuturas();
	}

	//Lista de entradas de un usuario.
	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
		
		Usuario usuario = autentificarUsuario(email, contrasenia);
		
	    return usuario.listarEntradas();
	}

	//Anula una entrada.
	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		if(entrada == null) {
	        throw new RuntimeException("Error: La entrada no puede ser nula");
	    }
	    if(contrasenia == null) {
	        throw new RuntimeException("Error: La contraseña no puede estar vacía");
	    }
	    Entrada entradaObjeto = (Entrada) entrada;
	    Usuario usuario = usuariosDeEntrada.get(entradaObjeto.getCodigo());
	    if(usuario == null) 
	        throw new RuntimeException("Error: No se encontró un usuario asociado a esta entrada");
	    
	    if(!usuario.autenticar(contrasenia))
			throw new RuntimeException("Error: La contraseña es incorrecta.");
	    
	    
	    boolean anulacionExitosa = usuario.anularEntrada(entradaObjeto);
	    
	    if(anulacionExitosa) {
	        usuariosDeEntrada.remove(entradaObjeto.getCodigo());
	        Espectaculo espectaculo = espectaculos.get(entradaObjeto.nombreEspectaculo());
	        if(espectaculo != null) {
	            espectaculo.anularEntrada(entradaObjeto.obtenerFecha(), entradaObjeto.getCodigo(), entradaObjeto.obtenerSector(), entradaObjeto.obtenerAsiento());
	        }
	    }
	    
	    return anulacionExitosa;
	}

	//Cambia una entrada.
	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fechaString, String sector, int asiento) {
		if(entrada == null) {
	        throw new RuntimeException("Error: La entrada no puede ser nula");
	    }
	    if(contrasenia == null || contrasenia.trim().isEmpty()) {
	        throw new RuntimeException("Error: La contraseña no puede estar vacía");
	    }
	    if(fechaString == null || fechaString.trim().isEmpty()) {
	        throw new RuntimeException("Error: La fecha no puede estar vacía");
	    }
	    if(sector == null || sector.trim().isEmpty()) {
	        throw new RuntimeException("Error: El sector no puede estar vacío");
	    }
	    if(asiento <= 0) {
	        throw new RuntimeException("Error: El número de asiento debe ser mayor a 0");
	    }
	    
	    Entrada entradaObjeto = (Entrada) entrada;
	    
	    Usuario usuario = usuariosDeEntrada.get(entradaObjeto.getCodigo());
	    
	    if(usuario == null) {
	        throw new RuntimeException("Error: No se encontró un usuario asociado a esta entrada");
	    }
	    
	    if(!usuario.autenticar(contrasenia)) {
	        throw new RuntimeException("Error: Contraseña incorrecta");
	    }
	    
	    // Obtener el espectáculo
	    Espectaculo espectaculo = espectaculos.get(entradaObjeto.nombreEspectaculo());
	    if(espectaculo == null) {
	        throw new RuntimeException("Error: No se encontró el espectáculo asociado a la entrada");
	    }
	    
	    // Anular la entrada actual.
	    boolean anulacionExitosa = anularEntrada(entrada, contrasenia);
	    if(!anulacionExitosa) {
	        throw new RuntimeException("Error: No se pudo anular la entrada original");
	    }
	    try {
	        // Crear nueva entrada con el nuevo sector y asiento
	    	Fecha fechaNueva = new Fecha(fechaString);
	        
	        List<IEntrada> nuevasEntradas = espectaculo.venderEntrada(entradaObjeto.nombreEspectaculo(), fechaNueva, sector, new int[]{asiento});
	        
	        if(nuevasEntradas.isEmpty()) {
	            throw new RuntimeException("Error: No se pudo crear la nueva entrada");
	        }
	        
	        Entrada nuevaEntrada = (Entrada) nuevasEntradas.get(0);
	        usuario.agregarEntrada(nuevaEntrada);
	        usuariosDeEntrada.put(nuevaEntrada.getCodigo(), usuario);
	        
	        return nuevaEntrada;
	        
	    } catch(RuntimeException e) {
	        // Si falla la creación de la nueva entrada, intentar restaurar la original
	        try {
	            usuario.agregarEntrada(entradaObjeto);
	            usuariosDeEntrada.put(entradaObjeto.getCodigo(), usuario);
	        } catch(Exception restoreException) {
	            // Si no se puede restaurar, lanzar el error original
	        }
	        throw e;
	    }
	}

	//Similar al anterior pero con sector y asientos.
	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fechaString) {
		if(entrada == null) {
	        throw new RuntimeException("Error: La entrada no puede ser nula");
	    }
	    if(contrasenia == null) {
	        throw new RuntimeException("Error: La contraseña no puede estar vacía");
	    }
	    if(fechaString == null) {
	        throw new RuntimeException("Error: La fecha no puede estar vacía");
	    }
	    
	    Entrada entradaObjeto = (Entrada) entrada;
	    
	    Usuario usuario = usuariosDeEntrada.get(entradaObjeto.getCodigo());
	    
	    if(usuario == null) {
	        throw new RuntimeException("Error: No se encontró un usuario asociado a esta entrada");
	    }
	    
	    if(!usuario.autenticar(contrasenia)) {
	        throw new RuntimeException("Error: Contraseña incorrecta");
	    }
	    
	    // Obtener el espectáculo
	    Espectaculo espectaculo = espectaculos.get(entradaObjeto.nombreEspectaculo());
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
	    	Fecha fechaNueva = new Fecha(fechaString);
	        
	        List<IEntrada> nuevasEntradas = espectaculo.venderEntrada(entradaObjeto.nombreEspectaculo(), fechaNueva, 1);
	        
	        if(nuevasEntradas.isEmpty()) {
	            throw new RuntimeException("Error: No se pudo crear la nueva entrada");
	        }
	        
	        Entrada nuevaEntrada = (Entrada) nuevasEntradas.get(0);
	        usuario.agregarEntrada(nuevaEntrada);
	        usuariosDeEntrada.put(nuevaEntrada.getCodigo(), usuario);
	        
	        return nuevaEntrada;
	        
	    } catch(RuntimeException e) {
	        // Si falla la creación de la nueva entrada, intentar restaurar la original
	        try {
	            usuario.agregarEntrada(entradaObjeto);
	            usuariosDeEntrada.put(entradaObjeto.getCodigo(), usuario);
	        } catch(Exception restoreException) {
	            // Si no se puede restaurar, lanzar el error original
	        }
	        throw e;
	    }
	    
	}


	//Calcula el costo de una entrada.
	@Override
	public double costoEntrada(String nombreEspectaculo, String fechaString ) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		
		Fecha fecha = new Fecha(fechaString);
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.costoEntrada(fecha);
	}

	//Similar al anterior pero con sector.
	@Override
	public double costoEntrada(String nombreEspectaculo, String fechaString, String sector) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);

		Fecha fecha = new Fecha(fechaString);
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.costoEntrada(fecha,sector);
	}

	//Calcula el total recaudado.
	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.totalRecaudado();
	}

	//Similar al anterior pero por sede.
	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		if(!sedes.containsKey(nombreSede))
			throw new RuntimeException("Error: El nombre de la sede no esta registrado.");
	
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.totalRecaudado(nombreSede);
	}
	
	//Genera reporte completo de usuarios, sedes y espectáculos.
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Usuarios: \n");
		for(Usuario usuario: usuarios.values()) {
			sb.append(usuario.toString());
			sb.append("\n");
		}
		sb.append("Sedes: \n");
		for(Sede sede : sedes.values()) {
			sb.append(sede.toString());
			sb.append("\n");
		}
		sb.append("Espectaculos: \n");
		for(Espectaculo espectaculo : espectaculos.values()) {
			sb.append(espectaculo.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private void validarExistenciaEspectaculo(String nombreEspectaculo) throws RuntimeException{
		if(espectaculos.containsKey(nombreEspectaculo))
			throw new RuntimeException("Error, el nombre del espectaculo ya esta registrado.");
	}
	
	private void validarExistenciaSede(String nombreSede) throws RuntimeException{
		if(sedes.containsKey(nombreSede))
			throw new RuntimeException("Error, el nombre de la sede ya esta registrado.");
	}
	
	private void validarSiEspectaculoEstaRegistrado(String nombreEspectaculo) throws RuntimeException{
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");
	}
	
	private Usuario autentificarUsuario(String email, String contrasenia) {
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
	    
	    return usuario;
	}
	
}
