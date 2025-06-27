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


	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		
		validarExistenciaSede(nombre);
		
		Sede sede = new Estadio(nombre,direccion,capacidadMaxima);
		sedes.put(nombre, sede);
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		
		validarExistenciaSede(nombre);
		
		Sede sede = new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, sede);
	}


	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		
		validarExistenciaSede(nombre);
		
		Sede sede = new MiniEstadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, sede);
	}


	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		if (email == null || email.isEmpty()) {
		    throw new IllegalArgumentException("Error: el email es invalido.");
		}
		if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
		    throw new IllegalArgumentException("Error: el email es invalido.");
		}
		if(usuarios.containsKey(email)) {
	        throw new RuntimeException("Error: Ya existe un usuario registrado con ese email");
	    }
		
	    Usuario usuario = new Usuario(email, nombre, apellido, contrasenia);
	    usuarios.put(email, usuario);
		
	}


	@Override
	public void registrarEspectaculo(String nombre) {
		
		validarExistenciaEspectaculo(nombre);
		
		Espectaculo espectaculo = new Espectaculo(nombre);
		espectaculos.put(nombre, espectaculo);
	}


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


	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fechaString, String email, String contrasenia,
			int cantidadEntradas) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		Usuario usuario = autentificarUsuario(email, contrasenia);
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		Fecha fecha = new Fecha(fechaString);
		LinkedList<IEntrada> entradas = espectaculo.venderEntrada(nombreEspectaculo, fecha, cantidadEntradas);
		
		agregarEntradasAlUsuario(usuario,entradas);
		
		return entradas;
	}


	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fechaString, String email, String contrasenia,
			String sector, int[] asientos) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);

		
		Usuario usuario = autentificarUsuario(email, contrasenia);
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		Fecha fecha = new Fecha(fechaString);
		LinkedList<IEntrada> entradas = espectaculo.venderEntrada(nombreEspectaculo, fecha, sector, asientos);
		
		agregarEntradasAlUsuario(usuario,entradas);
		
		return entradas;
	}

	@Override
	public String listarFunciones(String nombreEspectaculo) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.listarFunciones();
	}


	@Override
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.listarEntradas();
	}


	@Override
	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		
		Usuario usuario = autentificarUsuario(email, contrasenia);
	    
	    return usuario.listarEntradasFuturas();
	}


	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
		
		Usuario usuario = autentificarUsuario(email, contrasenia);
		
	    return usuario.listarEntradas();
	}

	
	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		if(entrada == null) {
	        throw new RuntimeException("Error: La entrada no puede ser nula");
	    }
		
		Entrada entradaObjeto = (Entrada) entrada;
		
		Usuario usuario = autentificarUsuarioDeEntrada(entradaObjeto, contrasenia);
		
	    boolean anulacionExitosa = usuario.anularEntrada(entradaObjeto.getCodigo());
	    
	    if(anulacionExitosa) {
	        usuariosDeEntrada.remove(entradaObjeto.getCodigo());
	        Espectaculo espectaculo = espectaculos.get(entradaObjeto.nombreEspectaculo());
	        if(espectaculo != null) {
	            espectaculo.anularEntrada(entradaObjeto.obtenerFecha(), entradaObjeto.getCodigo(), entradaObjeto.obtenerSector(), entradaObjeto.obtenerAsiento());
	        }
	    }
	    
	    return anulacionExitosa;
	}

	
	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fechaString, String sector, int asiento) {
		if(entrada == null) {
	        throw new RuntimeException("Error: La entrada no puede ser nula.");
	    }
		
		if(sector == null || sector.isEmpty()) {
			throw new RuntimeException("Error: El sector no puede estar vacio o nulo.");
		}
		
		if(asiento < 1) {
			throw new RuntimeException("Error: El asiento tiene un numero invalido.");
		}
		
		
		Entrada entradaObjeto = (Entrada) entrada;
		
		Usuario usuario = autentificarUsuarioDeEntrada(entradaObjeto, contrasenia);
	    

	    Espectaculo espectaculo = espectaculos.get(entradaObjeto.nombreEspectaculo());
	    if(espectaculo == null) {
	        throw new RuntimeException("Error: No se encontró el espectáculo asociado a la entrada");
	    }
	    
	    Fecha fechaNueva = new Fecha(fechaString);
	    
	    if(!espectaculo.puedeVenderEntrada(fechaNueva, sector, asiento)) {
	        throw new RuntimeException("Error: El asiento solicitado no está disponible para la fecha especificada");
	    }

	    
	    List<IEntrada> nuevasEntradas = espectaculo.venderEntrada(entradaObjeto.nombreEspectaculo(), fechaNueva, sector, new int[]{asiento});
	    
	    return procesarCambioEntrada(nuevasEntradas, usuario, entrada,contrasenia);
	    
	}


	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fechaString) {
		if(entrada == null) {
	        throw new RuntimeException("Error: La entrada no puede ser nula");
	    }
		Entrada entradaObjeto = (Entrada) entrada;
		
		Usuario usuario = autentificarUsuarioDeEntrada(entradaObjeto, contrasenia);
	    

	    Espectaculo espectaculo = espectaculos.get(entradaObjeto.nombreEspectaculo());
	    if(espectaculo == null) {
	        throw new RuntimeException("Error: No se encontró el espectáculo asociado a la entrada");
	    }
	    
	    Fecha fechaNueva = new Fecha(fechaString);
	    
	    if(!espectaculo.puedeVenderEntrada(fechaNueva, 1)) {
	        throw new RuntimeException("Error: No hay entradas disponibles para la fecha especificada");
	    }
	    
	    List<IEntrada> nuevasEntradas = espectaculo.venderEntrada(entradaObjeto.nombreEspectaculo(), fechaNueva, 1);
	   
	    return procesarCambioEntrada(nuevasEntradas, usuario, entrada,contrasenia);
	    
	}
	
	public IEntrada procesarCambioEntrada(List<IEntrada> nuevasEntradas, Usuario usuario, IEntrada entrada, String contrasenia) {
		anularEntrada(entrada, contrasenia);
	    Entrada nuevaEntrada = (Entrada) nuevasEntradas.get(0);
	    usuario.agregarEntrada(nuevaEntrada);
	    usuariosDeEntrada.put(nuevaEntrada.getCodigo(), usuario);
	    return nuevaEntrada;
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fechaString ) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		
		Fecha fecha = new Fecha(fechaString);
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.costoEntrada(fecha);
	}


	@Override
	public double costoEntrada(String nombreEspectaculo, String fechaString, String sector) {
		
		if(sector == null || sector.isEmpty())
			throw new RuntimeException("Error: el sector es invalido.");
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);

		Fecha fecha = new Fecha(fechaString);
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.costoEntrada(fecha,sector);
	}


	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.totalRecaudado();
	}


	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		
		validarSiEspectaculoEstaRegistrado(nombreEspectaculo);
		
		if(nombreSede == null)
			throw new RuntimeException("Error, el nombre de la sede es nulo.");
		
		if(!sedes.containsKey(nombreSede))
			throw new RuntimeException("Error: El nombre de la sede no esta registrado.");
	
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.totalRecaudado(nombreSede);
	}
	

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
		if(nombreEspectaculo == null)
			throw new RuntimeException("Error, el nombre del espectaculo es nulo.");
		if(espectaculos.containsKey(nombreEspectaculo))
			throw new RuntimeException("Error, el nombre del espectaculo ya esta registrado.");
	}
	
	private void validarExistenciaSede(String nombreSede) throws RuntimeException{
		if(nombreSede == null)
			throw new RuntimeException("Error, el nombre de la sede es nulo.");
		if(sedes.containsKey(nombreSede))
			throw new RuntimeException("Error, el nombre de la sede ya esta registrado.");
	}
	
	private void validarSiEspectaculoEstaRegistrado(String nombreEspectaculo) throws RuntimeException{
		if(nombreEspectaculo == null)
			throw new RuntimeException("Error, el nombre del espectaculo es nulo.");
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("Error: El nombre del espectaculo no esta registrado.");
	}
	
	private Usuario autentificarUsuario(String email, String contrasenia) {
		if(email == null) 
	        throw new RuntimeException("Error: El email no puede estar vacío");
	    
	    
	    if(contrasenia == null) 
	        throw new RuntimeException("Error: La contraseña no puede estar vacía");
	    
	    
	    Usuario usuario = usuarios.get(email);
	    
	    if(usuario == null) 
	        throw new RuntimeException("Error: No existe un usuario registrado con ese email");
	    
	    
	    if(!usuario.autenticar(contrasenia)) 
	        throw new RuntimeException("Error: Contraseña incorrecta");
	    
	    return usuario;
	}
	
	private Usuario autentificarUsuarioDeEntrada(Entrada entrada, String contrasenia) {
		if(contrasenia == null) {
	        throw new RuntimeException("Error: La contraseña no puede ser nula");
	    }
	    
	    Usuario usuario = usuariosDeEntrada.get(entrada.getCodigo());
	    
	    if(usuario == null) {
	        throw new RuntimeException("Error: No se encontró un usuario asociado a esta entrada");
	    }
	    
	    if(!usuario.autenticar(contrasenia)) {
	        throw new RuntimeException("Error: Contraseña incorrecta");
	    }
	    return usuario;
	}
	
	private void agregarEntradasAlUsuario(Usuario usuario, LinkedList<IEntrada> entradas) {
		for(IEntrada entrada : entradas) {
			Entrada entradaObjeto = (Entrada) entrada;
			usuario.agregarEntrada(entradaObjeto);
			usuariosDeEntrada.put(entradaObjeto.getCodigo(), usuario);
		}
	}
	
}
